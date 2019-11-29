import React, { Component } from "react";
import Table from "react-bootstrap/Table";
import Navbar from "./Navbar";
import axios from "axios";
import config from "../../src/config";
import { Button } from "react-bootstrap";
import Chatbot from "./Chatbot";

export default class BookingDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bookingDetails: []
    };
  }
  componentDidMount() {
    this.reloadFileList();
  }

  reloadFileList = () => {
    axios
      .get(
        config.BackendUrl + "/listDetails/" + localStorage.getItem("userName")
      )
      .then(res => {
        this.setState({ bookingDetails: res.data });
      })
      .catch(error => {
        console.log(error);
        alert("ServerConnection failure");
      });
  };
  cancelBooking = id => {
    axios
      .delete(config.BackendUrl + "/cancelBooking/" + id)
      .then(res => {
        alert(res.data);
        window.location.reload(false);
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div>
        <Navbar name={this.state.username} />
        <br />
        <h2>Booking Details</h2>
        <br />
        <Table striped bordered hover size="xl">
          <thead>
            <tr>
              <th>License</th>
              <th>FirstName</th>
              <th>LastName</th>
              <th>StartDate</th>
              <th>EndDate</th>
              <th>CarType</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {this.state.bookingDetails.map(bookingDetails => (
              <tr id={bookingDetails.id}>
                <td>{bookingDetails.license}</td>
                <td>{bookingDetails.firstname}</td>
                <td>{bookingDetails.lastname}</td>
                <td>{bookingDetails.startDate}</td>
                <td>{bookingDetails.endDate}</td>
                <td>{bookingDetails.carType}</td>
                <td>
                  <Button
                    variant="primary"
                    onClick={() => this.cancelBooking(bookingDetails.id)}
                  >
                    CANCEL
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
          <Chatbot />
      </div>
    );
  }
}
