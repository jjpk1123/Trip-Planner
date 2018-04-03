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
        type: "",
        title: "",
        options: {
          distance: "miles",
          optimization: "none",
          userUnit: "",
          userRadius: ""
        },
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      },
      query: { //query TFFI
        type: "",
        query: "",
        places: []
      },
      config: {
        type: "",
        version: 0,
        optimization: 0
      }
    };
    this.doTheConfig();
    this.updateTrip = this.updateTrip.bind(this);
    this.updateQuery = this.updateQuery.bind(this);
    this.updateTitle = this.updateTitle.bind(this);
  }

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
   * Sends a request file to server.
   */
  fetchConfigResponse() {
    return fetch('http://' + location.host + '/config', {
      method: "GET"
    });
  }

  /**
   * Initiates the process of calling the server (waiting) and updating config's state
   */
  async doTheConfig() {
    try {
      //console.log("Awaiting response from server: Config");
      let serverResponse = await this.fetchConfigResponse();
      let config = await serverResponse.json();

      //console.log(configTFFI);
      this.setState({config});
      console.log("Config=={v" + this.state.config.version + ", opt:" + this.state.config.optimization + "}");
      //console.log("Application.js::async doTheConfig(): fetchConfigResponse is done");
    } catch(err) {
      console.error("You hit an error in Application.js::async doTheConfig()");
      console.error(err);
    }
  }

  /**
   * Updates trip.title
   */
  updateTitle(title) {
    let trip = this.state.trip;
    trip.title = title;
    this.setState({trip});
    console.log("Title:" + this.state.trip.title);
  }

  render() {
    return <div id="application" className="cardBody">
      <div className="row">
        <div className="col-lg-8 col-xl-8">
          <Destinations trip={this.state.trip}
                        query={this.state.query}
                        places={this.state.trip.places}
                        updateTrip={this.updateTrip}
                        updateQuery={this.updateQuery}/>
        </div>
        <div className="col-lg-4 col-xl-4">
          <Options trip={this.state.trip}
                   distance={this.state.trip.options.distance}
                   optimization={this.state.trip.options.optimization}
                   configOptimizations={this.state.config.optimization}
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