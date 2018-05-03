import React, {Component} from 'react';
import { Button, ButtonGroup, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';



class Staff extends Component{
  constructor(props) {
    super(props);
    this.modalToggle = this.modalToggle.bind(this);
    this.modalCancel = this.modalCancel.bind(this);
    this.state = {modal: false};
    this.optCardHeader = <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
      Staff
    </h5>;
  }

  modalToggle() {
    this.setState({
      modal: !this.state.modal
    });
  }

  modalCancel() {
    this.modalToggle();
  }



  render() {

    const staffBio =
      <div id="Staff" className="card">
        <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
          Julien Pecquet
        </h5>
        <div className="row">
          <div className="col">
            <div className="card-body">
                Julien is a senior at Colorado State University studying
              Computer Science with a math minor. He loves gaming,
              bicycles, and Avogadro's number.
            </div>
          </div>
        </div>
        <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
          Michael Smith
        </h5>
        <div className="row">
          <div className="col">
            <div className="card-body">
              Michael is a senior at Colorado State University
              working towards a BS in Computer Science. He loves technology,
              spending time with friends, and his wife.
            </div>
          </div>
        </div>
        <h5 className="card-header text-white" style={{backgroundColor: "#1E4D28"}}>
          Ezra Huston
        </h5>
        <div className="row">
          <div className="col">
            <div className="card-body">
              Ezra is a junior at Colorado State University studying Computer Science.
              He likes cars, gaming, and motocross.
            </div>
          </div>
        </div>

      </div>
    ;

    const staffModal = <div>
      <Button style={{backgroundColor: "#1E4D28"}} onClick={this.modalToggle}>Click here to meet the team!</Button>
      <Modal isOpen={this.state.modal} toggle={this.modalToggle} className={this.props.className}>
        <ModalHeader toggle={this.modalToggle}>Meet the team!</ModalHeader>
        <ModalBody>
          {staffBio}
        </ModalBody>
        <ModalFooter>
          <Button style={{backgroundColor: "#1E4D28"}} onClick={this.modalCancel}>Done</Button>
        </ModalFooter>
      </Modal>
    </div>;




    return <div id="staff" className="card">
      {this.optCardHeader}
      <div className="row">
        <div className="col">
          <div className="card-body">
            <ButtonGroup vertical>
              {staffModal}
            </ButtonGroup>
          </div>
        </div>
      </div>
    </div>


  }
}
export default Staff