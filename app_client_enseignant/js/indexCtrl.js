$(document).ready(function () {
  const http = new HttpService();
  indexCtrl = new IndexCtrl(http);
  http.centraliserErreurHttp(indexCtrl.afficherErreurHttp);
});

class IndexCtrl {
  constructor(http) {
    this.http = http;
    this.vue = new VueService();
    this.loadIfConnected();
  }

  loadIfConnected() {
    if (sessionStorage.getItem("isConnected") === "1") {
      this.loadClasses();
    } else {
      this.loadLogin();
    }
  }

  afficherErreurHttp(msg) {
    alert(msg);
  }

  loadLogin() {
    this.vue.chargerVue("login", () => new LoginCtrl(this.http));
    $("title").text("GradeCloud - Login");
  }

  loadClasses() {
    this.vue.chargerVue("classes", () => new AccueilCtrl());
    $("title").text("GradeCloud - Classes");
  }
}
