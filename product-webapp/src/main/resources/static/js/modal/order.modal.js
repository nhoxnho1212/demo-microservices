var OrderModal = (function ($) {
    'use strict';

    var defaltConfig = {}

    function OrderModal(config) {
        this._$ = $({});
        this._conf = $.extend(defaltConfig, config);
    }

    $.extend(OrderModal.prototype, {
        _showToastrOrderSuccess: function () {
            toastr.success("Thank you for your purchase", "Order", this._conf.toastrConfig);
        },

        showModal: function (html, table) {
            var _this = this;
            _this.modalCart = bootbox.dialog({
                message: html,
                title: "Order",
                buttons: [
                    {
                        label: "Order",
                        className: 'btn btn-primary btn-reset-cart',
                        callback: function () {
                            _this._conf.productStore.resetCart();
                            _this._showToastrOrderSuccess();
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
            _this.modalCart.modal("show");
        },

        getModalCart: function () {
            return this.modalCart;
        }
    });

    return OrderModal;
})(jQuery)