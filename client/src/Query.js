import React, {Component} from 'react';

class Query extends Component {
  constructor(props) {
    super(props);
    this.search = "";
    this.query = this.query.bind(this);
    this.addToTrip = this.addToTrip.bind(this);
    this.createTable = this.createTable.bind(this);
    this.updateSearch = this.updateSearch.bind(this);
    this.fetchQueryResponse = this.fetchQueryResponse.bind(this);
  }

  updateSearch(event) {
    this.search = event.target.value;
    console.log("Search: " + this.search);
  }

  fetchQueryResponse() {
    let requestBody = {
      "version" : 2,
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
    if (event.target.checked) {
      console.log("Appending [" + event.target.value + "] to end of trip");

      for (let i = 0; i < this.props.query.places.length; ++i) {
        console.log("Adding " + this.props.query.places[i].id + " to trip.");
        if (this.props.query.places[i].id === event.target.value) {
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
  }

  createTable() {
    let i = 0;
    let queryResults = this.props.query.places.map((item) => <td key = {i++}><input type="checkbox" value={item.id} onClick={this.addToTrip}/>{item.name}</td>);

    //console.log(this.queryArray);
    return {queryResults};

  }

  render() {
    let table = this.createTable();

    return <div id="query">
        <div className="card-body">
          <h6 className="card-title">Search the database:</h6>
          <div className="input-group" role="group">
            <input type="text" className="form-control" placeholder="Search..." onChange={this.updateSearch}/>
            <span className="input-group-btn">
              <button className="btn btn-primary" onClick={this.query} type="button">Search</button>
            </span>
          </div>

          <div id="queryResults">
            <table className="table table-responsive table-bordered">
              <tbody>
              <tr>
                <th className="table-info align-middle">Places:</th>
                {table.queryResults}
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
  }
}

export default Query;