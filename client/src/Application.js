import React, {Component} from 'react';
import Options from './Options';
import Destinations from './Destinations';
import Trip from './Trip';

/**
 * Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
  constructor(props) {
    super(props);
    this.state = {
      trip: { //trip TFFI
        version: 1,
        type: "trip",
        title: "My Trip",
        options: {
          distance: "miles",
          optimization: "none",
        },
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      },
      query: { //query TFFI
        type: "query",
        query: "",
        places: []
      }
    };
    //this.plan = this.plan.bind(this);
    this.updateTrip = this.updateTrip.bind(this);
    this.updateQuery = this.updateQuery.bind(this);
    this.updateTitle = this.updateTitle.bind(this);
  }

  // /**
  //  * Calls respective update method based on input parameter
  //  * @param caller (which is the intended purpose [plan|query])
  //  * @param search (if caller==="query")
  //  */
  // async plan(caller, search) {
  //   try {
  //     //console.log("Awaiting response from server");
  //     let serverResponse = (caller === "plan") ?
  //         await this.fetchResponse() : await this.fetchQueryResponse(search);
  //     let tffi = await serverResponse.json();
  //     //console.log(tffi);
  //     if (caller === "plan") {
  //       this.updateTrip(tffi);
  //     } else if (caller ==="query") {
  //       this.updateQuery(tffi);
  //     }
  //     //console.log("async plan(): fetchResponse is done");
  //   } catch (err) {
  //     console.error("You hit an error in async plan");
  //     console.error(err);
  //   }
  // }
  //
  // /**
  //  * Sends a request to the server with the destinations and options.
  //  * Receives a response containing the map and itinerary to update the
  //  * state for this object.
  //  */
  // fetchResponse() {
  //   let requestBody = this.state.trip;
  //   console.log("from fetchResponse: " + this.state.trip);
  //   console.log(process.env.SERVICE_URL);
  //   //console.log("RQ:" + requestBody);
  //
  //   return fetch('http://' + location.host + '/plan', {
  //     method: "POST",
  //     body: JSON.stringify(requestBody)
  //   });
  // }
  //
  // fetchQueryResponse(search) {
  //   let requestBody = {
  //     "version" : 2,
  //     "type"    : "query",
  //     "query"   : search,
  //     "places"  : []
  //   };
  //   console.log(process.env.SERVICE_URL);
  //   console.log(requestBody);
  //
  //   return fetch('http://' + location.host + '/query', {
  //     method: "POST",
  //     body: JSON.stringify(requestBody)
  //   });
  // }

  /**
   * Updates the trip state
   */
  updateTrip(trip) {
    this.setState({trip});
  }

  /**
   * Updates the query state
   */
  updateQuery(query) {
    this.setState({query});
  }

  /**
   * Updates trip.title
   */
  updateTitle(title) {
    let trip = this.state.trip;
    trip.title = title;
    this.setState({trip});
    console.log("Title:" + this.state.trip.title);
    //console.log("Distance is: " + trip.options.distance);//Correctly implements unitButton
    //console.log("updateTrip: " + JSON.stringify(this.state.trip));//Sanity check
  }

  render() {
    return <div id="application" className="container">
      <div className="row">
        <div className="col-12">
          <Options trip={this.state.trip}
                   distance={this.state.trip.options.distance}
                   optimization={this.state.trip.options.optimization}
                   updateTrip={this.updateTrip}/>
        </div>
        <div className="col-12">
          <Destinations trip={this.state.trip}
                        query={this.state.query}
                        places={this.state.trip.places}
                        plan={this.plan}
                        updateTrip={this.updateTrip}
                        updateQuery={this.updateQuery}/>
        </div>
        <div className="col-12">
          <Trip trip={this.state.trip}
                title={this.state.trip.title}
                plan={this.plan}
                updateTrip={this.updateTrip}
                updateTitle={this.updateTitle}/>
        </div>
      </div>
    </div>
  }
}

export default Application;