What I want to merge with master after Alex's code goes back to master:
```
class Header extends React.Component {
  render() {
    return (
      <div>
        <h1>Distance Calculator <small>Quixotic Quetzals </small> </h1> 
        <div>Input your source and desintation coordinates in one of the following formats:
        </div>
        <ul>
          <li>38° 54\′ 02\″ N 107° 55\′ 33\″ W</li>
          <li>11° 22.123\′ N 99° 88.987\′ W</li>
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
      sourceLa: 0, //N|S
      sourceLo: 0, //E\W
      destinationLa: 0, //N|S
      destinationLo: 0, //E|W
      unit: "miles",
      rkm: 6371.0088, //Earth radius in kilometers
      rmi: 3958.7613  //Earth radius in miles
    };
    this.updateSource = this.updateSource.bind(this);
    this.updateDestination = this.updateDestination.bind(this);
    this.unitConvert = this.unitConvert.bind(this);
  }

  updateSource(event) { /* updates the value of source */
    var st = event.target.value;
    var start = event.target.value.split(/ |" |″ |' |′ |° /); // means it splits on: [space|d_quo +space|d_quo2 +space|s_quo +space|s_quo2 +space|deg_char +space]
        // [" ] means it spits on doubles quotes followed by a space; We'll need to adjust for future dist_calc assignments (brews.md does not account for spaces)
    var numLa = 0;
    var numLo = 0;    
    if(st.includes("\"") || st.includes("\″")){ // On assignment page, ″ was given (from wikipedia)
      //alert("s = DDD° MM′ SS″ N DDD° MM′ SS″ W");
      if(st.includes("N")){
        //alert("seconds + N");
        numLa =  Number(start[2].substring(0, start[2].slice(0, -1))) / 3600; // += seconds
        numLa += Number(start[1].substring(0, start[1].slice(0, -1))) / 60;   // += minutes
        numLa += Number(start[0].substring(0, start[0].slice(0, -1)));        // += degrees
      }
      else if(st.includes("S")){
        //alert("seconds + S");
        numLa -= Number(start[2].substring(0, start[2].slice(0, -1))) / 3600;
        numLa -= Number(start[1].substring(0, start[1].slice(0, -1))) / 60;
        numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
      }

      if(st.includes("E")){
        //alert("seconds + E");
        numLo = Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
        numLo += Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
        numLo += Number(start[4].substring(0, start[4].slice(0, -1)));
      }
      else if(st.includes("W")){
        //alert("seconds + W");
        numLo -= Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
        numLo -= Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
        numLo -= Number(start[4].substring(0, start[4].slice(0, -1)));
      }

    } 
    else if(st.includes("\'") || st.includes("\′")){ // On assignment, ′ was given (from wikipedia)
      //alert("s = DDD° MM.###′ N DDD° MM.###′ W");
      if(st.includes("N")){
        //alert("minutes + N");
        numLa = Number(start[1]) / 60;
        numLa += Number(start[0]);
      }
      else if(st.includes("S")){
        //alert("minutes + S");
        numLa -= Number(start[1]) / 60;
        numLa -= Number(start[0]); 
      }
      if(st.includes("E")){
        //alert("minutes + E");
        numLo = Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
        numLo += Number(start[3].substring(0, start[3].slice(0, -1)));
      }
      else if(st.includes("W")){
        //alert("minutes + W");
        numLo -= Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
        numLo -= Number(start[3].substring(0, start[3].slice(0, -1)));
      }
    }
    else if(st.includes("°")){
      //alert("s = DDD.###° N DDD.###° W");
      if(st.includes("N")){ 
        //alert("degrees + N");
        numLa = Number(start[0].substring(0, start[0].slice(0, -1)));
      }
      else if(st.includes("S")){
        //alert("degrees + N");
        numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
      } 
      if(st.includes("E")){
        //alert("degrees + E");
        numLo = Number(start[2].substring(0, start[2].slice(0, -1)));
      }
      else if(st.includes("W")){
        //alert("degrees + W");
        numLo -= Number(start[2].substring(0, start[2].slice(0, -1)));
      }
    }
    else{
      //alert("s = DDD.### DDD.###");
      numLa = Number(start[0]);
      numLo = Number(start[1]);
    }
    this.setState({sourceLa : numLa});
    this.setState({sourceLo : numLo});
    alert("sourceLa was [" + this.state.destinationLa + "]\n           will be [" + numLa + "]\n" +
          "sourceLo was [" + this.state.destinationLo + "]\n           will be [" + numLo + "]");
  }

  updateDestination(event) { /* updates the value of destination */
    var st = event.target.value;
    var start = event.target.value.split(/ |" |″ |' |′ |° /); /* split explained in updateSource(event) */
    //alert(start);
    var numLa = 0;
    var numLo = 0;
    if(st.includes("\"") || st.includes("\″")){
      //alert("d = DDD° MM′ SS″ N DDD° MM′ SS″ W");
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
        numLo += Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
        numLo += Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
        numLo += Number(start[4].substring(0, start[4].slice(0, -1)));
      }
      else if(st.includes("W")){
        numLo -= Number(start[6].substring(0, start[6].slice(0, -1))) / 3600;
        numLo -= Number(start[5].substring(0, start[5].slice(0, -1))) / 60;
        numLo -= Number(start[4].substring(0, start[4].slice(0, -1)));
      }

    } 
    else if(st.includes("\'") || st.includes("\′")){
      //alert("d = DDD° MM.###′ N DDD° MM.###′ W");
      if(st.includes("N")){
        numLa = Number(start[1]) / 60;
        numLa += Number(start[0]);
      }
      else if(st.includes("S")){
        numLa -= Number(start[1]) / 60;
        numLa -= Number(start[0]); 
      }
      if(st.includes("E")){
        numLo = Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
        numLo += Number(start[3].substring(0, start[3].slice(0, -1)));
      }
      else if(st.includes("W")){
        numLo -= Number(start[4].substring(0, start[4].slice(0, -1))) / 60;
        numLo -= Number(start[3].substring(0, start[3].slice(0, -1)));
      }
    }
    else if(st.includes("°")){
      //alert("d = DDD.###° N DDD.###° W");
      if(st.includes("N")){ 
        numLa = Number(start[0].substring(0, start[0].slice(0, -1)));
      } 
      else if(st.includes("S")){
        numLa -= Number(start[0].substring(0, start[0].slice(0, -1)));
      } 
      if(st.includes("E")){
        numLo = Number(start[2].substring(0, start[2].slice(0, -1)));
      }
      else if(st.includes("W")){
        numLo -= Number(start[2].substring(0, start[2].slice(0, -1)));
      }
    }
    else{
      //alert("d = DDD.### DDD.###");
      numLa = Number(start[0]);
      numLo = Number(start[1]);
    }
    this.setState({destinationLa : numLa});
    this.setState({destinationLo : numLo});
    alert("destLa was [" + this.state.destinationLa + "]\n       will be [" + numLa + "]\n" +
          "destLo was [" + this.state.destinationLo + "]\n       will be [" + numLo + "]");
  }
```
