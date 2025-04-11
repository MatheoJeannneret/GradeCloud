class VueService {
  constructor() {}

  chargerVue(vue, callback) {
    //charger la vue demandée
    $("#view").load("view/" + vue + ".html", function () {
      if (typeof callback !== "undefined") {
        callback();
      }
    });
  }
}
