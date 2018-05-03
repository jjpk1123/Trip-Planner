import React, {Component} from 'react';
import { Table, Form, FormGroup, Label, Input } from 'reactstrap';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

class Query extends Component {
  constructor(props) {
    super(props);
    this.modalSubmit = this.modalSubmit.bind(this);
    this.modalToggle = this.modalToggle.bind(this);
    this.modalCancel = this.modalCancel.bind(this);
    this.state = {
      search: "",
      dropdownOpen: false,
      modal: false,
      filters: []
    };
    this.fetchQuery = this.fetchQuery.bind(this);
    this.fetchQueryResponse = this.fetchQueryResponse.bind(this);
    this.addAll = this.addAll.bind(this);
    this.addToTrip = this.addToTrip.bind(this);
    this.createTable = this.createTable.bind(this);
    this.updateSearch = this.updateSearch.bind(this);
    this.removeFromTrip = this.removeFromTrip.bind(this);
    this.addFilter = this.addFilter.bind(this);
    this.addToFilter = this.addToFilter.bind(this);
  }

  modalToggle() {
    this.setState({modal: !this.state.modal});
  }

  modalSubmit() {
    let temp = this.props.query;
    temp.filters = this.state.filters;

    this.props.updateQuery(temp);
    this.modalToggle();
  }

  modalCancel() {
    this.modalToggle();
  }

  updateSearch(event) {
    this.state.search = event.target.value;
    console.log("Search: " + this.state.search);
  }

  async fetchQuery() {
    try {
      console.log("Awaiting response from server: Query");
      let serverResponse = await this.fetchQueryResponse();
      let tffi = await serverResponse.json();
      console.log(tffi);
      this.props.updateQuery(tffi);
      console.log("async fetchQuery(): fetchResponse is done");
    } catch(err) {
      console.error("You hit an error in async fetchQuery()");
      console.error(err);
    }
  }

  fetchQueryResponse() {
    let temp = this.props.query;
    temp.version = 3;
    temp.type = "query";
    temp.query = this.state.search;
    temp.places = [];
    this.props.updateQuery(temp);
    console.log(process.env.SERVICE_URL);
    console.log(this.props.query);

    return fetch('http://' +  this.props.hostname + ':' + this.props.port  + '/query', {
      method:"POST",
      body: JSON.stringify(this.props.query)
    });
  }

  /**
   * Adds all in query.places[] into trip.places[]
   */
  addAll() {
    for (let i = 0; i < this.props.query.places.length; ++i) {
      let temp = this.props.query.places[i];
      let add = true;

      for (let j = 0; j < this.props.trip.places.length; ++j) {
        if (temp.id === this.props.trip.places[j].id) {
          console.log(temp.name + " is already in trip.places");
          add = false;
        }
      }
      if (add === true) {
        console.log("Adding " + temp.name + " to the trip");
        let tempTrip = this.props.trip;
        let tempArray = tempTrip.places;
        tempArray.push(temp);

        tempTrip.places = tempArray;
        this.props.updateTrip(tempTrip);
      }
    }
  }

  /**
   * Appends the query.places element to trip.places array.
   * @param event = event.id == which index to add in query.places[]
   */
  addToTrip(event) {
    let placeToAdd = this.props.query.places[event.target.id];

    for (let i = 0; i < this.props.trip.places.length; ++i) {
      if (this.props.trip.places[i].id === placeToAdd.id) {
        console.log(placeToAdd.name + " is already in the trip!");
        return;
      }
    }

    console.log("Appending \"" + placeToAdd.name + "\" to end of trip");
    let tempTrip = this.props.trip;
    let tempArray = tempTrip.places;
    tempArray.push(placeToAdd);

    tempTrip.places = tempArray;
    this.props.updateTrip(tempTrip);
  }

  /**
   * Searches trip.places[] to see which place it needs to remove.
   * After it finds it via the unique places.id), it creates temp variables,
   * removes the place, and updates the Application's trip via tempTrip.
   * @param event = event.id == which index it is in query.places[]
   */
  removeFromTrip(event) {
    let placeToRem = this.props.query.places[event.target.id];

    for (let i = 0; i < this.props.trip.places.length; ++i) {
      if (this.props.trip.places[i].id === placeToRem.id) {
        console.log("Removing from trip.");
        let tempTrip = this.props.trip;
        let tempArray = tempTrip.places;
        tempArray.splice(i, 1);

        tempTrip.places = tempArray;
        this.props.updateTrip(tempTrip);
        return;
      }
    }
    console.log(placeToRem.name + " isn't even in the trip!");
  }

