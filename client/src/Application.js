import React, {Component} from 'react';
import Options from './Options';
import Destinations from './Destinations';
import Trip from './Trip';

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
  constructor(props) {
    super(props);
    this.state = {
      trip: { // default TFFI
        type: "trip",
        title: "",
        options: {
          distance: "miles",
          optimization: "none"
        },
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      }
    };
    this.updateTrip = this.updateTrip.bind(this);
    this.getCount = this.getCount.bind(this);
    this.getRoundTripDistance = this.getRoundTripDistance.bind(this);
  }

  getCount() { // "there are ?? destinations" in the user-provided TFFI file.
    return this.state.trip.places.length;
  }

  getRoundTripDistance() {
    let sum = 0;
    let howMany = this.state.trip.distances.length;
    for (var i=0 ; i < howMany ; ++i) {
      let d = this.state.trip.distances[i];
      sum += d;
    }
    //console.log("Total distance: " + sum); //WooHoo!
    return sum;
  }

  updateTrip(tffi) {
    this.setState({trip: tffi});
    //console.log("Distance is: " + tffi.options.distance); // Correctly implements unitButton
    //console.log("updateTrip : " + JSON.stringify(this.state.trip)); // Sanity check, the trip is updated!
  }

  render() {
    return (
        <div id="application" className="container">
          <div className="row">
            <div className="col-12">
              <Options trip={this.state.trip}
                       distance={this.state.trip.options.distance}
                       optimization={this.state.trip.options.optimization}
                       updateTrip={this.updateTrip}/>
            </div>
            <div className="col-12">
              <Destinations trip={this.state.trip}
                            getCount={this.getCount}
                            updateTrip={this.updateTrip}/>
            </div>
            <div className="col-12">
              <Trip trip={this.state.trip}
                    updateTrip={this.updateTrip}
                    getRoundTripDistance={this.getRoundTripDistance}/>
            </div>
          </div>
        </div>
    )
  }
}

export default Application;