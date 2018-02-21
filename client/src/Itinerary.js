import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);
    this.createTable = this.createTable.bind(this);
  }

  createTable () {
    let size = this.props.trip.distances.length;
    let units = this.props.trip.options.distance;
    let dists = this.props.trip.distances.map((item) => <td key = {this.props.trip.distances.indexOf(item)}>{item}</td>);

    //valid distance check
    for (let d = 0; d < size; ++d) {
      console.log(this.props.trip.places[d].name + " " + this.props.trip.distances[d]);
      let compare = this.props.trip.distances[d];
      if (compare < 0) {
        let whichCoord = "#####3rr0r#####";
        let coordValue = "#####3rr0r#####";

        if (compare === -1) {
          whichCoord = "latitude"
          coordValue = this.props.trip.places[d].latitude;
        }
        else if (compare === -2) {
          whichCoord = "longitude"
          coordValue = this.props.trip.places[d].longitude;
        }
        alert("Cannot parse the " + whichCoord + " of " + this.props.trip.places[d].name +
              "\nwith the value of \"" + coordValue + "\".");
        return {units};
      }
    }

    let dests = this.props.trip.places.map((item) => <td key = {item.id}>{item.name}</td>);
    let cumul = this.getCumulative();
    //console.log("Hello from Itinerary.createTable()! :)");

    if (dests.length !== 0) { // if there is more than one Destination
      dests = dests.concat([dests[0]]); //Adds the starting Place to the end (round trip)
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
    return this.props.trip.distances.map((item) => <td key = {this.props.trip.distances.indexOf(item)}>{sum+=item}</td>);
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
          <h4>Round trip distance of <b>{this.props.getRoundTripDistance()}  {table.units}. </b></h4>
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
