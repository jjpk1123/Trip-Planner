import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);
    this.createTable = this.createTable.bind(this);
  }

  createTable () {

    let distance = 0;  // need to sum this from real the trip
    let units = this.props.trip.options.distance;
    let dests = this.props.trip.places.map((item) => <td key = {item.id}>{item.name}</td>);
    let dists = this.props.trip.distances.map((item) => <td key = {this.props.trip.distances.indexOf(item)}>{item}</td>);
    console.log(dests);
    //console.log("Hello from Itinerary.createTable()! :)");
    //console.log(this.props.trip);

    return {distance, units, dests, dists};
  }

  render() {
    let table = this.createTable();
    let unit = "";
    if (table.units === "miles") {
      unit = "Miles";
    } else {
      unit = "Kilometers";
    }

    return(
        <div id="itinerary">
          <h4>Round trip distance of {this.props.getRoundTripDistance()}  {table.units}. </h4>
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
            </tr>
            </tbody>
          </table>
        </div>
    )
  }
}

export default Itinerary;
