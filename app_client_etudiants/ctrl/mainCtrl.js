function chargerCalendrier(data) {
    // <script>
    // Récupérer et afficher le nom d'utilisateur
    //  document.addEventListener('DOMContentLoaded', function() {
    // const username = localStorage.getItem('username') || 'Élève';
    // document.getElementById('welcomeMessage').textContent += username;

    // Initialisation du calendrier FullCalendar
    const calendarEl = document.getElementById('calendar');
    const events = genererEvenementsExamen(data);

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'fr',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,listMonth'
        },
        buttonText: {
            today: 'Aujourd\'hui',
            month: 'Mois',
            week: 'Semaine',
            list: 'Liste'
        },
        events:  events,
        eventDidMount: function (info) {
            if (info.event.extendedProps.description) {
                info.el.setAttribute('data-bs-toggle', 'tooltip');
                info.el.setAttribute('data-bs-placement', 'top');
                info.el.setAttribute('title', info.event.extendedProps.description);
            }
        },
        dayMaxEvents: true,
        navLinks: true,
        height: 'auto'
    });

    //laisse du temps au calendrier de charger :
    
        calendar.render();
  
//hapendChild permet  d'ajouter un noeud enfant a un noeud parent
    // Initialisation des tooltips Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}
//créer evenement sur calendrier 
function genererEvenementsExamen(data) {
    return data.map(examen => ({
        title: examen.nom,
        start: examen.date,
        backgroundColor: '#007bff',
        borderColor: '#007bff',
        description: examen.description
    }));
}

function chargerTableauNote(data) {
    const tableau = document.getElementById("tableauNote");
    tableau.innerHTML = ""; // Reset du tableau si déjà rempli

    const maxNote = getMaxNote(data);

    // Création de l'en-tête
    const thead = document.createElement("thead");
    thead.classList.add("table-light");
    const headerRow = document.createElement("tr");

    // Colonne "Branche"
    const thBranche = document.createElement("th");
    thBranche.textContent = "Branche";
    //happendChild permet 
    headerRow.appendChild(thBranche);

    // Colonnes "Note 1", "Note 2", ...
    for (let i = 0; i < maxNote; i++) {
        const th = document.createElement("th");
        th.textContent = `Note ${i + 1}`;
        headerRow.appendChild(th);
    }

    // Colonne "Moyenne"
    const thMoyenne = document.createElement("th");
    thMoyenne.textContent = "Moyenne";
    headerRow.appendChild(thMoyenne);

    thead.appendChild(headerRow);
    tableau.appendChild(thead);

    const tbody = document.createElement("tbody");

    let totalGeneral = 0;
    let totalNotes = 0;

    for (let item of data) {
        const tr = document.createElement("tr");
    
        const tdBranche = document.createElement("td");
        tdBranche.textContent = item.branche;
        tr.appendChild(tdBranche);
    
        let notes = item.notes || []; 
        let somme = 0;
    
        for (let i = 0; i < maxNote; i++) {
            const tdNote = document.createElement("td");
            if (notes[i] !== undefined) {
                tdNote.textContent = notes[i];
                somme += notes[i];
                totalGeneral += notes[i];
                totalNotes++;
            } else {
                tdNote.textContent = "-";
            }
            tr.appendChild(tdNote);
        }
    
        // Moyenne par branche
        const moyenne = notes.length > 0 ? (somme / notes.length).toFixed(2) : "-";
        const tdMoyenne = document.createElement("td");
        tdMoyenne.classList.add("fw-bold");
        tdMoyenne.textContent = moyenne;
        tr.appendChild(tdMoyenne);
    
        tbody.appendChild(tr); 
    }

    // Ligne Moyenne générale
    const trMoyenneGen = document.createElement("tr");
    const tdLabel = document.createElement("td");
    tdLabel.classList.add("fw-bold");
    tdLabel.textContent = "Moyenne générale";
    trMoyenneGen.appendChild(tdLabel);

    for (let i = 0; i < maxNote; i++) {
        const td = document.createElement("td");
        td.textContent = ""; // vide pour aligner
        trMoyenneGen.appendChild(td);
    }

    const tdMoyGen = document.createElement("td");
    tdMoyGen.classList.add("fw-bold");
    tdMoyGen.textContent = totalNotes > 0 ? (totalGeneral / totalNotes).toFixed(2) : "-";
    trMoyenneGen.appendChild(tdMoyGen);

    tbody.appendChild(trMoyenneGen);
    tableau.appendChild(tbody);
}




function getMaxNote(data) {
    let maxNote = 0;
    for (let item of data) {
        if (item.notes.length > maxNote) {
            maxNote = item.notes.length;
        }
    }
    return maxNote;
}

function CallbackError(request, status, error) {
    if (request.status === 401) {
        alert("Le mot de passe est incorrect");

    } else if (request.status === 404) {
        alert("Page demandée non trouvée [404] !");
    }
    else if (request.status === 400) {
        alert("Merci de remplir tous les champs");
    } else {
        alert("erreur : " + error + ", request: " + request.status + ", status: " + status);
    }
}


$(document).ready(function () {
    // Affichage du nom de l'utilisateur
    // const username = localStorage.getItem('username') || 'Élève';
    //document.getElementById('welcomeMessage').textContent += username;

    //listener deconnexion
    var butDisconnect = $("#btnDisconnect");
        butDisconnect.click(async function (event) {
            event.preventDefault();
            const confirmation = confirm("Voulez-vous vraiment vous déconnecter ?");
            if (confirmation) {
                disconnect(
                    () => {
                        alert("Vous êtes deconnecté avec succès");
                        window.location.href = "../ihm/login.html";
                    },
                    () => alert("Une erreur est survenue lors de la déconnexion")
                );
            }
        });

        getExamenByEleve('Curty', chargerCalendrier, CallbackError);
        getNoteByEleve('Curty', chargerTableauNote, CallbackError);

});