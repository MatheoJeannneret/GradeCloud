class ClassesCtrl {
  constructor() {
    this.initEvents();
    this.afficherClasses();

    // la classe actuelle
    this.currentClasseId = null;
    this.currentClasseNom = null;

    // examen actuel
    this.currentExamenId = null;
  }

  //évènements initialisés au début pour simplification
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
    $("#createExamen").on("click", (event) => {
      event.preventDefault();

      const nom = $("#nom").val();
      const description = $("#description").val();

      let date = $("#date").val();
      // Ajouter les secondes manquantes
      if (date.length === 16) {
        date += ":00";
      }

      const brancheId = parseInt($("#branches-select").val());
      const classeId = parseInt(this.currentClasseId);

      console.log({
        nom,
        description,
        date,
        branchId: brancheId,
        classeId: classeId,
      });

      http.createExamen(nom, description, date, brancheId, classeId, () => {
        alert("examen créé avec succès !!!");
        this.afficherClasse(this.currentClasseId, this.currentClasseNom);
      });
    });

    //supprimer un examen
    $("#delete-examen").on("click", (event) => {
      http.deleteExamen(this.currentExamenId, () => {
        alert(`suppression avec succès`);
        this.afficherClasse(this.currentClasseId, this.currentClasseNom);
      });
    });
  }

  //partie de déconnexion (appeler dans l'initialisation)
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

  //afficher les classes
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
        card.addEventListener("click", () =>
          this.afficherClasse(classe.id, classe.nom)
        );

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

  //afficher une seule classe
  afficherClasse(idClasse, nomClasse) {
    //stocker l'id de la classe
    this.currentClasseId = idClasse;
    this.currentClasseNom = nomClasse;

    //personnaliser la page
    document.getElementById("class-title").textContent =
      "Classe " + this.currentClasseNom;

    //afficher la bonne vue
    $("#classes-view").hide();
    $("#exam-details-view").hide();
    $("#class-view").show();

    //configurer le bouton de come-back
    $("#backToClasses").on("click", (event) => {
      $("#classes-view").show();
      $("#class-view").hide();
      $("#exam-details-view").hide();
    });

    //charger calendrier
    http.getExamensByClasse(this.currentClasseNom, (data) => {
      const calendarEl = document.getElementById("calendar");
      const events = this.genererEvenementsExamen(data);

      const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: "dayGridMonth",
        locale: "fr",
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek,listMonth",
        },
        buttonText: {
          today: "Aujourd'hui",
          month: "Mois",
          week: "Semaine",
          list: "Liste",
        },
        events: events,
        eventDidMount: function (info) {
          if (info.event.extendedProps.branche) {
            info.el.setAttribute("data-bs-toggle", "tooltip");
            info.el.setAttribute("data-bs-placement", "top");
            info.el.setAttribute("title", info.event.extendedProps.branche);
          }
        },
        eventClick: (info) => {
          this.afficherExamen(info);
        },
        dayMaxEvents: true,
        navLinks: true,
        height: "auto",
      });

      //laisse du temps au calendrier de charger :

      calendar.render();

      // Initialisation des tooltips Bootstrap
      const tooltipTriggerList = [].slice.call(
        document.querySelectorAll('[data-bs-toggle="tooltip"]')
      );
      tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
      });
    });
  }

  //créer evenement sur calendrier
  genererEvenementsExamen(data) {
    return data.map((examen) => ({
      title: examen.nom,
      start: examen.date,
      extendedProps: {
        description: examen.description,
        id: examen.id,
        branche: examen.branche.nom,
        classe: examen.classe.nom,
      },
    }));
  }

  //afficher l'examen
  afficherExamen(examen) {
    //stocker l'id de l'examen courrant
    this.currentExamenId = examen.event.extendedProps.id;

    //afficher la bonne vue
    $("#classes-view").hide();
    $("#class-view").hide();
    $("#exam-details-view").show();

    //configurer le bouton de come-back
    $("#backToClasse").on("click", (event) => {
      $("#classes-view").hide();
      $("#class-view").show();
      $("#exam-details-view").hide();
    });

    //charger les infos
    document.getElementById("exam-name").textContent = examen.event.title;
    const date = examen.event.start;
    const jour = String(date.getDate()).padStart(2, "0");
    const mois = String(date.getMonth() + 1).padStart(2, "0");
    const annee = date.getFullYear();
    const heures = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    document.getElementById(
      "exam-date"
    ).textContent = `${jour}/${mois}/${annee}`;
    document.getElementById("exam-time").textContent = `${heures}:${minutes}`;
    document.getElementById("exam-subject").textContent =
      examen.event.extendedProps.branche;
    document.getElementById("exam-description").textContent =
      examen.event.extendedProps.description;

    //charger les élèves
    http.getEleveByClasse(this.currentClasseNom, (eleves) => {
      const container = document.getElementById("eleves-container");
      container.innerHTML = ""; // Vide le conteneur avant d'ajouter les élèves

      eleves.forEach((eleve) => {
        const tr = document.createElement("tr");

        // Créer les colonnes
        const tdNom = document.createElement("td");
        tdNom.textContent = eleve.username; // Remplacer par le nom de l'élève

        const tdNote = document.createElement("td");
        const inputNote = document.createElement("input");
        inputNote.type = "number";
        inputNote.id = `note-${eleve.username}`; // ID unique pour chaque élève
        inputNote.min = 0;
        inputNote.max = 20;
        tdNote.appendChild(inputNote);

        const tdActions = document.createElement("td");
        const btnEdit = document.createElement("button");
        btnEdit.classList.add("btn", "btn-sm", "btn-outline-primary");
        btnEdit.innerHTML = '<i class="fas fa-edit"></i>';
        // Ajouter l'événement sur le bouton
        btnEdit.addEventListener("click", () => {
          // Récupérer la note et le username et appeler la méthode appropriée
          const note = inputNote.value;
          this.modifierNote(eleve.username, note); // Appel de la méthode avec le username et la note
        });
        tdActions.appendChild(btnEdit);

        // Ajouter les colonnes à la ligne
        tr.appendChild(tdNom);
        tr.appendChild(tdNote);
        tr.appendChild(tdActions);

        // Ajouter la ligne au conteneur
        container.appendChild(tr);
      });
    });
  }

  //modifier la note
  modifierNote(username, note) {
    http.createNote(username, this.currentExamenId, note, () => {
      alert(`la note de ${username} a bien été modifiée`);
    });
  }
}
