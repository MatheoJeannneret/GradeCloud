$(document).ready(function () {
  http = new HttpService();
  indexCtrl = new IndexCtrl();
  http.centraliserErreurHttp(indexCtrl.afficherErreurHttp);
});

class IndexCtrl {
  constructor() {
    this.vue = new VueService();
    this.loadIfConnected();
  }

  afficherErreurHttp(msg) {
    alert(msg);
  }

  loadIfConnected() {
    if (sessionStorage.getItem("isConnected") === "1") {
      this.loadClasses();
    } else {
      this.loadLogin();
    }
  }

  loadLogin() {
    this.vue.chargerVue("login", () => new LoginCtrl());
    $("title").text("GradeCloud - Login");
  }

  loadClasses() {
    this.vue.chargerVue("classes", () => new ClassesCtrl());
    $("title").text("GradeCloud - Classes");
  }
}
