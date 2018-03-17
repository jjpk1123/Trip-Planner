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
    this.loadTFFI = this.loadTFFI.bind(this);
    this.myObj = "";
    this.updateSearch = this.updateSearch.bind(this);
    this.search = "";
    this.fetchQueryResponse = this.fetchQueryResponse.bind(this);
    this.query = this.query.bind(this);
  }

  //Load a file from "browse" button
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
      //here we check that places have all required fields (ID, Name, Lat, Long)
      for (let i = 0; i < this.myObj.places.length; i++) {
        if (this.myObj.places[i].id === undefined || this.myObj.places[i].id === "") {
          alert("You seem to be missing an ID for one or more of your places!");
          return;
        }
        if (this.myObj.places[i].name === undefined || this.myObj.places[i].name === "") {
          alert("You seem to be missing a name for one or more of your places!");
          return;
        }
        if (this.myObj.places[i].latitude === undefined) {
          alert("You seem to be missing a latitude for one or more of your places!");
          return;
        }
        if (this.myObj.places[i].longitude === undefined) {
          alert("You seem to be missing a longitude for one or more of your places!");
          return;
        }
      }

      //IF map is undefined by TFFI, default it to blank CO map
      if (this.myObj.map === undefined || this.myObj.map === "") {
        console.log("Map not provided; defaulting.");
        this.myObj.map = "";
      }
      //If there are no distances defined, create an empty array of distances
      if (this.myObj.distances === undefined) {
        console.log("Distances not provided; defaulting to empty array");
        this.myObj.distances = [];
      }
      //if there is no type defined, let the user know their file is invalid
      if (this.myObj.type === undefined) {
        alert("Sorry, it looks like you need a type in your TFFI file.");
        return;
      }
      //if there is no options defined, set them to default
      if (this.myObj.options === undefined) {
        this.myObj.options = {"distance": "miles", "optimization": "none"};
        console.log("no options provided; defaulting to units of miles, optimization of none")
      }
      //if there is no distance defined in options, set it to default miles
      if (this.myObj.options.distance === undefined) {
        this.myObj.options.distance = "miles";
        console.log("Distance unit not provided; defaulting to miles")
      }
      //if there is no optimization defined in options, default it to none
      if (this.myObj.options.optimization === undefined) {
        this.myObj.options.optimization = "none";
        console.log("Optimization not provided; defaulting to none")
      }
      //if there are no places, let the user know their TFFI file is invalid
      if (this.myObj.places === undefined) {
        alert("Sorry, it looks like you need some places in your TFFI file.");
        return;
      }

      //File validation complete
      //Update the trip
      this.props.updateTrip(this.myObj); //Check Application.updateTrip, there's another

      console.log("loadTFFI, myObj: " + JSON.stringify(this.myObj));   //Sanity check, check console for JSON string
      //alert(file.name + " is loaded");                            //You did it!
    }.bind(this); //<-- Piazza made me feel this may have been necessary... we'll see

    //Read the data into the FileReader object
    reader.readAsText(file);
  }

  getCount() { // "there are ?? destinations" in the user-provided TFFI file.
    return this.props.places.length;
  }
  updateSearch(event) {

    this.search = event.target.value;
    console.log("Search: " + this.search);
  }


  fetchQueryResponse(){
    let requestBody = {
      "version" : 2,
      "type"    : "query",
      "query"   : this.search,
      "places"  : []
    };


    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + location.host + '/query', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async query(){
    try {
      console.log("Awaiting response from server: Query");
      let serverResponse = await this.fetchQueryResponse();

      console.log("Async Query(): fetchResponse is done");
    } catch(err) {
      console.error("You hit an error in async Query()");
      console.error(err);
    }
  }






  render() {
    // getCount() returns the number of places in the trip file
    return (
      <div id="destinations" className="card">
        <h5 className="card-header bg-info text-white">
          Destinations
        </h5>
        <div className="card-body">
          <h6>Load destinations or search our database:</h6>
          <div className="row">
            <div className="col-xs-2 col-sm-6 col-md-4 col-lg-4 col-xl-4">
              <div className="card-body">
                <h6 className="card-title">Load from a file:</h6>
                <div className="form-group" role="group">
                  <input type="file" className="form-control-file" onChange={this.loadTFFI} id="tffifile"/>
                </div>
              </div>
            </div>

            <div className="col-xs-2 col-sm-6 col-md-8 col-lg-8 col-xl-8">

              <div className="card-body">
                <h6 className="card-title">Search:</h6>
                <div className="input-group" role="group">
                  <input type="text" className="form-control" placeholder="Search..." onChange={this.updateSearch}/>
                  <span className="input-group-btn">
                <button className="btn btn-primary" onClick={this.query} type="button">Search</button>
              </span>
                </div>
              </div>
            </div>
          </div>
          <h5>There are <b>{this.getCount()}</b> destinations</h5>
        </div>
      </div>
    )
  }
}

export default Destinations;