(function ($) {
    'use strict';
    $.widget('custom.OrderTable', {
        options: {
            productStore: {},
        },
        _create: function () {
            var productStore = this._getProductStore();
            this._initTable();

            productStore.on("order:loaded", this._onDataLoaded.bind(this));
            productStore.on("order:cleared", this._onDataCleared.bind(this));
        },

        _onDataLoaded: function(event, data) {
            this.datatable.clear();
            this.datatable.rows.add(data.data).draw();
        },

        _onDataCleared: function (event) {
            this.datatable.clear();
            this.datatable.rows.add([]).draw();
        },

        _initTable: function () {
            this.datatable = this.element.DataTable({
                searching: false,
                columns: this._getColumnsConfig(),
                language: {
                    emptyTable: 'No products ordered !'
                }
            });
        },

        _getColumnsConfig: function () {
            return [
                {title: 'id', data: 'id', visible: false},
                {title: 'Name', data: 'name'},
                {title: 'Category', data: 'category.name'},
                {title: 'CategoryId', data: 'category.id', visible: false},
                {title: 'Price', data: 'price'},
                {title: 'Quantity', data: 'quantity'},
            ]
        },

        _destroy: function () {
            this.datatable.clear();
            this.datatable.destroy();
        },

        _getProductStore: function () {
            return this.options.productStore;
        }
    });
})(jQuery);