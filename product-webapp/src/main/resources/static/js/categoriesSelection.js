$(document).ready(function () {
    $("#selectCategory").select2();

    $('#selectCategory').on("select2:select", function (e) {
        // Reload fragment table product when searching
        $('#tableProducts').DataTable().draw();
    });

    $('#selectCategory').on("select2:unselect", function (e) {
        // Reload fragment table product when searching
        $('#tableProducts').DataTable().draw();
    });
});
