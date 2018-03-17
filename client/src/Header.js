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
      <p className="lead">"Want to travel far and wide?"</p>
      <ol>
        <li>
          Choose options for trip planning, information to display about locations,
          and how the trip map and itinerary should be saved.
        </li>
        <li>
          Choose your destinations by loading existing sets of destinations or
          find more in an extensive database of locations worldwide.
        </li>
        <li>
          Plan the trip with the options you selected.
          Review and revise the trip origin and order.
          Save the trip map and itinerary for future reference.
        </li>
      </ol>
    </div>
  }
}

export default Header;
