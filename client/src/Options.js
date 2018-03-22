import React, {Component} from 'react';

/**
 * Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Options extends Component {
  constructor(props) {
    super(props);
    this.optCardHeader = <h5 className="card-header bg-info text-white">
        Options
      </h5>;
    this.changeUnit = this.changeUnit.bind(this);
    this.retrieveOptimizationValue = this.retrieveOptimizationValue.bind(this);
    this.changeOptimization = this.changeOptimization.bind(this);
    this.retrieveOptimizationString = this.retrieveOptimizationString.bind(this);
  }

  /**
   * Called when user clicks on the unit_of_measurement button.
   * Changes the value in Application.js
   */
  changeUnit(userUnit) { // Changes the parent's (Application.js) options
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    tempTrip.options.distance = userUnit.target.value; //alters the distance field to reflect the newly-selected unit
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
  }

  /**
   * Highlights the correct unit_of_measurement Button "live"
   */
  testActiveBtn(unit) {
    return "btn btn-outline-dark " + (this.props.distance === unit ? "active" : "");
  }

  /**
   * Returns a value between 0 and 100 for the slider to render.
   */
  retrieveOptimizationValue() {
    if (this.props.optimization === "none") {
      return 0;
    }
    return (100 * parseFloat(this.props.optimization));
  }

  /**
   * Returns which optimization level the slider is currently at
   */
  retrieveOptimizationString() {
    if (this.props.optimization === "none") {
      return "longest";
    }
    let opt = 1.0 / 2; //@TODO Options.checkOptimize
    let curr = parseFloat(this.props.optimization);
    //console.log("Optimization value equals " + curr);
    if (curr < opt) {
      //console.log("longest");
      return "longest";
    } else if (curr >= opt) {
      //console.log("shortest");
      return "shortest";
    }
  }

  /**
   * Called when the user changes the slider "live"
   */
  changeOptimization(userOptimization) {
    this.props.doTheConfig();
    let newValue = userOptimization.target.value / 100;
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    tempTrip.options.optimization = "" + newValue; //alters the optimization field to reflect the slider's value
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
    //console.log("Optimization Selected: " + userOptimization.target.value);
    //console.log("trip.options.optim== " + newValue);
  }

  render() {
    return <div id="options" className="card">
      {this.optCardHeader}
      <div className="card-body">
        <h6>Select the desired:</h6>
        <div className="row">
          <div className="col-xs-2 col-sm-6 col-md-4 col-lg-4 col-xl-4">
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
          <div className="col-xs-2 col-sm-6 col-md-8 col-lg-8 col-xl-8">
              <div className="card-body">
                <h6 className="card-title">Round-Trip length:</h6>
                <div>
                  <input type="range" className="slider" min="0" max="99" step="1" id="myRange"
                         value={this.retrieveOptimizationValue()} onChange={this.changeOptimization}/>
                  <h6>Length: <b><big>{this.retrieveOptimizationString()}</big></b></h6>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  }
}

export default Options;