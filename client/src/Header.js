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
    return <div id="header" className="jumbotron">
      {this.retrieveTitle()}
    </div>
  }
}

export default Header;
