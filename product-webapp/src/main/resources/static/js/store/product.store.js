var ProductStore = (function($) {
    'use strict';

    var defaultConfig = { };

    function ProductStore(config) {
        this._$ = $({});
        this._conf = $.extend(defaultConfig, config);
        this._orderData = [];
        this._numberOfOrderProduct = 0;
        this._totalPrice = 0;
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
            this._$.trigger('order:cleared');
            this._orderData = [];
            this._numberOfOrderProduct = 0;
            this._totalPrice = 0;
            this._conf.cartCountTxt.text(0);
            this._conf.txtTotalPrice.text(0);
        },

        addProductToCart: function (productAdded) {
            let isExisted = false;
            this._numberOfOrderProduct ++;

            for (let product of this._orderData) {
                if (productAdded.id === product.id) {
                    product.quantity = product.quantity == null ? 1 : product.quantity + 1;
                    isExisted = true;
                    break;
                }
            }

            if (!isExisted) {
                productAdded.quantity = 1
                this._orderData.push(productAdded);
            }
            this._totalPrice += productAdded.price;
            this._conf.txtTotalPrice.text(this._totalPrice);
            this._$.trigger('order:loaded', { data: this._orderData });
        },

    });

    return ProductStore;

})(jQuery);