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
    }

    //Load a file from "browse" button
    loadTFFI(event) {
        //Read the file
        let file = event.target.files[0];
        let reader = new FileReader(); //Hi heellloooo

        //When we call readAsText() below, it'll read as text and then call this function
        reader.onload = function(){

            //Parse what the reader read from the file reference
            let myObj = JSON.parse(reader.result);

            //Update the trip
            this.props.updateTrip(myObj); //Check Application.updateTrip, there's another
            //console.log("loadTFFI, myObj: " + JSON.stringify(myObj));   //Sanity check, check console for JSON string
            //alert(file.name + " is loaded");                            //You did it!
        }.bind(this); //<-- Piazza made me feel this may have been necessary... we'll see

        //Read the data into the FileReader object
        reader.readAsText(file);
    }

    render() {
        // getCount() returns the number of places in the trip file
        return (
            <div id="destinations" className="card">
                <div className="card-header bg-info text-white">
                    Destinations
                </div>
                <div className="card-body">
                    <p>Load destinations from a file.</p>
                    <div className="form-group" role="group">
                        <input type="file" className="form-control-file" onChange={this.loadTFFI} id="tffifile" />
                    </div>
                    <h5>There are {this.props.getCount()} destinations. </h5>
                </div>
            </div>
        )
    }
}

export default Destinations;