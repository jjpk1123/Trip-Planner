import React, {Component} from 'react';

/**
 * Renders a text footer below the application with copyright
 * and other useful information.
 */
class Footer extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <div id="footer" className="jumbotron" style={{backgroundColor: "#1E4D28"}}>
    <div className="row text-white">
      <div className="col-12 col-sm-12 col-md-6  col-lg-7 col-xl-8">
        <h4><br/>© TripCo t{this.props.number} 2018</h4>
      </div>
      <div className="col-8 col-sm-7 col-md-6 col-lg-5 col-xl-4 align-self-right">
        <img className="img-fluid" src="http://www.cs.colostate.edu/~davematt/logos/CSU_logos/CSU-Official-wrdmrk-357-617_Rev.png" />
      </div>
    </div>
    </div>

  }
}

export default Footer;