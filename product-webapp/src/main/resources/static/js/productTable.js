
function productTable(url, method, name, categories) {
    'use strict';
    var tableProducts,
        searchUrl = url,
        searchMethod = method,
        searchName = name,
        searchCategories = categories,
        instance = this;

    function setupTableProduct() {
        tableProducts = $('#tableProducts').DataTable({
            searching: true,
            serverSide: true,
            columnDefs: [
                {
                    "targets": 'table-column__no-sort',
                    "orderable": false,
                },
                {
                    "targets": [0, 4],
                    "visible": false,
                }
            ],
            ajax: {
                url: searchUrl,
                type: searchMethod,
                dataType: 'json',
                contentType: 'application/json',
                beforeSend: function () {
                    if (tableProducts && tableProducts.hasOwnProperty('settings')) {
                        tableProducts.settings()[0].jqXHR.abort();
                    }
                },
                // dataSrc: '',
                data: function (data) {
                    data.name = searchName.val();
                    let orders = data.order;
                    for (let order of orders) {
                        switch (order.column) {
                            case 0:
                                order.columnName = 'id';
                                break;
                            case 1:
                                order.columnName = 'name';
                                break;
                            case 2:
                                order.columnName = 'price';
                                break;
                            case 3:
                                order.columnName = 'category';
                                break;
                        }
                    }
                    data.order = orders;
                    data.category = searchCategories.val();
                    return JSON.stringify(data);
                },
                dataFilter: function (data) {
                    let json = JSON.parse(data);
                    json.recordsTotal = json.total;
                    json.recordsFiltered = json.total;
                    return JSON.stringify(json);
                }
            },
            columns: [
                {title: 'id', data: 'id'},
                {title: 'Name', data: 'name'},
                {title: 'Price', data: 'price'},
                {title: 'Category', data: 'category.name'},
                {title: 'CategoryId', data: 'category.id'},
                {
                    title: " ",
                    render: function (data, type, row) {
                        return `<button class="btn btn-primary my-2 my-sm-0 ml-2 btn-add-product-cart" >
                        <i class="fa fa-plus"></i>
                        </button>`
                    }
                }
            ],
        });

        filterSearchingDateTable();
    }

    function filterSearchingDateTable() {
        $.fn.dataTable.ext.search.push(
            function (settings, data, dataIndex) {
                if (settings.nTable.id === 'tableProducts') {
                    var categoryId = data[4]; // use data for the category id column
                    if (searchCategories.val().length > 0)
                        return $('#selectCategory').val().includes(categoryId);
                }

                return true;
            }
        );
    }

    function addProductToCart(tableProductsOrder){
        $('#tableProducts tbody').on('click', '.btn-add-product-cart', function () {
            let productAdded = tableProducts.row($(this).parents('tr')).data();
            let isExisted = false;
            let listProductsOrder = tableProductsOrder.rows().data().toArray();

            for (let product of listProductsOrder) {
                if (productAdded.id == product.id) {
                    product.quantity = product.quantity == null ? 1 : product.quantity + 1;
                    isExisted = true;
                    break;
                }
            }

            if (!isExisted) {
                productAdded.quantity = 1
                listProductsOrder.push(productAdded);
            }

            countNumberOfCartProducts();
            updateNewDataTableProductOrder(listProductsOrder, tableProductsOrder);
            updateTotalPriceCart(productAdded.price);
        });
    }

    function countNumberOfCartProducts() {
        $('#cartCount').text(function (i, oldText) {
            return 1 + Number(oldText);
        });
    }

    function updateNewDataTableProductOrder(newProducts, tableProductsOrder) {
        tableProductsOrder.clear();
        tableProductsOrder.rows.add(newProducts);
        tableProductsOrder.draw();
    }

    function updateTotalPriceCart(newProductAdded) {
        $('#txtTotalPrice').text(function(i, oldTotalPriceText) {
            return Number(oldTotalPriceText) + newProductAdded;
        });
    }

    this.setupTableProduct = setupTableProduct;
    this.addProductToCart = addProductToCart;
    this.table = function () {
        return tableProducts;
    };
}









