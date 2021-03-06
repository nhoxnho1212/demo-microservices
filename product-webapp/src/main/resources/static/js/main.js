const SEARCH_URL = '/product/searchAndPaging',
    SEARCH_URL_METHOD = 'post',
    $cartCountTxt = $('#cartCount'),
    $selectCategory = $('#selectCategory'),
    $tableProducts = $('#tableProducts'),
    $tableProductsOrder = $('#tableProductsOrder'),
    $inputSearchProduct = $("#inputSearchProduct"),
    $modalAddCartContent = $('#modalAddCartContent'),
    $btnSearchProduct = $("#btnSearchProduct"),
    $btnOrder = $('.btn-order'),
    $txtTotalPrice = $('#txtTotalPrice');

var productsStore = {},
    toastrConfig = {
        "closeButton": true,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-bottom-right",
        "onclick": null,
    };

function setupComponents() {
    productsStore = new ProductStore({cartCountTxt: $cartCountTxt, txtTotalPrice: $txtTotalPrice});
    var defaultOptions = {
        productStore: productsStore
    };

    $txtTotalPrice.text(0);
    $selectCategory.select2();
    $tableProducts.ProductTable(_.extend({
        searchName: $inputSearchProduct.val(),
        searchCategories: $selectCategory.val(),
        searchUrl: SEARCH_URL,
        searchMethod: SEARCH_URL_METHOD
    }, defaultOptions));
    $modalAddCartContent.OrderModal(_.extend({
        toastrConfig: toastrConfig
    }, defaultOptions));
    $tableProductsOrder.OrderTable(defaultOptions);

}

function countNumberOfCartProducts() {
    $cartCountTxt.text(function (i, oldText) {
        return 1 + Number(oldText);
    });
}

function registerSelectionCategoryEvent() {
    $selectCategory.on("select2:select", function (e) {
        productsStore.reload({searchCategories: $selectCategory.val()});
    });

    $selectCategory.on("select2:unselect", function (e) {
        productsStore.reload({searchCategories: $selectCategory.val()});
    });
}

function registerButtonSearchEvent() {
    $btnSearchProduct.on('click',function () {
        let options = {
            searchName: $inputSearchProduct.val(),
            searchCategories: $selectCategory.val(),
        }
        productsStore.reload(options);

    });
}

function registerButtonOrderEvent() {
    $btnOrder.on('click', function () {
        productsStore.showModalOrder();
    });
}

function registerButtonAddProductToCartEvent() {
    $tableProducts.on('click', '.btn-add-product-cart', function () {
        var productAdded = $tableProducts.ProductTable("getTable").row($(this).parents('tr')).data();
        productsStore.addProductToCart(productAdded);
        countNumberOfCartProducts();
    })
}

function registerWorkflowConfig() {
    registerButtonAddProductToCartEvent();
    registerButtonOrderEvent();
    registerButtonSearchEvent();
    registerSelectionCategoryEvent();
}


$(document).ready(function () {
    setupComponents();
    registerWorkflowConfig();
});

