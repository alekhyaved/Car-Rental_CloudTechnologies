import React from "react";
import { withAuthenticator } from "aws-amplify-react";
import { Auth } from "aws-amplify";
import RentCarPage from "./RentCarPage";
import { Redirect } from "react-router-dom";

class LoginComponent extends React.Component {
  constructor(props, context) {
    super(props, context);
  }

  componentDidMount() {
    localStorage.setItem("userName", Auth.user.username);
  }

  render() {
    if (Auth.user.username === "admin1") {
      return <div>{/* adminpage component */}</div>;
    } else {
      return <Redirect to="/license" />;
    }
  }
}
const MyTheme = {
  button: { backgroundColor: "blue" },
  a: { color: "blue" },
  signInButtonIcon: { display: "none" }
};

export default withAuthenticator(LoginComponent, {
  includeGreetings: false,
  authenticatorComponents: [],
  theme: MyTheme,
  signUpConfig: {
    hiddenDefaults: ["phone_number"],
    signUpFields: [
      {
        label: "First Name",
        key: "name",
        required: false,
        type: "string"
      },
      {
        label: "Last Name",
        key: "name",
        required: false,
        type: "string"
      }
    ]
  }
});
