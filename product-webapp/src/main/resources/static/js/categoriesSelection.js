function categoriesSelection() {
    var selectCategories,
        instance = this;

    function setup() {
        selectCategories = $("#selectCategory");
        selectCategories.select2();
    }
    function searchTableWithCategories(table) {

        selectCategories.on("select2:select", function (e) {
           table.draw();
        });

        selectCategories.on("select2:unselect", function (e) {
            table.draw();
        });
    }

    this.setup = setup;
    this.searchTableWithCategories = searchTableWithCategories;
    this.getSelectCategories = function () {
        return selectCategories;
    };
}
