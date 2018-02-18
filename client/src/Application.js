import React, {Component} from 'react';
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
                options : {distance: "kilometers"},
                places: [],
                distances: [],
                map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
            }
        }
        this.updateTrip = this.updateTrip.bind(this);
        this.updateOptions = this.updateOptions.bind(this);
        this.getCount = this.getCount.bind(this);
    }

    getCount() {
        if (this.state.trip.places === []) {
            alert("count=0");
            return 0;
        }
        return this.state.trip.places.length;
    }

    updateTrip(tffi){
        this.setState({trip:tffi});
        //console.log("updateTrip : " + JSON.stringify(this.state.trip)); //Sanity check, the trip is updated!
    }

    updateOptions(options){
        console.log(options);
        // update the options in the trip.
        alert(options);
        // TODO: something like {options(:||.)distance : options}
    }

    render() {
        return(
            <div id="application" className="container">
                <div className="row">
                    <div className="col-12">
                        <Options options={this.state.trip.options} updateOptions={this.updateOptions}/>
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