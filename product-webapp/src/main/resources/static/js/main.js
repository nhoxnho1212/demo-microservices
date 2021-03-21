$(document).ready(function () {

    var listCategoriesSelected = [];
    var listProductsOrder = [];
    var totalPrice = 0;

    //  Select box Category
    $("#selectCategory").select2();

    $('#selectCategory').on("select2:select", function (e) {
        console.log(e.params.data.id);
        listCategoriesSelected.push(e.params.data.id);
        // Reload fragment table product when searching
        tableProducts.draw();
    });

    $('#selectCategory').on("select2:unselect", function (e) {
        console.log(e.params.data.id);
        listCategoriesSelected = listCategoriesSelected.filter(function (value) {
            return value != e.params.data.id;
        });
        // Reload fragment table product when searching
        tableProducts.draw();
    });

    // Data table Product
    var tableProducts = $('#tableProducts').DataTable({
        searching: true,
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
            url: '/product1?name=',
            dataSrc: 'message',
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

    // Filter searching for data table
    $.fn.dataTable.ext.search.push(
        function (settings, data, dataIndex) {
            if (settings.nTable.id === 'tableProducts') {
                var categoryId = data[4]; // use data for the category id column
                if (listCategoriesSelected.length > 0)
                    return listCategoriesSelected.includes(categoryId);
            }

            return true;
        }
    );

    $("#btnSearchProduct").click(function () {
        var url = '/product1?name=' + $("#inputSearchProduct").val();

        tableProducts.ajax.url(url).load();
        data = tableProducts.rows.data;
    });

    // Add product when press button add cart
    $('#tableProducts tbody').on('click', '.btn-add-product-cart', function () {
        var productAdded = tableProducts.row($(this).parents('tr')).data();
        let isExisted = false;
        for (var product of listProductsOrder) {
            if (productAdded.id == product.id) {
                product.quantity = product.quantity == null ? 1 : product.quantity + 1;
                totalPrice += product.price;
                isExisted = true;
                break;
            }
        }

        if (!isExisted) {
            productAdded.quantity = 1
            console.log(productAdded);
            listProductsOrder.push(productAdded);
            totalPrice += productAdded.price;
        }

        $('#cartCount').text(function (i, oldText) {
            return 1 + Number(oldText);
        });
    });

    // Modal Order
    $('.btn-order').on('click', function (event) {
        // Update order table
        tableProductsOrder.clear();
        tableProductsOrder.rows.add(listProductsOrder);
        tableProductsOrder.draw();

        $('#txtTotalPrice').text(totalPrice);

        var modal = bootbox.dialog({
            message: $("#modalAddCartContent").html(),
            title: "Order",
            buttons: [
                {
                    label: "Order",
                    className: 'btn btn-primary',
                    callback: function () {
                        resetCart();

                        toastr.success("Thank you for your purchase", "Order", {
                            "closeButton": true,
                            "newestOnTop": false,
                            "progressBar": false,
                            "positionClass": "toast-bottom-right",
                            "onclick": null,
                            "showDuration": "300",
                            "hideDuration": "1000",
                            "timeOut": "5000",
                            "extendedTimeOut": "1000",
                            "showEasing": "swing",
                            "hideEasing": "linear",
                            "showMethod": "fadeIn",
                            "hideMethod": "fadeOut"
                        });
                    }
                },
                {
                    label: "Cancel",
                    className: 'btn btn-default',
                    callback: function () {

                    }
                }
            ],
            show: false,
            onEscape: function () {
                modal.modal("hide");
            }
        });
        modal.modal("show");
    });

    // Data table order
    var tableProductsOrder = $('#tableProductsOrder').DataTable({
        searching: true,
        columnDefs: [
            {
                "targets": [0, 3],
                "visible": false,
            }
        ],
        columns: [
            {title: 'id', data: 'id'},
            {title: 'Name', data: 'name'},
            {title: 'Category', data: 'category.name'},
            {title: 'CategoryId', data: 'category.id'},
            {title: 'Price', data: 'price'},
            {title: 'Quantity', data: 'quantity'},
        ],
        language: {
            'emptyTable': 'No products ordered !'
        }
    });

    var resetCart = function () {
        listProductsOrder = [];
        totalPrice = 0;

        tableProductsOrder.clear();
        tableProductsOrder.rows.add(listProductsOrder);
        tableProductsOrder.draw();

        $('#cartCount').text(0);
    }
});

