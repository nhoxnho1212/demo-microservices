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
        },
        _getProductStore: function () {
            return this.options.productStore;
        }
    });
})(jQuery);