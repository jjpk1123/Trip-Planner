import React, {Component} from 'react';
import { Button, ButtonGroup, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';



class Staff extends Component{
  constructor(props) {
    super(props);
    this.modalToggle = this.modalToggle.bind(this);
    this.modalCancel = this.modalCancel.bind(this);
    this.state = {modal: false};
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
    const staffModal = <div>
      <Button style={{backgroundColor: "#1E4D28"}} onClick={this.modalToggle}>Click here to meet the team!</Button>
      <Modal isOpen={this.state.modal} toggle={this.modalToggle} className={this.props.className}>
        <ModalHeader toggle={this.modalToggle}>Meet the team!</ModalHeader>
        <ModalBody>

        </ModalBody>
        <ModalFooter>
          <Button style={{backgroundColor: "#1E4D28"}} onClick={this.modalCancel}>Done</Button>
        </ModalFooter>
      </Modal>
    </div>;

    return <div id="options" className="card">
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