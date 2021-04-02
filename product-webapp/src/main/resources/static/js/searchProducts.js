function inputSearchProducts() {
    'use strict'
    this.name = $("#inputSearchProduct");
    this.search = search;

    function search(tableProducts) {
        $("#btnSearchProduct").click(function () {
            tableProducts.ajax.reload();
        });
    }
}