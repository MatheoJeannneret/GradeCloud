class ClassesCtrl {
  constructor() {
    this.initEvents();
    this.afficherClasses();
    this.currentClasseId = null;
  }

  initEvents() {
    //écouteur de déconnexion
    $("#deconnexion").on("click", (event) => this.onLogoutClick(event));

    //liste des branches
    http.getBranche((branches) => {
      const selectBranches = document.getElementById("branches-select");

      branches.forEach((branche) => {
        const option = document.createElement("option");
        option.value = branche.id;
        option.textContent = branche.nom;
        selectBranches.appendChild(option);
      });
    });

    //ajouter examen
    $("#createExamen").on("click", (event) => {});
  }

  onLogoutClick(event) {
    event.preventDefault();
    const confirmQuestion = confirm("Voulez-vous vraiment vous déconnecter ?");
    if (confirmQuestion) {
      http.disconnect(() => {
        alert("Vous êtes deconnecté avec succès");
        sessionStorage.clear();
        indexCtrl.loadIfConnected();
      });
    }
  }

  afficherClasses() {
    http.getClasse((classes) => {
      const container = document.getElementById("classes-container");
      container.innerHTML = ""; // Vide le conteneur avant d'ajouter les classes

      classes.forEach((classe) => {
        const col = document.createElement("div");
        col.className = "col";

        const card = document.createElement("div");
        card.className = "card h-100 class-card";

        // Ajoute un événement click
        card.addEventListener("click", () => this.afficherClasse(classe.id));

        card.innerHTML = `
          <div class="card-body">
            <h5 class="card-title">Classe ${classe.nom}</h5>
          </div>
          <div class="card-footer">
            <i class="fa-solid fa-arrow-right"></i>
          </div>
        `;

        col.appendChild(card);
        container.appendChild(col);
      });
    });
  }

  afficherClasse(idClasse) {
    //stocker l'id de la classe
    this.currentClasseId = idClasse;

    //afficher la bonne vue
    $("#classes-view").hide();
    $("#class-view").show();

    //configurer le bouton de come-back
    $("#backToClasses").on("click", (event) => {
      $("#classes-view").show();
      $("#class-view").hide();
    });
  }
}
