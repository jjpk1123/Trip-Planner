class Header extends React.Component {
  render() {
    return (
      <div>
        <h1>Quixotic Quetzals <small>Distance Calculator</small></h1> 
        Input your source and desintation coordinates in one of the following formats:
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
      sourceLa: "",
      sourceLo: "",
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
    var st = event.target.value;
    var start = event.target.value.split(" ");
    var numLa = 0;
    var numLo = 0;
    if(st.includes("\"")){
         if(st.includes("N")){
            numLa += Number(start[2].substring(0, start[2].slice(0, -1))) / 3600;
            numLa += Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
            numLa += Number(start[0].substring(0, start[0].slice(0, -1)));
         }
         else if(st.includes("S")){
            numLa -= Number(start[2].substring(0, start[2].slice(0, -1))) / 3600;
            numLa -= Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
            numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
         }
  
         if(st.includes("E")){
            numLa += Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
            numLa += Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
            numLa += Number(start[4].substring(0, start[4].slice(0, -1)));
         }
         else if(st.includes("W")){
            numLa -= Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
            numLa -= Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
            numLa -= Number(start[4].substring(0, start[4].slice(0, -1)));
         }
     
    } else if(st.includes("\'")){
             if(st.includes("N")){
                numLa += Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
                numLa += Number(start[0].substring(0, start[0].slice(0, -1)));
               }
              else if(st.includes("S")){
                numLa -= Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
                numLa -= Number(start[0].substring(0, start[0].slice(0, -1))); 
              }
              if(st.includes("E")){
                numLa += Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
                numLa += Number(start[3].substring(0, start[3].slice(0, -1)));
              }
              else if(st.includes("W")){
                numLa -= Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
                numLa -= Number(start[3].substring(0, start[3].slice(0, -1)));
         }
    }
    else if(st.includes("°")){
         if(st.includes("N")){ 
           numLa += Number(start[0].substring(0, start[0].slice(0, -1)));
         }
         else if(st.includes("S")){
           numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
         } 
         if(st.includes("E")){
           numLa += Number(start[2].substring(0, start[2].slice(0, -1)));
         }
         else if(st.includes("W")){
           numLa -= Number(start[2].substring(0, start[2].slice(0, -1)));
         }
    }    
    this.setState({sourceLa : numLa});
    this.setState({sourceLo : numLo});
    this.setState({output : Number(event.target.value) + Number(this.state.destination) });
  }

  updateDestination(event) { /* updates the value of destination */
    var st = event.target.value;
    var start = event.target.value.split(" ");
    var numLa = 0;
    var numLo = 0;
    if(st.includes("\"")){
         if(st.includes("N")){
            numLa += Number(start[2].substring(0, start[2].slice(0, -1))) / 3600;
            numLa += Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
            numLa += Number(start[0].substring(0, start[0].slice(0, -1)));
         }
         else if(st.includes("S")){
            numLa -= Number(start[2].substring(0, start[2].slice(0, -1))) / 3600;
            numLa -= Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
            numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
         }
  
         if(st.includes("E")){
            numLa += Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
            numLa += Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
            numLa += Number(start[4].substring(0, start[4].slice(0, -1)));
         }
         else if(st.includes("W")){
            numLa -= Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
            numLa -= Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
            numLa -= Number(start[4].substring(0, start[4].slice(0, -1)));
         }
     
    } else if(st.includes("\'")){
             if(st.includes("N")){
                numLa += Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
                numLa += Number(start[0].substring(0, start[0].slice(0, -1)));
               }
              else if(st.includes("S")){
                numLa -= Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
                numLa -= Number(start[0].substring(0, start[0].slice(0, -1))); 
              }
              if(st.includes("E")){
                numLa += Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
                numLa += Number(start[3].substring(0, start[3].slice(0, -1)));
              }
              else if(st.includes("W")){
                numLa -= Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
                numLa -= Number(start[3].substring(0, start[3].slice(0, -1)));
         }
    }
    else if(st.includes("°")){
         if(st.includes("N")){ 
           numLa += Number(start[0].substring(0, start[0].slice(0, -1)));
         }
         else if(st.includes("S")){
           numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
         } 
         if(st.includes("E")){
           numLa += Number(start[2].substring(0, start[2].slice(0, -1)));
         }
         else if(st.includes("W")){
           numLa -= Number(start[2].substring(0, start[2].slice(0, -1)));
         }
    }  
    this.setState({destinationLa : numLa});
    this.setState({destinationLo : numLo});
    this.setState({output : Number(event.target.value) + Number(this.state.source) });
  }

  unitConvert(event) { /* gets called when the select box's value is changed */
    /* currUnit = value of unit select box located ~line 86 */
    var currUnit = document.getElementById("unitSelect").value; 
    if(currUnit == "kilometers") { // m -> k
      //alert("m2k");
      this.setState({unit : "kilometers"}); /* Changes "unit" from miles to kilos*/
      this.setState({output : Number(this.state.output) * 1.609344}); /* converts "output" to kilometers */
    }
    else if(currUnit == "miles"){ // k -> m
      //alert("k2m");
      this.setState({unit : "miles"}); /* Changes "unit" from kilos to miles*/
      this.setState({output : Number(this.state.output) / 1.609344}); /* converts "output" to miles */
    }
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

ReactDOM.render(<Application  />, document.getElementById("dist_app"));
