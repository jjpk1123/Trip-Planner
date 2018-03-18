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
      trip: {//default TFFI
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
    this.updateTrip = this.updateTrip.bind(this);
    this.updateQuery = this.updateQuery.bind(this);
    this.updateTitle = this.updateTitle.bind(this);
  }

  /**
   * Updates the trip state
   */
  updateTrip(tffi) {
    this.setState({trip: tffi});
  }

  /**
   * Updates the query state
   */
  updateQuery(tffi) {
    this.setState({query: tffi});
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
                          query = {this.state.query}
                          places={this.state.trip.places}
                          updateQuery={this.updateQuery}
                          updateTrip={this.updateTrip}/>
          </div>
          <div className="col-12">
            <Trip trip={this.state.trip}
                  title={this.state.trip.title}
                  updateTrip={this.updateTrip}
                  updateTitle={this.updateTitle}/>
          </div>
        </div>
      </div>
  }
}

export default Application;