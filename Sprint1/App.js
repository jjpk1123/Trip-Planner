class Header extends React.Component {
  render() {
    return (
      <div>
        <center>  
          <h1>Distance Calculator<br/> <small>Quixotic Quetzals</small> </h1> 
          <div>Input your source and destination coordinates in one of the following formats:</div>
        </center>
        <center> 
          <li>40° 26′ 46″ N 79° 58′ 56″ W</li>
          <li>40° 26.767′ N 79° 58.933′ W</li>
          <li>40.446° N 79.982° W</li>
          <li>40.445 -79.982</li>
        </center> 
      </div>
    )
  }
}

class Calculator extends React.Component {
  constructor(props) {
    super(props);
    this.state = { // state variables 
      output : "",
      source : "",
      sourceLa : "",
      sourceLo : "",
      destination : "",
      destinationLa : "",
      destinationLo : "",
      unit : "miles",
      debug : false // set debug to true for live alerts! 
    }
    this.updateSource = this.updateSource.bind(this);
    this.updateDestination = this.updateDestination.bind(this);
    this.GCD = this.GCD.bind(this);
    this.setUnit = this.setUnit.bind(this);
    this.Calculate = this.Calculate.bind(this);
  }

  // updates the value of {source, sourceLo, sourceLa}
  // called live whenever Source gets changed
  updateSource(event) { 
    this.setState({output : ""}); // clears the previous calculation from output
    this.setState({source : event.target.value}); // source input gets stored into a state

    if (event.target.value.includes("N")) {
      split = event.target.value.split("N");
      split[0] = this.degreesToDecimal((split[0] + "N"));
      //The first character of split[1] is a space. Must remove it before degreesToDecimal will work.
      split[1] = this.degreesToDecimal(split[1].substring(1, split[1].length))
    } 
    else if (event.target.value.includes("S")) {
      split = event.target.value.split("S");
      split[0] = this.degreesToDecimal((split[0] + "S"));
      //The first character of split[1] is a space. Must remove it before degreesToDecimal will work.
      split[1] = this.degreesToDecimal(split[1].substring(1, split[1].length))
    } 
    else { // Source in format: 40.445 -79.982
      split = event.target.value.split(" ");
    }
    if (this.state.debug) alert("sourceLat: ["+split[0]+"]\nsourceLong: ["+split[1]+"]");  // if debug is set to true
    this.setState({sourceLa : split[0]});
    this.setState({sourceLo : split[1]});
    //this.Calculate(event); Sadly, it's one-step-behind ]:
  }

  // updates the value of {destination, destinationLo, destinationLa}
  // called live whenever Destination gets changed
  updateDestination(event) {
    this.setState({output : ""});  // clears the previous calculation from output
    this.setState({destination : event.target.value}); // destination input gets stored into the state

    if (event.target.value.includes("N")) { 
      split = event.target.value.split("N");
      split[0] = this.degreesToDecimal((split[0] + "N"));
      //The first character of split[1] is a space. Must remove it before degreesToDecimal will work.
      split[1] = this.degreesToDecimal(split[1].substring(1, split[1].length))
    } 
    else if (event.target.value.includes("S")) {
      split = event.target.value.split("S");
      split[0] = this.degreesToDecimal((split[0] + "S"));
      //The first character of split[1] is a space. Must remove it before degreesToDecimal will work.
      split[1] = this.degreesToDecimal(split[1].substring(1, split[1].length))
    } 
    else { // Destination in format: 40.445 -79.982
      split = event.target.value.split(" ");
    }
    if (this.state.debug) alert("destinLat: ["+split[0]+"]\ndestinLong: ["+split[1]+"]");  // if debug is set to true
    this.setState({destinationLa : split[0]});
    this.setState({destinationLo : split[1]});
    //this.Calculate(event); Sadly, it's one-step-behind ]:
  }

  //Convert input to decimals
  degreesToDecimal(degrees) {
    var inp = degrees.split(" ");
    var ret = 0;

    if (degrees.includes("°")) { // if it has degrees
      ret += Number(inp[0].slice(0, -1));
    } 
    else { // Input is in format: 40.445 -79.982
      if (this.state.debug) alert("no °, ∴ returning["+Number(degrees)+"]");  // if debug is set to true
      return Number(degrees);
    }
    if (degrees.includes("\'") || degrees.includes("\′")) { // if it has minutes
      ret += (Number(inp[1].slice(0, -1)) / 60); // += minutes divided by 60
    }
    if (degrees.includes("\"") || degrees.includes("\″")) { // if it has seconds
      ret += (Number(inp[2].slice(0,-1)) / 3600); // += seconds divided by 3600
    }
    if (degrees.includes("S") || degrees.includes("W")) { // if it is South or West, 
      ret *= -1; // makes the return value negative
    }
    if (this.state.debug) alert("Converted ["+Number(degrees)+"] to: ["+ret+"]"); // if debug is set to true
    return ret;
  }

