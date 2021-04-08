const SEARCH_URL = '/product/searchAndPaging',
    SEARCH_URL_METHOD = 'post';

var productsStore = {},
    categories = {},
    $cartCountTxt = $('#cartCount'),
    $selectCategory = $('#selectCategory'),
    $tableProducts = $('#tableProducts'),
    $tableProductsOrder = $('#tableProductsOrder'),
    $inputSearchProduct = $("#inputSearchProduct"),
    $modalAddCartContent = $('#modalAddCartContent'),
    $btnSearchProduct = $("#btnSearchProduct"),
    $btnOrder = $('.btn-order');

function setupComponents() {
    productsStore = new ProductStore({cartCountTxt: $cartCountTxt});

    var defaultOptions = {
        productStore: productsStore
    };

    $selectCategory.select2();
    $tableProducts.ProductTable(_.extend({
        searchName: $inputSearchProduct.val(),
        searchCategories: $selectCategory.val(),
        searchUrl: SEARCH_URL,
        searchMethod: SEARCH_URL_METHOD
    }, defaultOptions));
    $modalAddCartContent.OrderModal(defaultOptions);
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
        productsStore.showModal();
    });
}

function registerButtonAddProductToCartEvent() {
    $tableProducts.on('click', '.btn-add-product-cart', function () {
        productsStore.addProductToCart($(this).parents('tr'));
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

