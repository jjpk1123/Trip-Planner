import React, {Component} from 'react';

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Options extends Component {
  constructor(props) {
    super(props);
    this.state = {
      optim: "0",
      final: "longest",
    };
    this.changeUnit = this.changeUnit.bind(this);
    this.changeOptimization = this.changeOptimization.bind(this);
  }

  changeUnit(userUnit) { // Changes the parent's (Application.js) options
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    tempTrip.options.distance = userUnit.target.value; //alters the distance field to reflect the newly-selected unit
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
  }

  testActiveBtn(unit) {
    return "btn btn-outline-dark " + (this.props.distance === unit ? "active" : "");
  }

  changeOptimization(userOptimization) {
    if (userOptimization.target.value === "0") {
      this.setState({optim: "0"});
      this.setState({final: "longest"});
    } else if (userOptimization.target.value === "1") {
      this.setState({optim: "1"});
      this.setState({final: "shortest"});
    }
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    tempTrip.options.optimization = userOptimization; //alters the optimization field to reflect the slider's value
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
    console.log("Optim: " + userOptimization.target.value);
  }

  render() {
    return <div id="options" className="card">
      <div className="card-header bg-info text-white">
        Options
      </div>
      <h6>Select the desired:</h6>
      <div className="row">
        <div className="col-xs-1 col-sm-1 col-md-1 col-lg-1 col-xl-1">
        </div>

        <div className="col-xs-2 col-sm-5 col-md-4 col-lg-3 col-xl-3">
          <div className="card">
            <div className="card-body">
              <h6 className="card-title">Unit of distance:</h6>
              <div className="btn-group btn-group-toggle" data-toggle="buttons" onChange={this.changeUnit}>
                <label className={this.testActiveBtn("miles")}>
                  <input type="radio" value="miles" name="distance"/> Miles
                </label>
                <label className={this.testActiveBtn("kilometers")}>
                  <input type="radio" value="kilometers" name="distance"/> Kilometers
                </label>
              </div>
            </div>
          </div>
        </div>

        <div className="col-xs-1 col-sm-1 col-md-1 col-lg-1 col-xl-1">
        </div>

        <div className="col-xs-2 col-sm-5 col-md-4 col-lg-3 col-xl-3">
          <div className="card">
            <div className="card-body">
              <h6 className="card-title">Round-Trip length:</h6>
              <div>
                <input type="range" className="slider" min="0" max="1" id="myRange"
                       value={this.state.optim} onChange={this.changeOptimization}/>
                <h6>Length: <b><big>{this.state.final}</big></b></h6>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  }
}

export default Options;