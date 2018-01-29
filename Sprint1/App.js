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
      destination: "",
      unit: "miles"
    };
    this.calc = this.calc.bind(this);
    this.updateSource = this.updateSource.bind(this);
    this.updateDestination = this.updateDestination.bind(this);
    this.unitConvert = this.unitConvert.bind(this);
  }

  updateSource(event) { /* updates the value of source */
    this.setState({source : event.target.value});
    this.setState({output : Number(event.target.value) + Number(this.state.destination) });
  }

  updateDestination(event) { /* updates the value of destination */
    this.setState({destination : event.target.value});
    this.setState({output : Number(event.target.value) + Number(this.state.source) });
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
  
    //Great Circle Distance
  //Takes long1, lat1 (source) and long2, lat2 (destination) as floating point
  GCD(long1, lat1, long2, lat2){
    //0. Convert to radians
    a1 = long1.toRadians;
    b1 = lat1.toRadians;
    a2 = long2.toRadians;
    b2 = lat2.toRadians;
    
    //1. Compute X, Y, Z
    x = Math.cos(b2)*Math.cos(a2) - Math.cos(a1)*Math.cos(b1);
    y = Math.cos(b2)*Math.sin(a2) - Math.cos(b1)*Math.sin(b1);
    z = Math.sin(b2) - Math.sin(b1);
    
    //2. Compute chord length
    c = Math.sqrt((x*x) + (y*y) + (z*z));
    
    //3. Compute central angle
    o = 2*Math.arcsin(c/2);
    
    //TODO: Find a way to live update this final step, as it depends on the unit chosen
    //My implementation might work, but could use some polish I believe
    //4. Find GCD
    d = 0;
    if (unit == "miles"){ d = rmi*o;} //Miles earth radius
    else{ d = rkm*o;}                 //Kilometer earth radius
    
    //5. Return the distance *phew*
    return d;
}
  
  calc(event) {
    /* Coordinates are text.  Must use GCD chord formula */
    this.setState({output : /* just adds the two for now */Number(this.state.source) + Number(this.state.destination)}); 
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
