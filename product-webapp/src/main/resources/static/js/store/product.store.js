var ProductStore = (function($) {
    'use strict';

    var defaultConfig = {
        orderData: [],
        numberOfOrderProduct: 0,
    }

    function ProductStore(config) {
        this._$ = $({});
        this._conf = $.extend(defaultConfig, config);
    }

    $.extend(ProductStore.prototype, {
        on: function (event, callback) {
            this._$.on(event, callback);
        },

        loadData: function (data) {
            this._$.trigger('data:loaded', data);
        },

        reload: function (options) {
            this._$.trigger('data:reloaded', options);
        },

        resetCart: function () {
            this._$.trigger('modal:showDialogOrderSuccess');
            this._$.trigger('order:cleared');
            this._conf.orderData = [];
            this._conf.numberOfOrderProduct = 0;
            this._conf.cartCountTxt.text(0);
        },

        addProductToCart: function (currentRow) {
            var productAdded = this._$.triggerHandler('data:addToCart', currentRow);
            let isExisted = false;
            this._conf.numberOfOrderProduct++;

            for (let product of this._conf.orderData) {
                if (productAdded.id === product.id) {
                    product.quantity = product.quantity == null ? 1 : product.quantity + 1;
                    isExisted = true;
                    break;
                }
            }

            if (!isExisted) {
                productAdded.quantity = 1
                this._conf.orderData.push(productAdded);

            }

            this._$.trigger('order:loaded', { data: this._conf.orderData });
        },

        showModal: function () {
            this._$.trigger('modal:show');
        }

    });

    return ProductStore;

})(jQuery);