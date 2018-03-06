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

    };
    this.changeUnit = this.changeUnit.bind(this);
    this.retrieveOptim = this.retrieveOptim.bind(this);
    this.retrieveOptimString = this.retrieveOptimString.bind(this);
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

  retrieveOptim() {
    if (this.props.optimization === "0" || this.props.optimization === "none" || this.props.optimization === undefined) {
      return 0;
    } else if (this.props.optimization === "1") {
      return 1
    }
    return 0;
  }

  retrieveOptimString() {
    if (this.props.optimization === "0" || this.props.optimization === "none" || this.props.optimization === undefined) {
      return "longest";
    } else if (this.props.optimization === "1") {
      return "shortest";
    }
    return "3rr0r";
  }

  changeOptimization(userOptimization) {
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    tempTrip.options.optimization = userOptimization.target.value; //alters the optimization field to reflect the slider's value
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
    console.log("Optim: " + userOptimization.target.value);
  }

  render() {
    return <div id="options" className="card">
      <div className="card-header bg-info text-white">
        Options
      </div>
      <div className="card-body">
        <p>Select the desired:</p>
        <div className="row">

          <div className="col-xs-2 col-sm-6 col-md-4 col-lg-3 col-xl-3">
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
                         value={this.retrieveOptim()} onChange={this.changeOptimization}/>
                  <h6>Length: <b><big>{this.retrieveOptimString()}</big></b></h6>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  }
}

export default Options;