/* ReactJS components to render the sample page */
/* Note the use of className rather than class for ReactJS */

class Header extends React.Component{
  render() {
    return (
     <section>
        <div className="container">
      <h1 className="display-4">Ezra Huston</h1>
        <p>I'm a Computer Science major at Colorado State University in Fort Collins, Colorado! Learn more about me, my hobbies, and my skills below!
          <br>
          </br>
            <strong>Contact Information</strong>
            <li><strong>Phone: </strong> (303) 775-6602</li>
            

            <li><strong>Mailing Address: </strong>
       1113 Hillcrest Dr., Fort Collins, CO 80521</li>
              <li><strong>Email: </strong>
              ezra.huston@gmail.com</li>
</p>
      </div>
        </section>
    )
  }
}

class Body extends React.Component {
  render() {
    return (
      <div className = "container">
        <div className="row">
          <div className="col-md-4">
            <h2>Skills</h2>
            <p>I am proficient in <li>Java</li>
              <li>C++</li>
              <li>C</li>
              <li>Python</li> 
              <li>HTML</li>
              I am working on learning Bootstrapv4, CodePen, and JavaScript currently. I also work well in teams, and have extensive previous experience in such. </p>
            </div>
          <div className="col-md-4">
            <h2>Education</h2>
            <p>
              <li><strong>B.S. in Computer Science</strong></li>
                Colorado State University, Fort Collins, CO<br></br>
                August 2015 - current
              
                  <li><strong>High School Diploma</strong></li> 
                 Earned May 2015 from Cherokee Trail High School
                  
            </p>
          </div>
          <div className="col-md-4">
            <h2>Work Experience</h2>
            <p>
              <li><strong>Discount Tire (Feb. 2014 - Aug. 2015)</strong></li>
              
                At Discount Tire I worked as a Service Coordinator, usually running a team of four technicians while they performed their work on vehicles. 
                
                <li><strong>Discovery Research</strong></li>
                
                I worked as an interviewer for a research firm. I learned a lot of customer service skills during my time at Discovery Research, and they have helped me greatly throught my education.
                
            </p>
            
          </div>
          </div>
      </div>
    )
  }
}

class Main extends React.Component {
  render() {
    return (
      <section>
      <div>
        <div className="jumbotron">
        <Header />
        </div>
        
        <Body />
      </div>
      </section>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("app"));
