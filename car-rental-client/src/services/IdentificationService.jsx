import HttpService from "./HttpService";
import { BackendUrl } from "../config.json";

export function getIdentifications() {
  const token = localStorage.getItem("idToken");
  const config = {
    headers: {
      Authorization: "Bearer " + token
    }
  };

  return HttpService.get(BackendUrl + "/identifications", config);
}

export function blacklistIdentification(id) {
  const token = localStorage.getItem("idToken");
  const config = {
    headers: {
      Authorization: "Bearer " + token
    }
  };

  return HttpService.post(BackendUrl + "/verification/blacklist/" + id, config);
}

export function deleteIdentification(id) {
  const token = localStorage.getItem("idToken");

  const config = {
    headers: {
      Authorization: "Bearer " + token
    }
  };

  return HttpService.delete(BackendUrl + "/assets/" + id, config);
}
