$().ready(function () {
    http = new HttpService();
    indexCtrl = new IndexCtrl();
    http.centraliserErreurHttp(indexCtrl.afficherErreurHttp);
});

class IndexCtrl {
    constructor
}