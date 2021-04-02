function modalCartProduct() {
    'use strict'

    var modal, instance = this;

    function setupModalCart() {
        setupDialogCart();
        setupTableProductOrder();
        instance.tableProductsOrder = $('#tableProductsOrder').DataTable();
    }

    function setupDialogCart() {
        $('.btn-order').on('click', function (event) {
            modal = bootbox.dialog({
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
    }

    function setupTableProductOrder() {
        $('#tableProductsOrder').DataTable({
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
    }

    function resetCart() {
        instance.tableProductsOrder.clear();
        instance.tableProductsOrder.rows.add([]);
        instance.tableProductsOrder.draw();

        $('#cartCount').text(0);
    }

    this.setupModal = setupModalCart;
}



