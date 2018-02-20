import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);

    this.createTable = this.createTable.bind(this);
    this.totalDistance = Itinerary.tripTotalDistance.bind(this);
  }

  createTable () {
    let distance = 0;  // need to sum this from real the trip
    let units = this.props.trip.options.distance;
    let dests = this.props.trip.places.map((item) => <td key = {item.id}>{item.name}</td>);
    let dists = this.props.trip.distances.map((item) => <td key = {this.props.trip.distances.indexOf(item)}>{item}</td>);
    let totalDistance = tripTotalDistance(dists);
    //console.log("Hello from Itinerary.createTable()! :)");
    //console.log(this.props.trip);

    return {distance, units, dests, dists};
  }

  tripTotalDistance () {
    let distances = this.props.roundTripDist;
    let sum = 0;
    for (d in distances) {
      sum += d;
    }
    return sum;
  }


  render() {
    let table = this.createTable();

    return(
        <div id="itinerary">
          <h4>Round trip distance of {this.tripTotalDistance} {table.units}. </h4>
          <table className="table table-responsive table-bordered">
            <thead>
            <tr className="table-info">
              <th className="align-middle">Destination</th>
              {table.dests}
            </tr>
            </thead>
            <tbody>
            <tr>
              <th className="table-info align-middle">{table.units}</th>
              {table.dists}
            </tr>
            </tbody>
          </table>
        </div>
    )
  }
}

export default Itinerary;