  addFilter(event) {
    // console.log("Finding " + event.target.id);

    for (let i = 0; i < this.props.config.filters.length; ++i) {
      let att = this.props.config.filters[i]["attribute"];

      for (let j = 0; j < this.props.config.filters[i]["values"].length; ++j) {
        let val = this.props.config.filters[i]["values"][j];
        // console.log(val);

        if (val === event.target.id) {
          this.addToFilter(att, val);
          return;
        }
      }
    }
  }

  addToFilter(att, val) {
    let index = -1;
    let attributeExists = false;
    for (let i = 0; i < this.state.filters.length; ++i) {

      if (this.state.filters[i]["attribute"] === att) {
        index = i;
        attributeExists = true;

        for (let j = 0; j < this.state.filters[i]["values"].length; ++j) {
          let currVal = this.state.filters[i]["values"][j];
          console.log("Maybe it's " + currVal + " ?");

          if (currVal === val) {
            // console.log(this.state.filters[i]["values"]);

            if (this.state.filters[i]["values"].length === 1) {
              console.log("Nothing left in '" + att + "', so I'm deleting it");
              this.state.filters.splice(i, 1);

            }
            else {
              console.log("Removing " + this.state.filters[i]["values"][j] + " from filters['" + att + "'].");
              this.state.filters[i]["values"].splice(j, 1);
              console.log("filters[" + i + "]['values'] length = " + this.state.filters[i]["values"].length);
            }
            console.log("After deleting, filter(s) length = " + this.state.filters.length);
            return;
          }
        } // end inner for loop

        //the attribute exists, but the target value is not in there

        let valArr = this.state.filters[i]["values"];
        valArr.push(val);
        this.state.filters[i] = {"attribute": att, "values": valArr};
        console.log("filters[" + i + "]['values'] length = " + this.state.filters[i]["values"].length);
        console.log("filters length = " + this.state.filters.length);
        return;
      }
    } // end outer for loop
    console.log("Making new Array for " + att);

    let tempValArray = new Array(val);
    let dict = {"attribute": att, "values": tempValArray};
    this.state.filters.push(dict);
    console.log("filters length = " + this.state.filters.length);
  }

  createTable() {
    let i = -1;
    let queryResults = this.props.query.places.map((item) =>
        <tr key={++i}>
          <td id={i} type="button" onClick={this.addToTrip}>+</td>
          <td id={i} type="button" onClick={this.removeFromTrip}>-</td>
          <td>{item.name}</td>
          <td>{item.city}</td>
          <td>{item.state}</td>
          <td>{item.country}</td>
          <td>{item.continent}</td>
        </tr>);

    return <Table responsive hover size="sm" style={{height: "20%", overflow: "scroll", display: "inline-block"}}>
      <thead>
      <tr>
        <th key={0} type="button" onClick={this.addAll}>Add All</th>
        <th key={1} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Name</th>
        <th key={2} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>City</th>
        <th key={3} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>State</th>
        <th key={4} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Country</th>
        <th key={5} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Continent</th>
      </tr>
      </thead>
      <tbody >
        {queryResults}
      </tbody>
    </Table>;
  }

  render() {
    let table = this.createTable();
    let unique = 0;

    const filterForm =
    <div className="filters">
      <form>
          {this.props.config.filters.map((filter) =>
            <div>
              <label> {filter["attribute"]} </label>
              {filter.values.map((val) =>
                <div>
                  <input className="checkbox" type="checkbox" key={val} id={val} onClick={this.addFilter}/>
                  <label key={unique++}> {val} </label>
                </div>)}

              <br />
            </div>
          )}
      </form>
    </div>;


    const filterModal = <div>
      <Button style={{backgroundColor: "#c8c372"}} onClick={this.modalToggle}>Filters</Button>
      <Modal isOpen={this.state.modal} toggle={this.modalToggle} className={this.props.className}>
        <ModalHeader toggle = {this.modalToggle}>Apply your desired filters below.</ModalHeader>
        <ModalBody>
          {filterForm}
        </ModalBody>
        <ModalFooter>
          <Button style={{backgroundColor: "#1E4D28"}} onClick={this.modalSubmit}>Submit</Button>
          {' '}
          <Button style={{backgroundColor: "#59595b"}} onClick={this.modalCancel}>Cancel</Button>
        </ModalFooter>
      </Modal>
    </div>;

    return <div id="query">
      <div className="card-body">
        <div className="input-group" role="group">
          <input type="text" className="form-control" placeholder="Search the database..."
                 onChange={this.updateSearch} />
          <span className="input-group-btn">
              <button className="btn text-white" style={{backgroundColor: "#1E4D2B"}} onClick={this.fetchQuery}
                      type="button">Search</button>
            </span>
          {filterModal}
        </div>

        {
          this.props.query.places.length > 0 &&
            table
        }
      </div>
    </div>
  }
}

export default Query;