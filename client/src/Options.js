import React, {Component} from 'react';

/**
 * Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */

function CustomUnits(props){
  const usingCustomUnits = props.customUnits;
  if (usingCustomUnits){
    return <p>true</p>;
  }
  else{
    return <p>false</p>;

  }
}


function UseCustom(props){
  props.customUnits = true;
}

function CustomUnitForm(props){

}

class Options extends Component {
  constructor(props) {
    super(props);
    this.changeUnit = this.changeUnit.bind(this);
    this.retrieveOptimizationValue = this.retrieveOptimizationValue.bind(this);
    this.changeOptimization = this.changeOptimization.bind(this);
    this.retrieveOptimizationString = this.retrieveOptimizationString.bind(this);
    this.customUnits = false;
    this.optCardHeader = <h5 className="card-header bg-info text-white">
        Options
      </h5>;
  }

  /**
   * Called when user clicks on the unit_of_measurement button.
   * Changes the value in Application.js
   */
  changeUnit(userUnit) { // Changes the parent's (Application.js) options
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    //if(userUnit.target.value == "custom"){ this.customUnits = true;}
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
    if (!this.checkOptimize()) {
      //console.log("No-opt");
      return "longest";
    }
    //console.log("(" + this.props.configOptimizations + ") + 1");
    let conOpt = parseFloat(this.props.configOptimizations);
    let opt = 1.0 / (conOpt + 1);
    let curr = parseFloat(this.props.optimization);
    if (curr >= opt) { // if (curr >= opt && curr < 2*opt) {
      //console.log("NearestNeighbor");
      return "shortest"; // "short"
    }
    // else if (curr >= 2*opt && curr < 3*opt) { // when we run 2-opt, make sure to less than or ***EQUAL*** 3*opt
    //   console.log("2-opt");
    //   return "shorter";
    // }
    // else if (curr >= 3*opt && curr <= 4*opt) {
    //   console.log("3-opt");
    //   return "shortest";
    // }
  }

  /**
   * Returns true if the slider is greater than "longest".
   * @return false = do not optimize path
   */
  checkOptimize() {
    if (this.props.optimization === "none") {
      return false;
    }
    let conOpt = parseFloat(this.props.configOptimizations);
    let opt = 1.0 / (conOpt + 1);
    let curr = parseFloat(this.props.optimization);
    return (curr >= opt);
  }

  /**
   * Returns true if the slider is greater than "longest".
   * @return false = do not optimize path
   */
  checkOptimize() {
    if (this.props.optimization === "none") {
      return false;
    }
    let conOpt = parseFloat(this.props.configOptimizations);
    let opt = 1.0 / (conOpt + 1);
    let curr = parseFloat(this.props.optimization);
    return (curr >= opt);
  }

  /**
   * Called when the user changes the slider "live".
   */
  changeOptimization(userOptimization) {
    //this.props.doTheConfig();
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
          <div className="col">
            <div className="card-body">
              <h6 className="card-title">Unit of distance:</h6>
              <div className="btn-group btn-group-toggle" data-toggle="buttons" onChange={this.changeUnit}>
                <label className={this.testActiveBtn("miles")}>
                  <input type="radio" value="miles" name="distance"/> Miles
                </label>
                <label className={this.testActiveBtn("kilometers")}>
                  <input type="radio" value="kilometers" name="distance"/> Kilometers
                </label>
                <label className={this.testActiveBtn("nautical miles")}>
                  <input type="radio" value="nautical miles" name="distance"/> Nautical Miles
                </label>
              <div className="btn-group btn-group-toggle" data-toggle="buttons" onChange={UseCustom}>
                <label className={this.testActiveBtn("custom")}>
                  <input type="radio" value="custom" name="distance"/> Custom
                </label>
              </div>
              </div>
              <CustomUnits/>
            </div>
          </div>

          <div className="col">
            <div className="card-body">
              <h6 className="card-title">Round-Trip length:</h6>
                <div>
                  <input type="range" className="slider" min="0" max="99" step="1" id="myRange"
                         value={this.retrieveOptimizationValue()} onChange={this.changeOptimization}/>
                  <h6>Length: <b>{this.retrieveOptimizationString()}</b></h6>
                </div>
            </div>
          </div>

        </div>

      </div>
    </div>
  }
}

export default Options;