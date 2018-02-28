import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);
    this.createTable = this.createTable.bind(this);
    this.getRoundTripDistance = this.getRoundTripDistance.bind(this);
  }

  createTable () {
    let size = this.props.trip.distances.length;
    let units = this.props.trip.options.distance;
    var i = 500;
    let dists = this.props.trip.distances.map((item) => <td key = {i++}>{item}</td>);

    //valid distance check
    for (let d = 0; d < size; ++d) {
      console.log(this.props.trip.places[d].name + " " + this.props.trip.distances[d]);
      let compare = this.props.trip.distances[d];
      let whichPlace = "########";
      let lat = "########";
      let lon = "########";
      if (compare < 0) {
        if (compare === -1) {
          whichPlace = this.props.trip.places[d].name;
          lat = this.props.trip.places[d].latitude;
          lon = this.props.trip.places[d].longitude;
        } else {
          whichPlace = this.props.trip.places[(d+1)%size].name;
          lat = this.props.trip.places[(d+1)%size].latitude;
          lon = this.props.trip.places[(d+1)%size].longitude;
        }
        alert("Cannot parse [" + whichPlace + "] destination with the coordinates provided:\n" +
                    "latitude:\t\"" + lat + "\"\n" +
                    "longitude:\t\"" + lon + "\"");
        return {units};
      }
    }

    let dests = this.props.trip.places.map((item) => <td key = {item.id}>{item.name}</td>);
    let cumul = this.getCumulative();
    //console.log("Hello from Itinerary.createTable()! :)");

    if (dests.length !== 0) { // if there is more than one Destination
      let start = this.props.trip.places[0].name;
      let myArray = [start];
      let zero = myArray.map((item) => <td key = {"asdf"}>{item}</td>);
      dests = dests.concat(zero); //adds a zero to starting dist
    }
    if (dists.length !== 0) { // same concept, but with Distances
      let myArray = [0];
      let zero = myArray.map((item) => <td key = {"banana"}>{item}</td>);
      dists = zero.concat(dists); //adds a zero to starting dist
      cumul = zero.concat(cumul); //adds a zero to starting cumulative dist
    }

    // returns can be accessed through table.unit, table.dests, etc.
    return {units, dests, dists, cumul};
  }

  getCumulative() {
    let sum = 0;
    return this.props.trip.distances.map((item) => <td key = {sum}>{sum+=item}</td>);
  }

  getRoundTripDistance() {
    let sum = 0;
    let howMany = this.props.trip.distances.length;
    for (let i=0 ; i < howMany ; ++i) {
      let distToAdd = this.props.trip.distances[i];
      if (distToAdd < 0) {
        return 0;
      }
      sum += distToAdd;
    }
    //console.log("Total distance: " + sum); //WooHoo!
    return sum;
  }

  render() {
    let table = this.createTable();

    let unit = ""; //capitalizes the Units rendered in table B]
    if (table.units === "miles") {
      unit = "Miles";
    } else {
      unit = "Kilometers";
    }

    return(
        <div id="itinerary">
          <h4>Round trip distance of <b>{this.getRoundTripDistance()}  {table.units}. </b></h4>
          <table className="table table-responsive table-bordered">
            <thead>
            <tr className="table-info">
              <th className="align-middle">Destination</th>
              {table.dests}
            </tr>
            </thead>
            <tbody>
            <tr>
              <th className="table-info align-middle">{unit}</th>
              {table.dists}
            </tr>
            <tr>
              <th className="table-info align-middle">Cumulative</th>
              {table.cumul}
            </tr>
            </tbody>
          </table>
        </div>
    )
  }
}

export default Itinerary;
