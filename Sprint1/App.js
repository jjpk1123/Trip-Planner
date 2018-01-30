class Header extends React.Component {
  render() {
    return (
      <div>
        <h1>Distance Calculator <small>Quixotic Quetzals </small> </h1> 
        <div>Input your source and desintation coordinates in one of the following formats:
        </div>
        <ul>
          <li>40° 26′ 46″ N 79° 58′ 56″ W</li>
          <li>40° 26.767′ N 79° 58.933′ W</li>
          <li>40.446° N 79.982° W</li>
          <li>40.445 -79.982</li>
        </ul>
      </div>
    )
  }
}

class Calculator extends React.Component {
  constructor(props) {
    super(props);
    /* state variables */
    this.state = { 
      output: "",
      source: "",
      sourceLa: 0,
      sourceLo: 0,
      destination: "",
      destinationLa: 0,
      destinationLo: 0,
      unit: "miles"
    };
    this.GCD                = this.GCD.bind(this);
    this.updateSource       = this.updateSource.bind(this);
    this.updateDestination  = this.updateDestination.bind(this);
    this.unitConvert        = this.unitConvert.bind(this);
  }

  updateSource(event) { /* updates the value of source */
    this.setState({source : event.target.value});
    var split = ""
    if(this.state.source.includes("N")){
      split = this.state.source.split("N");
      split[0] += "N";
      
    }else if(this.state.source.includes("S")){
      split = this.state.source.split("S");
      split[0] += "S"
    } else{
      split = this.state.source.split(" ");
    }
    this.setState({sourceLa: this.degreesToDecimal(split[0])});
    //The first character of split[1] is a space. Must remove it before degreesToDecimal will work.
    this.setState({sourceLo: this.degreesToDecimal(split[1].substring(1, split[1].length))});
    
    //Call the GCD method from here when ready.
    this.setState({output : this.GCD((this.state.sourceLo), 
                                     (this.state.sourceLa), 
                                     (this.state.destinationLo), 
                                     (this.state.destinationLa))});
  }

  updateDestination(event) { /* updates the value of destination */
    this.setState({destination : event.target.value});
    var split = ""
    if(this.state.destination.includes("N")){
      split = this.state.destination.split("N");
      split[0] += "N";
      
    }else if(this.state.destination.includes("S")){
      split = this.state.destination.split("S");
      split[0] += "S"
    } else{
      split = this.state.destination.split(" ");
    }
    this.setState({destinationLa: this.degreesToDecimal(split[0])});
    //The first character of split[1] is a space. Must remove it before degreesToDecimal will work.
    this.setState({destinationLo: this.degreesToDecimal(split[1].substring(1, split[1].length))});
    
    //Call the GCD method from here when ready.
    this.setState({output : this.GCD(this.state.sourceLo, 
                                     this.state.sourceLa, 
                                     this.state.destinationLo, 
                                     this.state.destinationLa)});
  }

  unitConvert(event) { /* gets called when the select box's value is changed */
    /* currUnit = value of select box located ~line 86 */
    var currUnit = document.getElementById("unitSelect").value; 
    if(currUnit == "kilometers") { // m -> k
      //alert("m2k");
      this.setState({unit : "kilometers"}); /* Changes "unit" from miles to kilos*/
      this.setState({output : Number(this.state.output) * 1.609344}); /* sets "output" to kilometers */
    }
    else if(currUnit == "miles"){ // k -> m
      //alert("k2m");
      this.setState({unit : "miles"}); /* Changes "unit" from kilos to miles*/
      this.setState({output : Number(this.state.output) / 1.609344}); /* sets "output" to miles */
    }
  }
  
  //Convert degrees to decimals
  degreesToDecimal(degrees){
    var inp = degrees.split(" ");
    var ret = 0;
    
    if(degrees.includes("°")){
      ret += Number(inp[0].slice(0, -1));
    } else{
      return Number(degrees);
    }   
    //minutes divided by 60
    if(degrees.includes("\'")){
      ret += (Number(inp[1].slice(0, -1)) / 60);
    }
    //seconds divided by 3600
    if(degrees.includes("\"")){
      ret += (Number(inp[2].slice(0,-1)) / 3600);
    }
    //If negative
    if(degrees.includes("S") || degrees.includes("W")){
      ret *= -1;
    }
    return ret;
    
  }
  radians(degrees){
    return degrees * (Math.PI / 180);
  }  
  
  //Great Circle Distance
  //Takes long1, lat1 (source) and long2, lat2 (destination) as degrees
  GCD(event){
    //0. Convert to radians    
    var a1 = this.radians(this.state.sourceLo);
    var b1 = this.radians(this.state.sourceLa);
    var a2 = this.radians(this.state.destinationLo);
    var b2 = this.radians(this.state.destinationLa);
    
    //1. Compute X, Y, Z
    var x = Math.cos(b2)*Math.cos(a2) - Math.cos(b1)*Math.cos(a1);
    var y = Math.cos(b2)*Math.sin(a2) - Math.cos(b1)*Math.sin(a1);
    var z = Math.sin(b2) - Math.sin(b1);
    
    //2. Compute chord length
    var c = Math.sqrt((x*x) + (y*y) + (z*z));
    
    //3. Compute central angle
    var o = 2*(Math.asin(c/2));
    
    //4. Find GCD
    if (document.getElementById("unitSelect").value == "miles"){
      this.setState({unit : "miles"});
      d = o * 3958.7613; //Miles earth radius
    }
    else if (document.getElementById("unitSelect").value == "kilometers"){ 
      this.setState({unit : "kilometers"});
      d = o * 6371.0088; //Kilometer earth radius
    }
    else{ // wipes old units from output box
      this.setState({unit: "select"});
      d = 0; //Maybe return string "Select a unit! -->"
    }
    this.setState({output : Math.floor(d)});
}
  
  render() { /* 2 x 3 table containing source, destination, and output rows*/
    return (
      <table>
        <tr>
          <td><b> Source: </b></td>
          <td><input type="text" className="text-left" size="33" 
                value={this.state.source} onChange={this.updateSource}/> 
          </td>
        </tr>
        <tr>
          <td><b> Destination: </b></td>
          <td><input type="text" className="text-left" size="33" 
                value={this.state.destination} onChange={this.updateDestination}/> 
          </td>
        </tr>
        <tr>
          <td><b> Output: </b></td>
          <td>
            <input size="33" value={this.state.output} disabled/>
          </td>
          <select id="unitSelect" value={this.state.unit} onChange={this.unitConvert}>
            <option value="miles">     Miles</option>
            <option value="kilometers">Kilometers</option>
          </select>
        </tr>
      </table>
    )
  } 
}
/* my failed attempt at coming up with two buttons; used "select/option" instead of "form/label" because it won't call: onChange={this.unitConvert}
      <td>
          <form id="unitsRadioButton" data-toggle="buttons" 
                className="btn-group btn-group-toggle p-3" onChange={this.unitConvert}>
              <label className="btn btn-outline-secondary active">
                Miles <input type="radio" value="miles"/>
              </label>
              <label className="btn btn-outline-secondary">
                Kilometers <input type="radio" value="kilometers"/>
              </label>
          </form>
          </td>
*/

class Application extends React.Component {
  render() {

    /* separate the page layout from the calculator function */
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
