import React, {Component} from 'react';

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Options extends Component{
    constructor(props) {
        super(props);
        this.changeUnit = this.changeUnit.bind(this);
    }

    changeUnit(event) { // *should* Change the parent's (Application.js) options
        //console.log("Options: changeUnit Begin");
        if (event.target.value === "kilometers") { //m2k
            this.setState({distance: "kilometers"});
            console.log("changing Miles to Kilometers");
        }
        else { //k2m
            this.setState({distance: "miles"});
            console.log("changing Kilometers to Miles");
        }
        //this.props.updateOptions(event);
        //console.log("Options: changeUnit Complete");
        console.log("[After] options.distance: " + this.props.distance);
    }

    //changeOptimization(event) {}

    render() {
        return(
            <div id="options" className="card">
                <div className="card-header bg-info text-white">
                    Options
                </div>
                <div className="card-body">
                    <p>Highlight the options you wish to use.</p>
                    <div className="btn-group btn-group-toggle" data-toggle="buttons" onChange={this.changeUnit}>
                        <label className="btn btn-outline-dark active">
                            <input type="radio" value="miles" name="distance" defaultChecked/> Miles
                        </label>
                        <label className="btn btn-outline-dark">
                            <input type="radio" value="kilometers" name="distance"/> Kilometers
                        </label>
                    </div>
                </div>
            </div>
        )
    }
}

export default Options;