var BASE_URL_NOTE = "https://133.darazsj.emf-informatique.ch/note";
var BASE_URL_AUTH = "https://133.darazsj.emf-informatique.ch/auth";
 

 /*
  centraliserErreurHttp(httpErrorCallbackFn) {
    $.ajaxSetup({
      error: function (xhr, exception) {
        let msg;
        if (xhr.status === 0) {
          msg = "Pas d'accès à la ressource serveur demandée !";
        } else if (xhr.status === 400) {
          msg = xhr.responseText;
        } else if (xhr.status === 403) {
          msg = "Pas les droits d'accès [403]";
        } else if (xhr.status === 404) {
          msg = "Page demandée non trouvée [404] !";
        } else if (xhr.status === 500) {
          msg = "Erreur interne sur le serveur [500] !";
        } else if (exception === "parsererror") {
          msg = "Erreur de parcours dans le JSON !";
        } else if (exception === "timeout") {
          msg = "Erreur de délai dépassé [Time out] !";
        } else if (exception === "abort") {
          msg = "Requête Ajax stoppée !";
        } else {
          msg = "Erreur inconnue : \n" + xhr.responseText;
        }
        httpErrorCallbackFn(msg);
      },
    });
  }
 */
  function connect(username, password, successCallback, errorCallback) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: BASE_URL_AUTH + "/login",
      contentType: "application/x-www-form-urlencoded", 
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
 
  function disconnect(successCallback) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: BASE_URL_AUTH + "/logout",
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }
 
  function isAdmin(username, successCallback) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: BASE_URL_AUTH + "/isadmin",
      data: { username: username },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }
