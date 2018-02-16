import React, {Component} from 'react';
import Map from './Map';
import Itinerary from './Itinerary';

/* Trip computes the map and intinerary based on a set of destinations and options.
 * The destinations and options reside in the parent object so they may be set by
 * the Destinations and Options classes.
 * The map and itinerary reside in this object so they can be passed to the Map and Itinerary classes.
 */
class Trip extends Component {
  constructor(props) {
    super(props);

    this.plan = this.plan.bind(this);
    this.saveTFFI = this.saveTFFI.bind(this);
  }

  /* Sends a request to the server with the destinations and options.
   * Receives a response containing the map and itinerary to update the
   * state for this object.
   */
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = this.props.trip;
    console.log("from fetchResponse: " + this.props.trip);

    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch(process.env.SERVICE_URL + '/plan', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async plan(){
    try {
      console.log("Awaiting response from server");
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();
      console.log(tffi);
      this.props.updateTrip(tffi);
      console.log("Async Plan(): fetchReponse is done");
    } catch(err) {
      console.error("You hit an error in async plan()");
      console.error(err);
    }
  }

  /* Saves the map and itinerary to the local file system.
   */
  saveTFFI(){
  }

  /* Renders the buttons, map, and itinerary.
   * The title should be specified before the plan or save buttons are valid.
   */
  render(){
    return(
        <div id="trip" className="card">
          <div className="card-header bg-info text-white">
            Trip
          </div>
          <div className="card-body">
            <p>Give your trip a title before planning or saving.</p>
            <div className="input-group" role="group">
              <span className="input-group-btn">
              <button className="btn btn-primary " onClick={this.plan} type="button">Plan</button>
            </span>
              <input type="text" className="form-control" placeholder="Trip title..."/>
              <span className="input-group-btn">
              <button className="btn btn-primary " onClick={this.saveTFFI} type="button">Save</button>
            </span>
            </div>
            <Map trip={this.props.trip} />
            <Itinerary trip={this.props.trip} />
          </div>
        </div>
    )
  }
}

export default Trip;