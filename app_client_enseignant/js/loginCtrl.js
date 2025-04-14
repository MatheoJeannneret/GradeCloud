class LoginCtrl {
  constructor(httpService) {
    this.http = httpService;
    this.btnConnect = $("#connect");
    this.initEvents();
  }

  connectSuccess(data, text, jqXHR) {
    sessionStorage.setItem("isConnected", "1");

    alert("Connexion avec succès !");

    IndexCtrl.loadIfConnected();
  }

  initEvents() {
    this.btnConnect.on("click", (event) => this.onLoginClick(event));
  }

  async onLoginClick(event) {
    event.preventDefault();

    const username = $("#username").val();
    const password = $("#password").val();

    const hashedPassword = await this.hashPassword(password);

    this.http.isAdmin(username, (res) => {
      this.http.connect(
        username,
        hashedPassword,
        this.connectSuccess.bind(this)
      );
    });
  }

  async hashPassword(password) {
    const encoder = new TextEncoder();
    const data = encoder.encode(password);
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray
      .map((b) => b.toString(16).padStart(2, "0"))
      .join("");
    return hashHex;
  }
}
