import React, {Component} from 'react';
import Map from './Map';
import Itinerary from './Itinerary';

/**
 * Trip computes the Map and Itinerary based on a set of destinations and options.
 * The destinations and options reside in the parent object so they may be set by
 * the Destinations and Options classes.
 * The map and itinerary reside in this object so they can be passed to the Map and Itinerary classes.
 */
class Trip extends Component {
  constructor(props) {
    super(props);
    this.plan = this.plan.bind(this);
    this.saveTFFI = this.saveTFFI.bind(this);
    this.updateTitle = this.updateTitle.bind(this);
    this.reverseTrip = this.reverseTrip.bind(this);
    this.tripCardHeader = <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
        Trip
      </h5>;
  }

  /**
   * Sends a request to the server with the destinations and options.
   * Receives a response containing the map and itinerary to update the
   * state for this object.
   */
  fetchResponse() {
    let requestBody = this.props.trip;
    //console.log("from fetchResponse: " + this.props.trip);
    //console.log(process.env.SERVICE_URL);
    //console.log("RQ:" + requestBody);

    return fetch('http://' + location.host + '/plan', {
      method: "POST",
      body: JSON.stringify(requestBody)
    });
  }

  async plan() {
    try {
      //console.log("Awaiting response from server");
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();
      //console.log(tffi);
      this.props.updateTrip(tffi);
      //console.log("async plan(): fetchResponse is done");
    } catch (err) {
      console.error("You hit an error in async plan()");
      console.error(err);
    }
  }

  /**
   * Saves the map and itinerary to the local file system.
   */
  saveTFFI() {
    let jsonData = JSON.stringify(this.props.trip);
    let name = this.props.title + ".json";
    console.log("Saving trip as: " + name);

    let jsonBlob = new Blob([jsonData], {type: "text/plain;charset=utf-8"});
    let textToSaveAsUrl = window.URL.createObjectURL(jsonBlob);

    let downloadLink = document.createElement("a");
    downloadLink.download = name;
    downloadLink.innerHTML = "Download File";
    downloadLink.href = textToSaveAsUrl;
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);

    downloadLink.click();
  }

  /**
   * Updates the trip.title field as the user is typing "live"
   */
  updateTitle(event) {
    console.log("Name: " + event.target.value);
    this.props.updateTitle(event.target.value);
  }

  /**
   * Reverses the trip.places && trip.distances array on the client.
   */
  reverseTrip() {
    if ((this.props.trip.places).length === 0) return;
    console.log("Reversing Trip");
    let tempTrip = this.props.trip;
    let size = (this.props.trip.places).length;
    let tempPlaces = [];
    let tempDistances = [];

    tempPlaces.push(tempTrip.places[0]);
    for (let i = (size-1); i > 0; --i) {
      tempPlaces.push(tempTrip.places[i]);
      tempDistances.push(tempTrip.distances[i]);
    }
    tempDistances.push(tempTrip.distances[0]);
    tempTrip.places = tempPlaces;
    tempTrip.distances = tempDistances;

    this.props.updateTrip(tempTrip);
  }

  /**
   * Renders the buttons, map, and itinerary.
   * The title should be specified before the plan or save buttons are valid.
   */
  render() {
    return <div id="trip" className="card">
      {this.tripCardHeader}
      <div className="card-body">
        <p>Give your trip a title before planning or saving.</p>
        <div className="input-group" role="group">
          <span className="input-group-btn">
            <button className="btn text-white " style={{backgroundColor: "#1E4D28"}} onClick={this.plan} type="button">Plan</button>
          </span>
          <input type="text" className="form-control" placeholder="Name your Trip here" onChange={this.updateTitle}/>
          <span className="input-group-btn">
            <button className="btn text-white " style={{backgroundColor: "#1E4D28"}} onClick={this.saveTFFI} type="button">Save</button>
          </span>
        </div>
      </div>
      <div className="card-body">
        <div className="input-group" role="group">
          <span className="input-group-btn">
            <button className="btn text-white"  style={{backgroundColor: "#1E4D28"}} onClick={this.reverseTrip} type="button">Reverse Trip</button>
          </span>
        </div>
      </div>
      <div className="card-body">
        <Itinerary trip={this.props.trip}/>
        <Map trip={this.props.trip}/>
      </div>
    </div>
  }
}

export default Trip;