class Header extends React.Component{
  render() {
    return (
      <section>
        <div className="container">
          <h1 className="display-3">Julien Pecquet</h1>
            <p>
           Team player ready to work in a fast-paced environment who understands that the customer is always the highest priority. Experienced in completing tasks under a time constraint. Always tries to do what will benefit the team over what will benefit himself.
            </p>
          </div>
      </section>
    )
  }
}

class Body extends React.Component {
  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <h2>Goal</h2>
            <p>To gain as much experience and knowledge as possible in the field in order to become a valuable and reputable asset to any team. My dream is to be a lead programmer for an application which yields huge success.</p>
          </div>
          
          <div className="col-md-6">
            <h2>Skills</h2>
            <p>Java, C, C++, Python, HTML, ReactJS, IntelliJ, Eclipse</p>
          </div>
          
          <div className="col-md-6">
            <h2>Education</h2>
            <p>
              <li><b>B.S. in Computer Science</b></li>
             Colorado State University, Fort Collins, CO
               <br></br>January 2016 - Ongoing
              <li> <b>A.A. in Psychology</b></li>
               Front Range Community College, Fort Collins, CO 
               <br></br> May 2011 - December 2013
               <li> <b>General Education Diploma</b></li>
                 Earned in February 2011
             </p>
          </div>
          
           <div className="col-md-6">
            <h2>Personal Information</h2>
            <p>
              <li><b>Address:</b> 1821 Broadview Place, Fort Collins, CO, 80521</li>
              <li><b>Cell Phone:</b> (970)388-0716</li>
              <li><b>Personal Email:</b> JulienPecquet@gmail.com</li>
              <li><b>School Email:</b> jjpk@rams.colostate.edu</li>
              <li><b>Website:</b> Work In Progress :)</li>
            </p>
          </div>
        </div>
       <hr />
        
    <footer class="container">
      <p>&copy; JJPK Corp. 2018</p>
    </footer>
        
      </div> 
    )
  }
}

class Main extends React.Component {
  render() {
    return (
      <section>
      <div className="jumbotron">
        <Header />
      </div>
      <div>
        <Body />
        </div>
      </section>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("app"));