  // Checks to make sure all 4 fields are populated, and calls GCD
  Calculate(event) {
    if (this.state.source == "" || this.state.sourceLo == "" || this.state.sourceLa == "" || 
        this.state.destination == "" || this.state.destinationLo == "" || this.state.destinationLa == "") {
      this.setState({output : "Please enter both inputs"}); // Displays message through output
      return; // does NOT call GCD
    }
    this.GCD(event); // all 4 inputs are populated
  }

  //Great Circle Distance
  GCD(event) {
    //0. Convert to radians    
    var a1 = this.radians(this.state.sourceLa);
    var b1 = this.radians(this.state.sourceLo);
    var a2 = this.radians(this.state.destinationLa);
    var b2 = this.radians(this.state.destinationLo);

    //1. Test validity of inputs
    var rtnNum = this.inputValid([a1,b1,a2,b2]); // returns -1 if all 4 inputs are valid
    if (rtnNum != -1) {
      var input_error = "Not Calculatable";
      if      (rtnNum == 0) inp_error = "Source Latitude";
      else if (rtnNum == 1) inp_error = "Source Longitude";
      else if (rtnNum == 2) inp_error = "Destination Latitude";
      else if (rtnNum == 3) inp_error = "Destination Longitude";
      this.setState({output : "Input Failed with: " + inp_error});
      return;
    }

    //2. Compute X, Y, Z
    var x = Math.cos(b2)*Math.cos(a2) - Math.cos(b1)*Math.cos(a1);
    var y = Math.cos(b2)*Math.sin(a2) - Math.cos(b1)*Math.sin(a1);
    var z = Math.sin(b2) - Math.sin(b1);

    //3. Compute chord length
    var c = Math.sqrt((x*x) + (y*y) + (z*z));

    //4. Compute central angle
    var o = 2*(Math.asin(c/2));

    //5. Find GCD
    if (document.getElementById("unitSelect").value == "miles") {
      this.setState({unit : "miles"});
      d = o * 3958.7613; // Miles earth radius
    }
    else { // "kilometers" is selected 
      this.setState({unit : "kilometers"});
      d = o * 6371.0088; // Kilometer earth radius
    }
    if (this.state.debug) {  // if debug is set to true
      alert("a1 = " + a1 + "\nb1 = " + b1 +
            "\na2 = " + a2 + "\nb2 = " + b2 +
            "\nx = " + x + "\ny = " + y + "\nz = " + z +
            "\nc = " + c + "\no = " + o + "\nd = " + d);
    }
    this.setState({output : Math.round(d)}); // updates output box to distance (without decimals)
  }

  radians(degrees) {
    return degrees * (Math.PI / 180);
  }

  // Tests to see if "Not a Number" on all 4 inputs once they are in decimal format
  inputValid(array) {
    for (i = 0; i < array.length; ++i)
      if (isNaN(array[i])) // if input is Not a Number (ex: "String")
        return i; // returns the input that is incorrect
    return -1; // All inputs are valid 
  }

  // Provides functionality to id="unitSelect" dropdown box
  setUnit(event) {
    if (event.target.value == "miles") { // if switched to miles
      this.setState({unit : event.target.value}); // changes unit to miles
    }
    else if (event.target.value == "kilometers") { // if switched to kilos
      this.setState({unit : event.target.value}); // changes unit to kilos
    }
    this.Calculate(event); // update output box (with new units)
  }

  render() { /* 3 x 2 table containing source, destination, and output rows*/
    return (
      <div className = "container">
        <div className = "row no-gutters">
          <div className="col-md-2">
          </div>
          <div className="col-md-4">
            <input type="text" className="form-control form-control-lg" placeholder="Source"
              value={this.state.source} onChange={this.updateSource}/> 
          </div>
          <div className="col-md-4">
            <input type="text" className="form-control form-control-lg"  placeholder="Destination"
              value={this.state.destination} onChange={this.updateDestination}/> 
          </div>
        </div>
        <div className = "row no-gutters">
          <div className="col-md-2">
          </div>
          <div className="col-md-4">
            <select className="form-control form-control-lg" id="unitSelect" onChange={this.setUnit}>
              <option value="miles">     Miles</option>
              <option value="kilometers">Kilometers</option>
            </select>
          </div>
          <div className="col-md-4">
            <input className="btn btn-secondary btn-lg btn-block" type="submit" value="Calculate" 
              onClick={this.Calculate}/>
          </div>
        </div>
        <div className = "row no-gutters">
          <div className="col-md-2">
          </div>
          <div className="col-md-8">
            <input className="form-control form-control-lg text-center" placeholder="Output" 
              id="outputOut" value={this.state.output} disabled/>
          </div>
        </div>
      </div>
    )
  } 
}

class Application extends React.Component {
  render() {
    return (
      <div className="jumbotron">
        <Header />
        <hr/>
        <Calculator />
      </div>  
    )
  }
}

ReactDOM.render(<Application  />, document.getElementById("distance"));
