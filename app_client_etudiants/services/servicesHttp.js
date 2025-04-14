var BASE_URL_NOTE = "https://133.darazsj.emf-informatique.ch/note";
var BASE_URL_AUTH = "https://133.darazsj.emf-informatique.ch/auth";
 

class ServiceHttp{

   connect(username, password, successCallback, errorCallback) {
    $.ajax({
      type: "POST",
      dataType: "text",
      url: BASE_URL_AUTH + "/login",
      data: {
        username: username,
        password: password,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
      error: errorCallback
    });
  }
 
   disconnect(successCallback, errorCallback) {
    $.ajax({
      type: "POST",
      dataType: "text",
      url: BASE_URL_AUTH + "/logout",
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

   getNoteByEleve(username, successCallback, errorCallback) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_NOTE + "/getnotesbyeleve",
      data: {
        username: username,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
      error: errorCallback
    });
  }

   getExamenByEleve(username, successCallback, errorCallback) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_NOTE + "/getexamenbyeleve",
      data: {
        username: username,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
      error: errorCallback
    });
}

}