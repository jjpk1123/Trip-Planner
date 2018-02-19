import React, {Component} from 'react';
//import update from 'react-addons-update';
import Options from './Options';
import Destinations from './Destinations';
import Trip from './Trip';

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
    constructor(props){
        super(props);
        this.state = {
            trip: { // default TFFI
                type: "trip",
                title: "",
                options : {distance: "miles", optimization: "none"},
                places: [],
                distances: [],
                map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
            }
        };
        this.updateTrip = this.updateTrip.bind(this);
        //this.updateOptions = this.updateOptions.bind(this);
        this.getCount = this.getCount.bind(this);
    }

    getCount() { // "there are ?? destinations" in the user-provided TFFI file.
        return this.state.trip.places.length;
    }

    updateTrip(tffi) {
        this.setState({trip:tffi});
        //console.log("updateTrip : " + JSON.stringify(this.state.trip)); //Sanity check, the trip is updated!
    }

   /* updateOptions(event) { following belongs in <Options /> below: updateOptions={this.updateOptions}
        //console.log("Application: updateOptions Begin");
        const newUnit = event.target.value;
        //this.setState({trip.options.distance : newUnit}); //Of course it's not this easy
        if (newUnit === "miles") { // miles to kilometers
            this.setState({trip: {...this.state.trip, options: {...this.state.trip.options, distance : "miles"}}});
        }
        else { // kilometers to miles
            this.setState({trip: {...this.state.trip, options: {...this.state.trip.options, distance : "kilometers"}}});

        }
        //console.log("Application: updateOptions Complete " + this.state.trip.options.distance);
    }*/

    render() {
        return(
            <div id="application" className="container">
                <div className="row">
                    <div className="col-12">
                        <Options distance={this.state.trip.options.distance} optimization={this.state.trip.options.optimization} />
                    </div>
                    <div className="col-12">
                        <Destinations trip={this.state.trip} getCount={this.getCount} updateTrip={this.updateTrip}/>
                    </div>
                    <div className="col-12">
                        <Trip trip={this.state.trip} updateTrip={this.updateTrip} />
                    </div>
                </div>
            </div>
        )
    }
}

export default Application;