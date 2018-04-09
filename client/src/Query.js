import React, {Component} from 'react';
import { Button, Table } from 'reactstrap';

class Query extends Component {
  constructor(props) {
    super(props);
    this.search = "";
    this.query = this.query.bind(this);
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

  addToTrip(event) {
    console.log(event.target);
    console.log("Appending [" + event.target.value + "] to end of trip");

    for (let i = 0; i < this.props.query.places.length; ++i) {
      if (this.props.query.places[i].id === event.target.value) {
        console.log("Adding " + this.props.query.places[i].id + " to trip.");
        let tempTrip = this.props.trip;
        let tempArray = tempTrip.places;
        let newPlace = this.props.query.places[i];
        tempArray.push(newPlace);

        tempTrip.places = tempArray;
        this.props.updateTrip(tempTrip);
        return;
      }
    }
  }

  removeFromTrip(event) {
    console.log(event.target);
    console.log("Removing  [" + event.target.value + "] from the trip");

    for (let i = 0; i < this.props.trip.places; ++i) {
      if (this.props.query.places[i].id === event.target.value) {
        console.log("Removing " + this.props.query.places[i].id + " from trip.");
        console.log("Still need to implement remove!");
        return;
      }
    }
  }

  createTable() {
    let i = 0;
    let queryResults = this.props.query.places.map((item) =>
        <tr key={i++} value={item.id}>
          {console.log(i)}
          <td value={i} type="button" onClick={this.addToTrip}>+</td>
          <td value={i} type="button" onClick={this.removeFromTrip}>-</td>
          <td>{item.name}</td>
          <td>{item.latitude}</td>
          <td>{item.longitude}</td>
        </tr>);

    return <Table responsive hover size="sm">
      <thead>
      <tr>
        <th key={0} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Add</th>
        <th key={1} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Name</th>
        <th key={2} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Lat</th>
        <th key={3} className="text-white align-self-center" style={{backgroundColor: "#1E4D28"}}>Long</th>
      </tr>
      </thead>
      <tbody>
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

        {table}
      </div>
    </div>
  }
}

export default Query;