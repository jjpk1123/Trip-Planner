class Header extends React.Component {
  render() {
    return (
      <div>
        <h1>Quixotic Quetzals <small>Distance Calculator</small></h1> 
        Input your source and desintation coordinates in one of the following formats:
        <ul>
          <li>40° 26′ 46″ N 79° 58′ 56″ W</li>
          <li>40° 26.767′ N 79° 58.933′ W</li>
          <li>40.446° N 79.982° W</li>
          <li>40.445 -79.982</li>
        </ul>
      </div>
    )
  }
}

class Calculator extends React.Component {
  constructor(props) {
    super(props);
    /* state variables */
    this.state = { 
      output: "",
      source: "", 
      destin: ""
    };
    /* must bind all functions in constructor */
    this.calc = this.calc.bind(this);
    this.updateSource = this.updateSource.bind(this);
    this.updateDestin = this.updateDestin.bind(this);
  }

  updateSource(event) {
    /* update the value of srce.  needs validation */
    this.setState({source : event.target.value});
    this.setState({output : Number(event.target.value) + Number(this.state.destin) })
  }

  updateDestin(event) {
    /* update the value of destination.  needs validation */
    this.setState({destin : event.target.value});
    this.setState({output : Number(event.target.value) + Number(this.state.source) })
 }

  calc(event) {
    /* Coordinates are text.  Must convert to add rather than concatenate. */
    this.setState({output : Number(this.state.source) + Number(this.state.destin) })
    event.preventDefault();
  }

  render() {
    /* a simple form with text input and a submit button  */
    /* how do I make a line break between Src & Dest input? 
       <br></br> doesn't work. StackOverflow says document.write("\n"); */
    return (
      <div>
        <small>TODO: it's just a button for now</small>
        <div className="row">
          <h4 className="">Options</h4>
          <div className="btn-group btn-group-toggle" data-toggle="buttons">
            <label className="btn btn-secondary active">
              Miles<input type="radio" name="options" id="option1" autocomplete="off" checked/>
            </label>
            <label className="btn btn-secondary">
              Kilometers<input type="radio" name="options" id="option2" autocomplete="off"/>
            </label>
        </div>
          
      </div>
        <br/>
        <form onSubmit={this.calc}>
          <div className="">
            Source:<input type="text" name="source" className="text-left" size="33"
              value={this.state.source} onChange={this.updateSource}/> 
            <br/>
            Destination:<input type="text" name="destination" className="text-left" size="33"
              value={this.state.destin} onChange={this.updateDestin}/> 
            <br/>
            Output:<input type="text" className="text-left" 
              value={this.state.output} disabled/>
          </div>
        </form>
      </div>
    )
  } 
}

class Application extends React.Component {
  render() {
    /* separate the page layout from the calculator function */
    return (
      <div className="jumbotron">
        <Header />
        <hr/>
        <Calculator />
      </div>  
    )
  }
}

ReactDOM.render(<Application  />, document.getElementById("dist_app"));
