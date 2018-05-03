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
    return <div id="footer" className="card-footer" style={{backgroundColor: "#1E4D2B"}}>
    <div className="row text-white">
      <div className="col-8 col-sm-7 col-md-6 col-lg-5 col-xl-4 align-self-right"  style={{height:"80px"}}>
        <img className="img-fluid" style={{height:"100%"}}
             src="http://www.cs.colostate.edu/~davematt/logos/CSU_logos/CSU-Official-wrdmrk-357-617_Rev.png" />
      </div>
    </div>
    </div>

  }
}

export default Footer;