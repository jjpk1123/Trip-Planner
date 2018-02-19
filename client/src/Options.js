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
            console.log("miles -> Kilos!");
            this.setState({k_btn :
                    <label className="btn btn-outline-dark active">
                        <input type="radio" value="kilometers" name="distance"/> Kilometers
                    </label>});
            this.setState({m_btn :
                    <label className="btn btn-outline-dark">
                        <input type="radio" value="miles"      name="distance"/> Miles
                    </label>});
        }
        else { // k2m
            console.log("kilos -> Miles!");
            this.setState({m_btn :
                    <label className="btn btn-outline-dark active">
                        <input type="radio" value="miles"      name="distance"/> Miles
                    </label>});
            this.setState({k_btn :
                    <label className="btn btn-outline-dark">
                        <input type="radio" value="kilometers" name="distance"/> Kilometers
                    </label>});
        }
        let tempTrip = this.props.trip;
        tempTrip.options.distance = event.target.value;
        this.props.updateTrip(tempTrip);
        //console.log("[Options] changeUnit Complete");
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
                        {this.state.m_btn}
                        {this.state.k_btn}
                    </div>
                </div>
            </div>
        )
    }
}

export default Options;