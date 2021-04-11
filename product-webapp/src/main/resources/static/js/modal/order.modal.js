(function ($) {
    'use strict';

    $.widget('custom.OrderModal', {
        options: {
            productStore: {}
        },

        _create: function () {
            var productStore = this.options.productStore;

            productStore.on('modal:show', this._onShowModal.bind(this));
        },

        _showToastrOrderSuccess: function () {
            toastr.success("Thank you for your purchase", "Order", this.options.toastrConfig);
        },

        _onShowModal: function () {
            var _this = this;
            _this.modalCart = bootbox.dialog({
                message: _this.element.html(),
                title: "Order",
                buttons: _this._getButtonConfig(),
                show: true,
                onEscape: function () {
                    this.modal("hide")
                },
            });
        },


        _getButtonConfig: function () {
            var _this = this;
            return [
                {
                    label: "Order",
                    className: 'btn btn-primary btn-reset-cart',
                    callback: function () {
                        _this.options.productStore.resetCart();
                        _this._showToastrOrderSuccess();
                    }
                },
                {
                    label: "Cancel",
                    className: 'btn btn-default',
                }
            ]
        }
    })

})(jQuery);