import React, {Component} from 'react';
import Header from './Header';
import Application from './Application';
import Footer from './Footer';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      number: "17",
      name: "Quixotic Quetzals"
    }
  }
  //style={{backgroundColor: "#1E4D28"}}
  render() {
    return <div id="tripco">
      <Header number={this.state.number} name={this.state.name}/>
      <Application/>
      <Footer number={this.state.number} name={this.state.name}/>
    </div>
  }
}

export default App;
