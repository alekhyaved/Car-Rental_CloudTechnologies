import React, { Component } from 'react';
import * as IdentificationService from '../services/IdentificationService';
import Nav from "./Navbar";

class Admin extends Component {
  state = {
    identifications: [],
    currentUser: ""
  };

  async componentDidMount() {
    const { data: identifications } = await IdentificationService.getIdentifications();

    // update state
    this.setState({ identifications });
  }

  handleBlacklist = identification => {
    if (identification.blacklisted) {
      return;
    }

    if (!window.confirm("Are you sure you want to blacklist this user?")) {
      return;
    }

    // Call backend to blacklist
    IdentificationService.blacklistIdentification(identification.id);

    console.log("Blacklisting identification ", identification);
    const identifications = [...this.state.identifications];
    const index = identifications.indexOf(identification);
    identifications[index].blacklisted = !identifications[index].blacklisted;

    // TODO blacklist, update and disable button
    this.setState({ identifications });

    // window.location = "/admin";
  };

  render() {
    return (
      <React.Fragment>
        <Nav />
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th>ID</th>
              <th>S3 Key</th>
              <th>Image</th>
              <th>Username</th>
              <th>Blacklisted</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.identifications.map(identification => (
              <tr key={identification.id}>
                <td>{identification.id}</td>
                <td>{identification.s3Key}</td>
                <td>
                  <a href={identification.fileUrl}><img src={identification.fileUrl} width="100" height="70" /></a>
                </td>
                <td>{identification.username}</td>
                <td>{identification.blacklisted ? "Yes" : "No"}</td>
                <td>
                  <button
                    onClick={() => this.handleBlacklist(identification)}
                    className={"btn btn-danger btn-sm" + (identification.blacklisted ? " disabled" : "")}
                  >
                    Blacklist
                </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </React.Fragment>
    );
  }
}

export default Admin;