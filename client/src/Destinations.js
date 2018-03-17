import React, {Component} from 'react';

/* Destinations reside in the parent object so they may be shared
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
    this.destCardHeader = <div className="card-header bg-info text-white">
                            Destinations
                          </div>;

    this.loadTFFI = this.loadTFFI.bind(this);
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
        console.log("There was an error reading the JSON file!");
        alert("Sorry, that doesn't look like a valid JSON file.");
        return;
      }

      if (this.validateTFFI() === false) {
        return; //File does not contain one of the necessary fields.
      }
      //File validation complete

      //Update the trip
      this.props.updateTrip(this.myObj); //Check Application.updateTrip, there's another

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
    this.validateVersion();//If version is undefined, set version to default -- 1

    if (this.validateType() === false) {
      return false;//If there is no type defined, fail
    }

    this.validateTitle();//If title is undefined, set title to default -- "My Trip"

    this.validateOptions();//If options is undefined, set options to default -- "miles", "none"

    if (this.validatePlaces() === false) {
      return false;//If places (doesn't exist|length<1), fail
    }

    if (this.validateIndividualPlaces() === false) {
      return false;//If not all places in TFFI contain valid fields, fail
    }

    this.validateDistances();//If distances is undefined, set distances to default -- []

    this.validateMap();//If map is undefined, set map to default -- ""

    return true; // All TFFI fields are populated
  }

  /**
   * Inserts {"version": 1} if version element is not provided in TFFI file
   */
  validateVersion() {
    if (this.myObj.version === undefined) {
      console.log("version element not provided; defaulting to 1");
      this.myObj.version = 1;
    } else if (this.myObj.version < 1) {
      console.log("version less than min; changing to 1");
      this.myObj.version = 1;
    } else if (this.myObj.version > 2) {
      console.log("version greater than max; changing to 2");
      this.myObj.version = 2;
    }
  }

  /**
   * Inserts {"title": "myTrip"} if the title element is (not provided | "") in TFFI file
   */
  validateTitle() {
    if ((this.myObj.title === undefined) || (this.myObj.title === "")) {
      console.log("title element not provided; defaulting to \"myTrip\"");
      this.myObj.title = "myTrip";
    }
  }

  /**
   * Returns true if the type element is provided in TFFI file
   */
  validateType() {
    if (this.myObj.type === undefined) {
      alert("type element not provided\nQuitting...");
      return false;
    }
    return true;
  }

  /**
   * Inserts {"distance": "miles", "optimization": "0"}
   * if options element not provided in TFFI file
   */
  validateOptions() {
    if (this.myObj.options === undefined) {
      //if options element does not exist, default it to {miles, 0}
      console.log("options field not provided");
      console.log("defaulting to {\"distance\": \"miles\", \"optimization\": \"0\"}")
      this.myObj.options = (this.myObj.version === 1) ?
          {"distance": "miles", "optimization": "none"}   //if v1: "none"
          : {"distance": "miles", "optimization": "0.0"}; //if v2: "0.0"
    } else if (this.myObj.options.distance === undefined) {
      //if there is no distance defined in options, default it to {miles}
      console.log("options.distance not provided; defaulting to \"miles\"");
      this.myObj.options.distance = "miles";
    } else if (this.myObj.options.optimization === undefined) {
      //if there is no optimization defined in options, default it to {none|0.0}
      console.log("options.optimization not provided; defaulting to \"0\"");
      this.myObj.options.optimization = (this.myObj.version === 1) ? "none" : "0.0";
    }
  }

  /**
   * Return true if there are 1 or more places provided in TFFI file
   */
  validatePlaces() {
    //If places is not provided, let the user know their TFFI file is invalid
    if (this.myObj.places === undefined) {
      console.log("places field does not exist");
      alert("You need to define some places in your TFFI file.");
      return false;
    }
    //If there are 0 places provided in TFFI, fail
    if ((this.myObj.places).length === 0) {
      console.log("places field does not contain any places");
      alert("Your TFFI file needs at least 1 place in order to make a trip");
      return false;
    }
    return true;
  }

  /**
   * Returns true if all places have validated all required fields
   */
  validateIndividualPlaces() {
    for (let i = 0; i < (this.myObj.places).length; i++) {
      let place = this.myObj.places[i];

      // ID check
      if ((place.id === undefined) || (place.id === "")) {
        console.log("places[" + (i+1) + "] does not contain an id");
        alert("You seem to be missing an ID for one or more of your places!");
        return false;
      }
      // Name check
      if (place.name === undefined || place.name === "") {
        console.log("places[" + (i+1) + "] does not contain a name");
        alert("You seem to be missing a name for one or more of your places!");
        return false;
      }
      // Latitude check
      if (place.latitude === undefined) {
        console.log("places[" + (i+1) + "] does not contain a latitude");
        alert("You seem to be missing a latitude for one or more of your places!");
        return false;
      }
      //Longitude check
      if (place.longitude === undefined) {
        console.log("places[" + (i+1) + "] does not contain a longitude");
        alert("You seem to be missing a longitude for one or more of your places!");
        return false;
      }
    }
    return true;
  }

  /**
   * Inserts {"distances": []} if distances element is not provided in TFFI file
   */
  validateDistances() {
    if (this.myObj.distances === undefined) {
      console.log("distances not provided; defaulting to empty array");
      this.myObj.distances = [];
    }
  }

  /**
   * Inserts {"map": ""} if map element is not provided in TFFI file
   */
  validateMap() {
    if (this.myObj.map === undefined) {
      console.log("map not provided; defaulting to blank CO map");
      this.myObj.map = "";
    }
  }

  render() {
    return (
      <div id="destinations" className="card">
        {this.destCardHeader}
        <div className="card-body">
          <p>Load destinations from a file:</p>
          <div className="form-group" role="group">
            <input type="file" className="form-control-file" onChange={this.loadTFFI} id="tffifile"/>
          </div>
          <p>There are <big><big><b>{(this.props.places).length}</b></big></big> destinations</p>
        </div>
      </div>
    )
  }
}

export default Destinations;