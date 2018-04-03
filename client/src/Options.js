import React, {Component} from 'react';
import { ButtonDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import { Button, ButtonGroup, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { Form, FormGroup, Label, Input } from 'reactstrap';

/**
 * Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
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
    this.optCardHeader = <h5 className="card-header bg-info text-white">
        Options
      </h5>;
    this.dropdownToggle = this.dropdownToggle.bind(this);
    this.modalToggle = this.modalToggle.bind(this);
    this.state = {
      dropdownOpen: false,
      modal: false,
      unitOption: "Select your unit",
      customUnit: "user defined"
    };
  }

  dropdownToggle(){
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  modalToggle(){
    this.setState({
      modal: !this.state.modal
    });
  }

  /**
   * Called when user clicks on the unit_of_measurement button.
   * Changes the value in Application.js
   */
  changeUnit(e) { // Changes the parent's (Application.js) options
    //Easier to read or maintain if it's like this.
    let tempTrip = this.props.trip; //retrieves trip from parent (Application.js)
    let unit = e.target.value;

    //Do the things
    tempTrip.options.distance = unit; //alters the distance field to reflect the newly-selected unit
    this.state.unitOption = unit;
    this.props.updateTrip(tempTrip); //re-renders the client to show the changes made
  }

  /**
   * Highlights the correct unit_of_measurement Button "live"
   */
  testActiveDropdown(unit) {
    return "btn btn-outline-blue " + (this.props.distance === unit ? "active" : "");
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
      return "shorter"; // "short"
    }
    else if (curr >= 2*opt && curr <= 3*opt) { // when we run 2-opt, make sure to less than or ***EQUAL*** 3*opt
      //   console.log("2-opt");
      return "shortest";
    }
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
    //console.log(curr + " " + opt); // debug the string
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

  render() {
    const options = ['miles', 'kilometers', 'nautical miles', this.state.customUnit];
    const dropdownItems =
      <ButtonDropdown isOpen = {this.state.dropdownOpen} toggle={this.dropdownToggle}
                      data-toggle="buttons">
        <DropdownToggle caret color="primary">
          Select distance unit
        </DropdownToggle>
        <DropdownMenu>
          {options.map((option) =>
            <DropdownItem active={this.props.trip.options.distance === option} value={option}
              onClick={this.changeUnit} className={this.testActiveDropdown(option)}>
              {option}
              </DropdownItem>)}
          </DropdownMenu>
      </ButtonDropdown>;

    const customUnitForm = <Form>
      <FormGroup>
        <Label for = "userUnit">Unit</Label>
        <Input type = "unit" name = "unit" id="userUnit" placeholder="Enter your unit's name..." />
      </FormGroup>
      <FormGroup>
        <Label for = "userEarthRadius">Earth radius</Label>
        <Input type = "earthRadius" name = "earthRadius" id="userEarthRadius" placeholder="Enter your unit's earth radius..." />
      </FormGroup>
    </Form>;

    const customUnitModal = <div>
      <Button color = "secondary" onClick={this.modalToggle}>Customize a unit</Button>
      <Modal isOpen={this.state.modal} toggle={this.modalToggle} className={this.props.className}>
        <ModalHeader toggle = {this.modalToggle}>Set up your custom unit below.</ModalHeader>
        <ModalBody>
          {customUnitForm}
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={this.modalToggle}>Do something</Button>{' '}
          <Button color="secondary" onClick={this.modalToggle}>Cancel</Button>
        </ModalFooter>
      </Modal>
    </div>;


    return <div id="options" className="card">
      {this.optCardHeader}
        <div className="row">
          <div className="col">
            <div className="card-body">
              <h6 className="card-title">Distance unit:</h6>
              <ButtonGroup vertical>
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