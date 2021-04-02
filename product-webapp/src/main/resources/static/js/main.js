$(document).ready(function () {
    var url = '/product/searchAndPaging',
        method = 'post';

    var categories = new categoriesSelection();
    categories.setup();

    var inputSearchProduct = new inputSearchProducts();
    var tableProduct = new productTable(url, method, inputSearchProduct.name, categories.getSelectCategories());
    var modalCart = new modalCartProduct();

    tableProduct.setupTableProduct();
    modalCart.setupModal();

    tableProduct.addProductToCart(modalCart.tableProductsOrder);
    categories.searchTableWithCategories(tableProduct.table());

    inputSearchProduct.search(tableProduct.table());
});

