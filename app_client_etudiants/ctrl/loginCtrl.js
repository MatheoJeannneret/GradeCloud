function connectSuccess(data, text, jqXHR) {
  if (jqXHR.status === 200) {
    // Stocker l'état de connexion dans sessionStorage
    sessionStorage.setItem("isConnected", "1");

    alert("Vous êtes connectés avec succès !");
    // Rediriger vers la page des produits
    window.location.href = "../ihm/main.html";
  }
}

function CallbackError(request, status, error) {
  if (request.status === 401) {
    alert("Le mot de passe est incorrect");
  } else if (request.status === 404) {
    alert("Cet utilisateur n'existe pas");
  } else if (request.status === 400) {
    alert("Merci de remplir tous les champs");
  } else {
    alert(
      "erreur : " +
        error +
        ", request: " +
        request.status +
        ", status: " +
        status
    );
  }
}
async function hashPassword(password) {
  const encoder = new TextEncoder();
  const data = encoder.encode(password);
  const hashBuffer = await crypto.subtle.digest("SHA-256", data);
  const hashArray = Array.from(new Uint8Array(hashBuffer));
  const hashHex = hashArray
    .map((b) => b.toString(16).padStart(2, "0"))
    .join("");
  return hashHex;
}

$(document).ready(function () {
  var butConnect = $("#btnConnect");
  butConnect.click(async function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const hashedPassword = await hashPassword(password); // ATTEND le hash correctement

    connect(username, hashedPassword, connectSuccess, CallbackError);
  });
});
