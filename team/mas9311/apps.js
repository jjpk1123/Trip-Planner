class Header extends React.Component{
  render() {
    return (
      <div>
        <h1 className="text-center p-0">Michael Smith</h1>
        <p className="text-center">
          (808) 208-6942<br></br>
          Msmith91@rams.colostate.edu<br></br>
          451 Boardwalk Dr.
          Fort Collins, CO 80525
        </p>
      </div>
    )
  }
}

class Body extends React.Component {
  render() {
    return (
      <div>
        <h4>Objective</h4>
        <div className="card text-white bg-primary d-inline-flex p-2">
          Undergraduate studying computer science. Seeking entry-level position into the field.
        </div>
        <br></br>
        <div className="container">
        <div className="row">
          <div className="card col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
            <h4> Education</h4>
              <div className="card-title lead">Computer Science undergraduate</div>
                  <ul>
                    <li>
                      <h5> 
                        <div className="bg-success text-warning d-inline p-0">
                          Colorado State University
                        </div>
                        <small className="text-muted">Fort Collins, CO </small>
                      </h5> 
                      <ul> 
                        <li><i>May 2016 - May 2019</i>
                          <ul> 
                            <li><b>3.6 GPA</b></li>
                          </ul>
                        </li>
                      </ul>
                    </li>
                    <br></br>
                    <li> 
                      <h5>
                        <div className="bg-warning text-danger d-inline p-0"> 
                          Arizona State University
                        </div>
                        <small className="text-muted">Tempe, AZ </small>
                      </h5>
                      <ul> 
                        <li><i>August 2015 - May 2016</i>
                          <ul> 
                            <li>3.4 GPA</li> 
                          </ul>
                        </li>
                      </ul>
                    </li>
                  </ul>
            </div>

            <div className="card col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">
              <h3>Skills</h3>
              <ul>
                <li><b>GitHub, CodePen, Eclipse, PyCharm</b>, AppCode, Microsoft SQL Server</li>
                <li><b>Java, C, C++</b>, Objective C</li>
                <li>HTML, Bootstrap, JavaScript, ReactJS</li>
                <li><b>Python</b>, Swift</li>
                <li>SQL </li>
                <li>Windows 10, Mac OS X, Linux</li>
              </ul>
            </div>
        
            <div className="card col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">
              <h3>Experience</h3>
                <div className="lead">Computer Science field: <i>None</i> </div>
            </div>
        
            <div className="card col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">
              <h3>Certifications</h3>
                <ul><li><b>CPR</b> American Red Cross</li>
                  <ul><li>expires <i>May 2018</i></li></ul>
              </ul>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

class Main extends React.Component {
  render() {
    return (
      <div className="jumbotron">
        <Header />
        <hr/>
        <hr/>
        <Body />
      </div>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("app"));
