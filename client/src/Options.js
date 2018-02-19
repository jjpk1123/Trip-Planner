import React, {Component} from 'react';

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Options extends Component{
    constructor(props) {
        super(props);
        this.state = {
            m_btn : <label className="btn btn-outline-dark active">
                        <input type="radio" value="miles"      name="distance"/> Miles
                    </label>,
            k_btn : <label className="btn btn-outline-dark">
                        <input type="radio" value="kilometers" name="distance"/> Kilometers
                    </label>
        };
        this.changeUnit = this.changeUnit.bind(this);
    }

    changeUnit(event) { // Changes the parent's (Application.js) options
        //console.log("[Options] changeUnit Start");
        if (event.target.value === "kilometers") { // m2k
            //console.log("miles -> Kilos!");
            //sets "kilometers" to the active state
            this.setState({k_btn :
                    <label className="btn btn-outline-dark active">
                        <input type="radio" value="kilometers" name="distance"/> Kilometers
                    </label>});
            //sets "miles" to inactive
            this.setState({m_btn :
                    <label className="btn btn-outline-dark">
                        <input type="radio" value="miles"      name="distance"/> Miles
                    </label>});
        }
        else { // k2m
            //console.log("kilos -> Miles!");
            //sets "miles" to active state
            this.setState({m_btn :
                    <label className="btn btn-outline-dark active">
                        <input type="radio" value="miles"      name="distance"/> Miles
                    </label>});
            //sets "kilometers" to inactive
            this.setState({k_btn :
                    <label className="btn btn-outline-dark">
                        <input type="radio" value="kilometers" name="distance"/> Kilometers
                    </label>});
        }
        let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
        tempTrip.options.distance = event.target.value; //alters the distance field to reflect the newly-selected unit
        this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
        //console.log("[Options] changeUnit Complete");
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
                        {this.state.m_btn}
                        {this.state.k_btn}
                    </div>
                </div>
            </div>
        )
    }
}

export default Options;