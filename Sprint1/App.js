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
      sourceLa: "",
      sourceLo: "",
      destination: "",
      destinationLa: "",
      destinationLo: "",
      unit: "miles"
    };
    this.calc = this.calc.bind(this);
    this.updateSource = this.updateSource.bind(this);
    this.updateDestination = this.updateDestination.bind(this);
    this.unitConvert = this.unitConvert.bind(this);
  }

  updateSource(event) { /* updates the value of source */
    this.setState({source : event.target.value});
    var inp = this.state.source;
    if(inp.includes("N")){
      inp = inp.split("N");
      inp[0] += "N";
    } else if(inp.includes("S")){
      inp = inp.split("S");
      inp[0] += "S";
    
    this.setState({sourceLa : this.degreesToDecimal(inp[0])});
    this.setState({sourceLo : this.degreesToDecimal(inp[1])});
    this.setState({output : this.state.sourceLa + this.state.sourceLo });
  }

  updateDestination(event) { /* updates the value of destination */
      this.setState({destination : event.target.value});
    var inp = this.state.destination;
    if(inp.includes("W")){
      inp = inp.split("W");
      inp[1] += "W";
    } else if(inp.includes("E")){
      inp = inp.split("E");
      inp[1] += "E";
    }
    this.setState({destinationLa : this.degreesToDecimal(inp[0])});
    this.setState({destinationLo : this.degreesToDecimal(inp[1])});
    this.setState({output : this.state.sourceLa + this.state.sourceLo });
  }
  //Return decimal value of valid input.
  degreesToDecimal(input){
    var val = input.split(" ");
    var ret = 0;
    if(input.includes("°")){
      ret += val[0].slice(0, -1);
      
    } if(input.includes("\'")){
      ret += val[1].slice(0, -1);
    } if(input.includes("\"")){
      ret += val[2].slice(0, -1);
    }
    if(input.includes("S") || input.includes("W")){
      ret *= -1;
    }
    return ret;
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
