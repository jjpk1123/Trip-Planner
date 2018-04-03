import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);
    this.unitsString = "";
    this.createTable = this.createTable.bind(this);
    this.retrieveAlgorithm = this.retrieveAlgorithm.bind(this);
    this.getRoundTripDistance = this.getRoundTripDistance.bind(this);
  }

  /**
   * Returns the values to be placed in the render() block
   */
  createTable() {
    let numPlaces = this.props.trip.distances.length;
    let units = this.props.trip.options.distance;
    if (units === "user defined") units = this.props.trip.options.userUnit;
    this.setUnitsString(units);
    let distKey = 0;
    let dists = this.props.trip.distances.map((item) => <td key={distKey++}>{item}</td>);

    if (this.validateDistance(numPlaces) === false) {
      return {units};//not all places have valid distances
    }

    let dests = this.props.trip.places.map((item) => <td key={item.id}>{item.name}</td>);
    let cumul = this.getCumulative();

    if (dests.length !== 0) {//if there are >1 Destination
      dests = this.destsConcatFinal(dests);//appends the final dest=start place
    }
    if (dists.length !== 0) {//if there are >1 Distances
      dists = this.distsConcatZero(dists, 0);//appends a 0 to distances
      cumul = this.distsConcatZero(cumul, 1);//appends a 0 to cumulative
    }
    return {units, dests, dists, cumul};
  }

  /**
   * Capitalizes the units for the row in table, unless user-defined
   */
  setUnitsString(units) {
    units = units.toLowerCase();

    if (units === "miles") {
      this.unitsString = "Miles";
    } else if (units === "kilometers") {
      this.unitsString = "Kilometers";
    } else if (units === "nautical miles") {
      this.unitsString = "Nautical Miles";
    } else { // user-defined
      this.unitsString = this.props.trip.options.userUnit;
    }
  }

  /**
   * Return true if all Latitude/Longitude are >= 0
   */
  validateDistance(numPlaces) {
    for (let d = 0; d < numPlaces; ++d) {
      //console.log(this.props.trip.places[d].name + " " + this.props.trip.distances[d]);
      let compare = this.props.trip.distances[d];
      if (compare < 0) {//Incorrect Latitude/Longitude
        this.invalidDistance(compare, d, numPlaces);
        return false;
      }
    }
    return true;
  }

  /**
   * Called from validateDistance() when there
   * is an invalid distance between two places
   * @param compare value either -1(this dest) or -2(the next dest)
   * @param d = which Dest is currently invalid
   * @param numPlaces = how many Destinations there are
   */
  invalidDistance(compare, d, numPlaces) {
    let plc = ""; //which place
    let lat = ""; //what's the lat
    let lon = ""; //what's the long
    if (compare === -1) {
      plc = this.props.trip.places[d].name;
      lat = this.props.trip.places[d].latitude;
      lon = this.props.trip.places[d].longitude;
    } else if (compare === -2) {
      plc = this.props.trip.places[(d + 1) % numPlaces].name;
      lat = this.props.trip.places[(d + 1) % numPlaces].latitude;
      lon = this.props.trip.places[(d + 1) % numPlaces].longitude;
    }
    console.log("Bad lat/long; Quitting...");
    alert("Cannot parse [" + plc + "] destination with the coordinates provided:\n" +
        "latitude:\t\"" + lat + "\"\n" +
        "longitude:\t\"" + lon + "\"");
  }

  /**
   * Appends the starting place as the final destination (round-trip)
   */
  destsConcatFinal(dests) {
    let start = this.props.trip.places[0].name;
    let myArray = [start];
    let endingCol = myArray.map((item) => <td key={"dest_n"}>{item}</td>);
    return dests.concat(endingCol);//adds to the end of dests row
  }

  /**
   * Concatenates a zero to the beginning of dists/cumul rows
   */
  distsConcatZero(row, unique) {
    let myArray = [0];
    let zero = myArray.map((item) => <td key={"key_" + unique}>{item}</td>);
    return zero.concat(row);//adds a zero to the beginning of row
  }

  /**
   * Returns the "Cumulative" row in the table render() block
   */
  getCumulative() {
    let sum = 0;
    let unique = 0;
    return this.props.trip.distances.map((item) => <td key={"place_" + unique++}>{sum += item}</td>);
  }

  /**
   * Returns the total distance for the trip
   */
  getRoundTripDistance() {
    let sum = 0;
    for (let i = 0; i < this.props.trip.distances.length; ++i) {
      let distToAdd = this.props.trip.distances[i];
      if (distToAdd < 0) {
        console.log("Itinerary.getRoundTripDistance() failed @ place #" + i);
        return 0;
      }
      sum += distToAdd;
    }
    //console.log("Total distance: " + sum); //WooHoo!
    return sum;
  }

  /**
   * Prints out which Algorithm is being used (if > No optimization)
   */
  retrieveAlgorithm() {
    if (this.props.trip.options.optimization === "none") {
      return this.restOfTheString(0);
    }
    let slider = parseFloat((this.props.trip).options.optimization);
    let configOpt = parseInt(this.props.config.optimization);
    let breakPoint = 1.0 / (configOpt + 1);

    if (slider < breakPoint) {
      return this.restOfTheString(0);
    }
    else if (slider >= breakPoint && slider <= 2*breakPoint) { // for 2-opt, use: (... && slider < 2*bP)
      return this.restOfTheString(1);
    }
    // else if (slider >= 2*breakPoint && slider < 3*breakPoint) {
    //   return this.restOfTheString(2);
    // }
  }

  /**
   * If No optimization, returns the default string
   * Else, prints which algo it computed it with
   */
  restOfTheString(index) {
    if (index === 0) {
      return " using the order provided in the file.";
    }
    return " with the " + ((this.props.config).optimizations[index])["label"] + " algorithm!";
  }

  /**
   * Renders the table block located under the <Map>
   */
  render() {
    let table = this.createTable();

    return <div id="itinerary">
      <table className="table table-responsive table-bordered">
        <thead>
        <tr className="table-info">
          <th className="align-middle">Destinations</th>
          {table.dests}
        </tr>
        </thead>
        <tbody>
        <tr>
          <th className="table-info align-middle">{this.unitsString}</th>
          {table.dists}
        </tr>
        <tr>
          <th className="table-info align-middle">Cumulative</th>
          {table.cumul}
        </tr>
        </tbody>
      </table>
      <h4>Round-trip distance of <b>{this.getRoundTripDistance()} {table.units}.</b></h4>
      <h6>Computed <b>{this.retrieveAlgorithm()}</b></h6>
    </div>
  }
}

export default Itinerary;
