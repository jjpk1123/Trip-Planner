import React, {Component} from 'react';
import Query from './Query';
import Trip from './Trip';

/**
 * Destinations reside in the parent object so they may be shared
 * with the Trip object.
 * Renders the current destination list.
 * Loads destinations from files.
 * Finds destinations in a database.
 * Displays the current number of destinations
 */
class Destinations extends Component {
  constructor(props) {
    super(props);
    this.myObj = "";
    this.loadTFFI = this.loadTFFI.bind(this);
    this.destCardHeader = <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
        Destinations
      </h5>;
  }

  /**
   * Loads a file from "browse" button. Checks if file contains all necessary fields.
   * If a some fields do not exist, it creates default values for those fields.
   */
  loadTFFI(event) {
    //Read the file
    let file = event.target.files[0];
    let reader = new FileReader();

    //When we call readAsText() below, it'll read as text and then call this function
    reader.onload = function () {
      //Parse what the reader read from the file reference
      //If invalid JSON, let the user know in the catch statement.
      try {
        this.myObj = JSON.parse(reader.result);
      } catch (e) {
        console.log("There was an error reading the JSON file; Quitting...");
        alert("Sorry, that doesn't look like a valid JSON file.");
        return;
      }

      if (!this.validateTFFI()) {
        return; //File does not contain 1 or more of the necessary fields.
      }
      //File validation complete

      //Update the trip
      this.props.updateTrip(this.myObj); //Check Application.updateTrip, there's another

      Trip.resetState();

      //console.log("loadTFFI, myObj: " + JSON.stringify(this.myObj));   //Sanity check, check console for JSON string
      //alert(file.name + " is loaded");                            //You did it!
    }.bind(this); //<-- Piazza made me feel this may have been necessary... we'll see

    //Read the data into the FileReader object
    reader.readAsText(file);
  }

  /**
   * Return true if all required fields are able to be rendered
   */
  validateTFFI() {
    let rtnBool = true;
    this.validateVersion();//If version is undefined, set version to default -- 1

    if (this.validateType() === false) {
      return false;//If there is no type defined, fail
    }

    this.validateTitle();//If title is undefined, set title to default -- "My Trip"

    if (this.validateOptions() === false) {//If options is undefined, set options to default -- "miles", "none"
      rtnBool = false;//User failed to include a usable userRadius
    }
    if (this.validatePlaces() === false) {
      rtnBool = false;//If places (doesn't exist || length<1), fail
    }
    if (this.validateIndividualPlaces() === false) {
      rtnBool = false;//If not all places in file contain valid fields, fail
    }

    this.validateDistances();//If distances is undefined, set distances to default -- []

    this.validateMap();//If map is undefined, set map to default -- ""

    return rtnBool; // All trip fields are populated
  }

  /**
   * Inserts {"version": 1} if version element is not provided in file
   */
  validateVersion() {
    if (!Destinations.checkIfStringExists(this.myObj.version)) {
      console.log("version element not provided; defaulting to 1");
      this.myObj.version = 1;
    } else if (this.myObj.version < 1) {
      console.log("version less than min; changing to 1");
      this.myObj.version = 1;
    } else if (this.myObj.version > 3) {
      console.log("version greater than max; changing to 3");
      this.myObj.version = 3;
    }
  }

  /**
   * Inserts {"title": "myTrip"} if the title element is (not provided | "") in file
   */
  validateTitle() {
    if (!Destinations.checkIfStringExists(this.myObj.title)) {
      console.log("title element not provided; defaulting to \"My Trip\"");
      this.myObj.title = "My Trip";
    }
  }

  /**
   * Returns true if the type element is provided in file
   */
  validateType() {
    if (!Destinations.checkIfStringExists(this.myObj.type)) {
      alert("type element not provided; Quitting...");
      return false;
    }
    return true;
  }

  /**
   * Inserts "distance" and "optimization" to trip if non-existent
   */
  validateOptions() {
    if (!Destinations.checkIfStringExists(this.myObj.options)) {
      this.insertOptions();
    }
    else if (!Destinations.checkIfStringExists(this.myObj.options.distance)) {
      console.log("options.distance not provided; defaulting to \"miles\"");
      this.myObj.options.distance = "miles";
    }
    else if (!Destinations.checkIfStringExists(this.myObj.options.optimization)) {
      this.changeOptimization("0.0", "options.optimization not provided; defaulting to \"none|0\"");
    }
    else {
      this.checkOptimizationValidity();
    }

    if (this.validateOptionsDistance() === false) {
      return false;
    }
  }

  /**
   * Called when the trip file does not have options field
   */
  insertOptions() {
    console.log("options field not provided");
    console.log("defaulting to {\"distance\": \"miles\", \"optimization\": \"0\"}");
    this.myObj.options = (this.myObj.version === 1) ?
        {"distance": "miles", "optimization": "none"}   //if v1: "none"
        : {"distance": "miles", "optimization": "0.0"}; //if v2: "0.0"

  }

  /**
   * Logs the optimization error message associated with the change
   * Changes the value of trip.options.optimization based on trip.version
   */
  changeOptimization(ver2Value, errorMsg) {
    console.log(errorMsg);
    this.myObj.options.optimization = (this.myObj.version === 1) ? "none" : ver2Value;
  }

