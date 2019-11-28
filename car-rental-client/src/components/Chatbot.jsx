import React, { Component } from "react";
import axios from "axios";
import { Widget, addResponseMessage, dropMessages} from "react-chat-widget";
import "react-chat-widget/lib/styles.css";
import config from "../../src/config";

export default class ListPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      opened: false,
      message: ""
    };
  }

  componentDidMount() {
    dropMessages();
    addResponseMessage("Hi! How can I help you today?");
  }
  handleNewUserMessage = newMessage => {
    console.log(`New message incoming! ${newMessage}`);
    // Now send the message throught the backend API
    axios
      .post(config.BackendUrl + "/chatbot/" + newMessage)
      .then(res => {
        this.state.message = res.data;
        if (this.state.message === "") {
          addResponseMessage("Chat bot services are currently not avaialable");
        } else {
          addResponseMessage(this.state.message);
        }
      })
      .catch(error => {
        console.log(error);
        addResponseMessage("Chat bot services are currently not avaialable");
      });
  };

  render() {
    return (
      <div>
        <Widget
          title="Car-Rental"
          subtitle="ChatBot"
          senderPlaceHolder="Type a message..."
          background-color="blue"
          handleNewUserMessage={this.handleNewUserMessage}
        />
      </div>
    );
  }
}
