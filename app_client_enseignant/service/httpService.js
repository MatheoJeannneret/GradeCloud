var BASE_URL_NOTE = "https://133.darazsj.emf-informatique.ch/note";
var BASE_URL_AUTH = "https://133.darazsj.emf-informatique.ch/auth";

class HttpService {
  constructor() {}

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

  connect(username, password, successCallback) {
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
    });
  }

  disconnect(successCallback) {
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

  isAdmin(username, successCallback) {
    $.ajax({
      type: "POST",
      dataType: "text",
      url: BASE_URL_AUTH + "/isadmin",
      data: { username: username },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  getInfosUser(username, success) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_AUTH + "/getinfo",
      data: { username: username },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  getExamensByClasse(classe, successCallback) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_NOTE + "/getexamensbyclasse",
      data: { classe: classe },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  createExamen(nom, description, date, branchId, classeId, successCallback) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: BASE_URL_NOTE + "/createexamen",
      data: {
        nom: nom,
        description: description,
        date: date,
        brancheId: branchId,
        classeId: classeId,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  getClasse(successCallback) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_NOTE + "/getclasse",
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  getBranche(successCallback) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_NOTE + "/getbranche",
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  getEleveByClasse(classNom, successCallback) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: BASE_URL_NOTE + "/getelevebyclasse",
      data: { classNom: classNom },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  createNote(username, examenId, noteChiffre, successCallback) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: BASE_URL_NOTE + "/createnote",
      data: {
        username: username,
        examenId: examenId,
        noteChiffre: noteChiffre,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  updateExamen(
    examenId,
    nom,
    description,
    date,
    branchId,
    classeId,
    successCallback
  ) {
    $.ajax({
      type: "PUT",
      dataType: "json",
      url: BASE_URL_NOTE + "/updateexamen",
      data: {
        examenId: examenId,
        nom: nom,
        description: description,
        date: date,
        branchId: branchId,
        classeId: classeId,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }

  deleteExamen(examenId, successCallback) {
    $.ajax({
      type: "DELETE",
      dataType: "json",
      url: BASE_URL_NOTE + "/deleteexamen",
      data: {
        examenId: examenId,
      },
      xhrFields: {
        withCredentials: true,
      },
      success: successCallback,
    });
  }
}