  /**
   * Checks validity of trip.options.optimization
   */
  checkOptimizationValidity() {
    if (isNaN(parseFloat(this.myObj.options.optimization))) {
      this.changeOptimization("0.0", "options.optimization string== NaN; defaulting to \"none|0\"");
    }
    else if (parseFloat(this.myObj.options.optimization) < 0.0) {
      this.changeOptimization("0.0", "options.optimization string < 0.0; defaulting to \"none|0\"");
    }
    else if (parseFloat(this.myObj.options.optimization) > 1.0) {
      this.changeOptimization("1.0", "options.optimization string > 1.0; defaulting to \"none|1\"");
    }
  }

  /**
   * Makes sure the unit of distance is all lowercase.
   * If the user selected the "user defined" unit of distance, this makes sure there is a name
   * and a viable double for the radius.
   * @return false = Non-parsable userRadius
   */
  validateOptionsDistance() {
    this.myObj.options.distance = (this.myObj.options.distance).toLowerCase();
    let rtnBool = true;

    if (this.myObj.options.distance === "user defined") {
      if (!Destinations.checkIfStringExists(this.myObj.options.userUnit)) {
        console.log("No Unit name provided; defaulting to 'Custom Units'");
        this.myObj.options.userUnit = "Custom Units";
      }
      if (!Destinations.checkIfStringExists(this.myObj.options.userRadius)) {
        console.log("User-defined radius not provided; Failure.");
        alert("You need to include a radius if you're going to measure by user-defined unit!");
        rtnBool = false;
      }
      if (isNaN(Number(this.myObj.options.userRadius))) {
        console.log("User-defined radius is not a number; Failure.");
        alert("You need to include a valid user radius number if you're going to measure by user-defined unit!");
        rtnBool = false;
      }
      if (Number(this.myObj.options.userRadius) <= 0) {
        console.log("User-defined radius is <= 0; Failure.");
        alert("You need to include a Non-Negative & Non-Zero user radius number if you're going to measure by user-defined unit!");
        rtnBool = false;
      }
    }
    return rtnBool;
  }

  /**
   * Return true if there are 1 or more places provided in file
   */
  validatePlaces() {
    //If places is not provided, let the user know their file is invalid
    if (!Destinations.checkIfStringExists(this.myObj.places)) {
      console.log("places field does not exist; Quitting...");
      alert("You need to define some places in your file.");
      return false;
    }
    //If there are 0 places provided in file, fail
    if ((this.myObj.places).length === 0) {
      console.log("places field does not contain any places; Quitting...");
      alert("Your file needs at least 1 place in order to make a trip");
      return false;
    }
    return true;
  }

  /**
   * Returns true if all places have validated all required fields
   */
  validateIndividualPlaces() {
    for (let i = 0; i < (this.myObj.places).length; i++) {
      if (Destinations.undefinedOrEmpty(this.myObj.places[i]) === false) {
        return false;//a field in a place was not defined
      }
    }
    return true;//all places have fields, and able to be rendered
  }

  /**
   * Called from validateIndividualPlaces.
   * If a place does not have the 4 fields,
   */
  static undefinedOrEmpty(place) {
    let retBool = true;

    // Id/Name Check
    if (!Destinations.checkIfStringExists(place.longitude) || !Destinations.checkIfStringExists(place.name) ||
        !Destinations.checkIfStringExists(place.latitude)  || !Destinations.checkIfStringExists(place.id)) {
      alert("You seem to be missing a required field for one or more of your places!");
      retBool = false;
    }
    return retBool;
  }

  /**
   * Inserts {"distances": []} if distances element is not provided in file
   */
  validateDistances() {
    if (!Destinations.checkIfStringExists(this.myObj.distances)) {
      console.log("distances not provided; defaulting to empty array");
      this.myObj.distances = [];
    }
  }

  /**
   * Inserts {"map": ""} if map element is not provided in file
   */
  validateMap() {
    if (this.myObj.map === undefined) {
      console.log("map not provided; defaulting to blank CO map");
      this.myObj.map = "";
    }
  }

  /**
   * If the field exists, and is populated, returns true
   * @param item
   * @return false = the String was not defined or empty string in loaded file
   */
  static checkIfStringExists(item) {
    // if (item === "" || item
    return (item);
  }

  /**
   * Renders the Options card on the client
   */
  render() {
    return <div id="destinations" className="card" >
      {this.destCardHeader}
        <div className="row">
          <div className="col-xs-2 col-sm-6 col-md-4 col-lg-4 col-xl-4">
            <div className="card-body">
              <h6 className="card-title">Load from a file:</h6>
              <div className="form-group" role="group">
                <input type="file" className="form-control-file" onChange={this.loadTFFI}/>
              </div>
              <h5>There are <b>{(this.props.places).length}</b> destinations</h5>
            </div>
          </div>
          <div className="col-xs-12 col-sm-12 col-md-8 col-lg-8 col-xl-8">
            <Query  trip={this.props.trip}
                    query={this.props.query}
                    config={this.props.config}
                    hostname={this.props.hostname}
                    port={this.props.port}
                    updateTrip={this.props.updateTrip}
                    updateQuery={this.props.updateQuery}/>
          </div>
        </div>
      </div>
  }
}

export default Destinations;