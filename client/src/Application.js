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
      hostname: location.hostname,
      port: location.port,
      trip: { // trip TFFI
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
      query: { // query TFFI
        version: 0,
        type: "",
        limit: 0,
        query: "",
        filters: [],
        places: []
      },
      config: { // config TFFI
        type: "",
        version: 0,
        filters: [],
        maps: [],
        optimization: 0,
        optimizations: [{label: "No optimization", description: "Longest"}],
        units: []
      }
    };
    this.doTheConfig();
    this.updateHostAndPort = this.updateHostAndPort.bind(this);
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
   * Updates trip.title
   */
  updateTitle(title) {
    let trip = this.state.trip;
    trip.title = title;
    this.setState({trip});
    console.log("Title:" + this.state.trip.title);
  }

  updateHostAndPort(host, port){
    console.log("\nApplication.js is updating host and portname...");
    console.log("hostname will be: " + host);
    console.log("port will be: " + port);

    this.setState({
      hostname: host,
      port: port});

    console.log("\nApplication.js has updated...");
    console.log("hostname is: " + this.state.hostname);
    console.log("port is: " + this.state.port);
  }

  /**
   * Sends a request file to server.
   */
  fetchConfigResponse() {
    return fetch('http://' + location.host + '/config', {
      method: "GET",
      header: {'Access-Control-Allow-Origin': '*'}
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
//      console.log("Config:{\nversion=" + this.state.config.version + ", maps=[" + this.state.config.maps
//          + "], opt=" + this.state.config.optimization + ", units=[" + this.state.config.units + "]\n}"); // debug
    } catch(err) {
      console.error("You hit an error in Application.js::async doTheConfig()");
      console.error(err);
    }
  }

  render() {
    return <div id="application" className="cardBody">
      <div className="row">
        <div className="col-lg-8 col-xl-8">
          <Destinations trip={this.state.trip}
                        query={this.state.query}
                        places={this.state.trip.places}
                        updateTrip={this.updateTrip}
                        updateQuery={this.updateQuery} />
        </div>
        <div className="col-lg-4 col-xl-4">
          <Options trip={this.state.trip}
                   config={this.state.config}
                   distance={this.state.trip.options.distance}
                   hostname={this.state.hostname}
                   port={this.state.port}
                   updateHostAndPort={this.updateHostAndPort}
                   updateTrip={this.updateTrip}/>
        </div>
        <div className="col-12">
          <Trip trip={this.state.trip}
                title={this.state.trip.title}
                config={this.state.config}
                updateTrip={this.updateTrip}
                updateTitle={this.updateTitle}/>
        </div>
      </div>
    </div>
  }
}

export default Application;