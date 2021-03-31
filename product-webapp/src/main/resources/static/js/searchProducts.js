$(document).ready(function () {
    $("#btnSearchProduct").click(function () {
        let tableProducts = $("#tableProducts").DataTable();
        tableProducts.ajax.reload();
        data = tableProducts.rows.data;
    });

});