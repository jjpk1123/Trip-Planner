import React, {Component} from 'react';

class Query extends Component {
  constructor(props) {
    super(props);
    this.search = "";
    this.updateSearch = this.updateSearch.bind(this);
    this.fetchQueryResponse = this.fetchQueryResponse.bind(this);
    this.query = this.query.bind(this);
    this.createTable = this.createTable.bind(this);
  }

  updateSearch(event) {
    this.search = event.target.value;
    console.log("Search: " + this.search);
  }

  fetchQueryResponse(){
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

  async query(){
    try {
      console.log("Awaiting response from server: Query");
      let serverResponse = await this.fetchQueryResponse();
      let tffi = await serverResponse.json();
      console.log(tffi);
      this.props.updateQuery(tffi);
      console.log("Async Query(): fetchResponse is done");
    } catch(err) {
      console.error("You hit an error in async Query()");
      console.error(err);
    }
  }
  createTable () {
    let i = 0;
    let queryResults = this.props.query.places.map((item) => <td key = {i++}>{item.name}</td>);
    return {queryResults};
  }

  render() {
    let table = this.createTable();

    return(
    <div id = "query">
      <div className="col-xs-2 col-sm-6 col-md-8 col-lg-8 col-xl-8">
        <div className="card-body">
          <h6 className="card-title">Search:</h6>
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
    </div>
    )
  }
}

export default Query;