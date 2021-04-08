(function ($) {
    'use strict';
    $.widget('custom.OrderModal', {
        options: {
            productStore: {},
            toastrConfig: {
                "closeButton": true,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-bottom-right",
                "onclick": null,
            },
        },

        _create: function () {
            var productStore = this._getProductStore();

            productStore.on("modal:showDialogOrderSuccess", this._onShowDialogOrderSuccess.bind(this));
            productStore.on("modal:show", this._onShow.bind(this));
        },

        _onShowDialogOrderSuccess: function () {
            var toastrConfig = this._getToastrConfig();
            toastr.success("Thank you for your purchase", "Order", toastrConfig);
        },

        _onShow: function () {
            var _this = this;
            var modal = bootbox.dialog({
                message: _this.element.html(),
                title: "Order",
                buttons: [
                    {
                        label: "Order",
                        className: 'btn btn-primary btn-reset-cart',
                        callback: function () {
                            _this._getProductStore().resetCart();
                        }
                    },
                    {
                        label: "Cancel",
                        className: 'btn btn-default',
                        callback: function () {
                            this.modal("hide")
                        },
                    }
                ],
                show: false,
                onEscape: function () {
                   this.modal("hide")
                },
            });
            modal.modal("show");
        },

        _getProductStore: function () {
            return this.options.productStore;
        },

        _getToastrConfig: function () {
            return this.options.toastrConfig;
        },
    });
})(jQuery)