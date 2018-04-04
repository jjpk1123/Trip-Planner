import React, {Component} from 'react';

/**
 * Renders a text heading above the application with useful information.
 */
class Header extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * Returns the title Header at top-most of web page
   */
  retrieveTitle() {
    return <h3>TripCo <small>t{this.props.number} {this.props.name}</small></h3>;
  }

  /**
   * Renders the Header of the web page
   */
  render() {
    return <div id="header" className="jumbotron" style={{backgroundColor: "#1E4D28"}}>
      <div className="row text-white ">
        <div className="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">
          <img className="img-fluid" alt="Responsive image"
               src="http://www.cs.colostate.edu/~davematt/logos/CS_unit_identifiers/CompSci-NS-CSU-1-Hrev.png" />
        </div>
      </div>
    </div>
  }
}

export default Header;
