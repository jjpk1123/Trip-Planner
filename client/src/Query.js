import React, {Component} from 'react';
import { Button, Table } from 'reactstrap';

class Query extends Component {
  constructor(props) {
    super(props);
    this.search = "";
    this.query = this.query.bind(this);
    this.addAll = this.addAll.bind(this);
    this.addToTrip = this.addToTrip.bind(this);
    this.createTable = this.createTable.bind(this);
    this.updateSearch = this.updateSearch.bind(this);
    this.removeFromTrip = this.removeFromTrip.bind(this);
    this.fetchQueryResponse = this.fetchQueryResponse.bind(this);
  }

  updateSearch(event) {
    this.search = event.target.value;
    console.log("Search: " + this.search);
  }

  fetchQueryResponse() {
    let requestBody = {
      "version" : 3,
      "type"    : "query",
      "query"   : this.search,
      "places"  : []
    };
    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + location.host + '/query', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async query() {
    try {
      console.log("Awaiting response from server: Query");
      let serverResponse = await this.fetchQueryResponse();
      let tffi = await serverResponse.json();
      console.log(tffi);
      this.props.updateQuery(tffi);
      console.log("async query(): fetchResponse is done");
    } catch(err) {
      console.error("You hit an error in async query()");
      console.error(err);
    }
  }

  /**
   * Adds all in query.places[] into trip.places[]
   */
  addAll(event) {
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

    return <div id="query">
      <div className="card-body">
        <div className="input-group" role="group">
          <input type="text" className="form-control" placeholder="Search the database..."
                 onChange={this.updateSearch} />
          <span className="input-group-btn">
              <button className="btn text-white" style={{backgroundColor: "#1E4D2B"}} onClick={this.query}
                      type="button">Search</button>
            </span>
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
