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

    changeUnit(event) { // Changes the parent's (Application.js) options
        let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
        tempTrip.options.distance = event.target.value; //alters the distance field to reflect the newly-selected unit
        this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
    }

    testActiveBtn(unit) {
        return "btn btn-outline-dark " + (this.props.distance === unit ? "active" : "");
    }

    // TODO: Once we have options to display, namely clickable "Show Me These" checkboxes and optimization,
    // someone will need to mimic the unitButtons to re-render.
    // changeOptimization(event) {}

    render() {
        return(
            <div id="options" className="card">
                <div className="card-header bg-info text-white">
                    Options
                </div>
                <div className="card-body">
                    <p>Highlight the options you wish to use.</p>
                    <div className="btn-group btn-group-toggle" data-toggle="buttons" onChange={this.changeUnit}>
                        <label className={this.testActiveBtn("miles")}>
                            <input type="radio" value="miles"      name="distance"/> Miles
                        </label>
                        <label className={this.testActiveBtn("kilometers")}>
                            <input type="radio" value="kilometers" name="distance"/> Kilometers
                        </label>
                    </div>
                </div>
            </div>
        )
    }
}

export default Options;