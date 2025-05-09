$(document).ready(function () {
    const loginCtrl = new LoginCtrl();
    const serviceHttp = new ServicesHttp();
    var butConnect = $("#btnConnect");
    butConnect.click(async function (event) {
        event.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        sessionStorage.setItem("username", username);
        const hashedPassword = await loginCtrl.hashPassword(password); // ATTEND le hash correctement

        serviceHttp.connect(username, hashedPassword, loginCtrl.connectSuccess, loginCtrl.CallbackError);
    });
});

class LoginCtrl {

    connectSuccess(data, text, jqXHR) {
        
        if (jqXHR.status === 200) {

            // Stocker l'état de connexion dans sessionStorage
            sessionStorage.setItem("isConnected", "1");
            alert("Vous êtes connectés avec succès !");
            // Rediriger vers la page des produits
            window.location.href = "../ihm/main.html";
        }
    }

    CallbackError(request, status, error) {
        if (request.status === 401) {
            alert("Le mot de passe est incorrect");
        
        }else if (request.status === 404) {
            alert("Cet utilisateur n'existe pas");
        }
        else if (request.status === 400) {
            alert("Merci de remplir tous les champs");
        } 
        else if (request.status === 403) {
            alert("Vous n'avvez pas les droits ! ");
        }else {
            alert("erreur : " + error + ", request: " + request.status + ", status: " + status);
        }
    }

    async hashPassword(password) {
        const encoder = new TextEncoder();
        const data = encoder.encode(password);
        const hashBuffer = await crypto.subtle.digest("SHA-256", data);
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
        return hashHex;
    }
}

