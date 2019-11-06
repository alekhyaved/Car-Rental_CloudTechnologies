import React, { Component } from "react";
import Row from "react-bootstrap/Row";
import Login from "./LoginComponent";
import Badge from "react-bootstrap/Badge";

export default class Loginpage extends Component {
  render() {
    return (
      <div>
        <Row className="justify-content-md-center">
          <Badge variant="primary">
            <h1>Car Rental</h1>
          </Badge>
        </Row>
        <Login />
      </div>
    );
  }
}
