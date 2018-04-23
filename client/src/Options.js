import React, {Component} from 'react';
import { ButtonDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import { Button, ButtonGroup, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { Form, FormGroup, Label, Input } from 'reactstrap';

/**
 * Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary. hello.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Options extends Component {
  constructor(props) {
    super(props);
    this.changeUnit = this.changeUnit.bind(this);
    this.retrieveOptimizationValue = this.retrieveOptimizationValue.bind(this);
    this.changeOptimization = this.changeOptimization.bind(this);
    this.retrieveOptimizationString = this.retrieveOptimizationString.bind(this);
    this.dropdownToggle = this.dropdownToggle.bind(this);
    this.modalToggle = this.modalToggle.bind(this);
    this.modalCancel = this.modalCancel.bind(this);
    this.modalSubmit = this.modalSubmit.bind(this);
    this.updateCustomUnit = this.updateCustomUnit.bind(this);
    this.updateCustomRadius = this.updateCustomRadius.bind(this);
    this.optCardHeader = <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
      Options
    </h5>;
    this.state = {
      dropdownOpen: false,
      modal: false,
      customUnit: "",
      customRadius: ""
    };
  }

  dropdownToggle() {
    this.setState({
      customUnit: this.props.trip.options.userUnit,
      customRadius: this.props.trip.options.userRadius,
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  modalToggle() {
    this.setState({
      modal: !this.state.modal
    });
  }

  modalSubmit() {
    let temp = this.props.trip;
    temp.options.userUnit = this.state.customUnit;
    temp.options.userRadius = this.state.customRadius;
    this.props.updateTrip(temp);
    this.modalToggle();
  }

  modalCancel() {
    //Close the dang thang
    this.modalToggle();
  }

  updateCustomUnit(e) {
    console.log("customUnit: " + e.target.value);
    this.setState({
      customUnit: e.target.value
    });
  }

  updateCustomRadius(e) {
    console.log("customRadius: " + e.target.value);
    this.setState({
      customRadius: e.target.value
    });
  }

  /**
   * Called when user clicks on the unit_of_measurement button.
   * Changes the value in Application.js
   */
  changeUnit(e) { // Changes the parent's (Application.js) options
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    let unit = e.target.value;
    console.log(unit);

    if (unit !== "miles" && unit !== "kilometers" && unit !== "nautical miles") { // "bananas"
      tempTrip.options.distance = "user defined"; // saves as "user defined"
      //this.setState({customUnit: e.target.value});
    } else {
      tempTrip.options.distance = unit; //alters the distance field to reflect the newly-selected unit
    }
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
  }

  /**
   * Highlights the correct unit_of_measurement Button "live"
   */
  testActiveDropdown(unit) {
    return "btn " + (this.props.distance === unit ? " active text-white" : "");
  }

  /**
   * Returns a value between 0 and 100 for the slider to render.
   */
  retrieveOptimizationValue() {
    if (this.props.trip.options.optimization === "none") {
      return 0;
    }
    return (100 * parseFloat(this.props.trip.options.optimization));
  }

  /**
   * Returns which optimization level the slider is currently at
   * More information is found in optString()'s summary
   */
  retrieveOptimizationString() {
    if (!this.checkOptimize()) {
      //console.log("No optimization");
      return this.optString(0);
    }

    let configOpt = this.props.config.optimization;
    let opt = 1.0 / (configOpt + 1);
    let curr = parseFloat(this.props.trip.options.optimization);
    if (curr >= opt && curr < 2*opt) {
      //console.log("Nearest Neighbor");
      return this.optString(1);
    }
    else if (curr >= 2*opt && curr <= 3*opt) {
    //else if (curr >= 2*opt && curr < 3*opt) {  // use for 3-opt
      // console.log("2-opt");
      return this.optString(2);
    }
    else if (curr >= 3*opt && curr <= 4*opt) {
    //   console.log("3-opt");
       return this.optString(3);
     }
  }

  /**
   * Returns whichever optimization level is in the config.optimization field
   * @param index
   * @return description = {0="Longest", 1="Short", 2="Shorter", 3="Shortest"}
   */
  optString(index) {
    return ((this.props.config).optimizations[index])["description"];
  }

  /**
   * Returns true if the slider is greater than "longest".
   * @return false = do not optimize path
   */
  checkOptimize() {
    if ((this.props.trip).options.optimization === "none") {
      return false;
    }
    // console.log(this.props.trip.options.optimization);
    let conOpt = parseFloat(this.props.config.optimization);
    let opt = 1.0 / (conOpt + 1);
    let curr = parseFloat((this.props.trip).options.optimization);
    // console.log(curr + " " + opt); // debug the string
    return (curr >= opt);
  }

  /**
   * Called when the user changes the slider "live".
   */
  changeOptimization(userOptimization) {
    let newValue = userOptimization.target.value / 100;
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    tempTrip.options.optimization = "" + newValue; //alters the optimization field to reflect the slider's value
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
    //console.log("Optimization Selected: " + userOptimization.target.value);
    //console.log("trip.options.optim== " + newValue);
  }

  /**
   * Makes the "title" of the dropdown menu reflect the current distance unit.
   */
  dropdownTitle(){
    if (this.props.trip.options.distance === "user defined"){
      return this.props.trip.options.userUnit;
    }
    return this.props.trip.options.distance;
  }


  render() {
    const options = ['miles', 'kilometers', 'nautical miles', this.state.customUnit];
    let unique = 0;
    const dropdownItems =
      <ButtonDropdown isOpen={this.state.dropdownOpen} toggle={this.dropdownToggle}
                      data-toggle="buttons" >
        <DropdownToggle caret style={{backgroundColor: "#1E4D28"}}>
          {this.dropdownTitle()}
        </DropdownToggle>
        <DropdownMenu>
          {options.map((option) =>
            <DropdownItem active={this.props.trip.options.distance === option} value={option}
                          onClick={this.changeUnit} key={++unique} className={this.testActiveDropdown(option)}>
              {option}
            </DropdownItem>)}
        </DropdownMenu>
      </ButtonDropdown>;

    const customUnitForm = <Form>
      <FormGroup>
        <Label for = "userUnit">Unit</Label>
        <Input type = "text" name = "unit" id="userUnit"
               onChange={this.updateCustomUnit} placeholder="Enter your unit's name..." />
      </FormGroup>
      <FormGroup>
        <Label for = "userEarthRadius">Earth radius</Label>
        <Input type = "text" name = "earthRadius" id="userEarthRadius"
               onChange={this.updateCustomRadius} placeholder="Enter your unit's earth radius..." />

      </FormGroup>
    </Form>;

    const customUnitModal = <div>
      <Button style={{backgroundColor: "#c8c372"}} onClick={this.modalToggle}>Customize a unit</Button>
      <Modal isOpen={this.state.modal} toggle={this.modalToggle} className={this.props.className}>
        <ModalHeader toggle = {this.modalToggle}>Set up your custom unit below.</ModalHeader>
        <ModalBody>
          {customUnitForm}
        </ModalBody>
        <ModalFooter>
          <Button style={{backgroundColor: "#1E4D28"}} onClick={this.modalSubmit}>Submit</Button>{' '}
          <Button style={{backgroundColor: "#59595b"}}   onClick={this.modalCancel}>Cancel</Button>
        </ModalFooter>
      </Modal>
    </div>;


    return <div id="options" className="card">
      {this.optCardHeader}
      <div className="row">
        <div className="col">
          <div className="card-body">
            <h6 className="card-title">Distance unit:</h6>
            <ButtonGroup vertical >
              {dropdownItems}
              {customUnitModal}
            </ButtonGroup>
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
  }
}

export default Options;