import Axios from "axios";

export function getBearerToken() {
  return "Bearer " + localStorage.getItem("idToken");
}

Axios.interceptors.response.use(null, error => {
  const expectedError =
    error.response &&
    error.response.status >= 400 &&
    error.response.status < 500;

  if (!expectedError) {
    console.log("Log unexpected error", error);
    alert("An unexpected error occurred.");
  }

  return Promise.reject(error);
});

export default {
  get: Axios.get,
  post: Axios.post,
  put: Axios.put,
  delete: Axios.delete,
  getBearerToken
};
