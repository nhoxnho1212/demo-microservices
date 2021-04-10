(function ($) {
    'use strict';
    $.widget('custom.ProductTable', {
        options: {
            productStore: {},
            searchName: "",
            searchCategories: [],
            searchUrl: "",
            searchMethod: "post"
        },
        _create: function () {
            var productStore = this.getProductStore();

            this._initProductTable();

            productStore.on('data:loaded', this._onDataLoaded.bind(this));
            productStore.on('data:reloaded', this._onDataReloaded.bind(this));
        },

        _onDataLoaded: function (event, data) {
            this.datatable.clear();
            this.datatable.rows.add(data.data).draw();
        },

        _onDataReloaded: function (event, option) {
            this.options = _.assignIn(this.options, option)
            this.datatable.clear();
            this.datatable.ajax.reload();
            event.datatable = this.datatable;
        },

        _initProductTable: function () {
           let _this = this;
           _this.datatable = _this.element.DataTable({
               searching: true,
               serverSide: true,
               columns: _this._getColumnsConfig(),
               ajax: _this._getAjaxConfig()
           })
        },

        _getAjaxConfig: function () {
            let _this = this;
            return {
                url: _this._getSearchUrl(),
                type: _this.options.searchMethod,
                dataType: 'json',
                contentType: 'application/json',
                data: function (data) {
                    data.name = _this._getSearchName();
                    let orders = data.order;
                    if (orders != null) {
                        for (let order of orders) {
                            order.columnName = _this._getColumnNameSorted(order.column, data.columns);
                        }
                    }
                    data.order = orders;
                    data.category = _this.options.searchCategories;
                    return JSON.stringify(data);
                },
                beforeSend: function () {
                    _this._handleLastRequest();
                },
                dataSrc: function (data) {
                    data.recordsTotal = data.total;
                    data.recordsFiltered = data.total;
                    return data.data;
                },
            }
        },

        _handleLastRequest: function () {
            if (this.datatable && this.datatable.hasOwnProperty('settings')) {
                this.datatable.settings()[0].jqXHR.abort();
            }
        },

        _getColumnNameSorted: function (column, columns) {
            var columnName = columns[column].data;
            if (columnName.includes('category')) {
                columnName = 'category'
            }
            return columnName;
        },

        _getColumnsConfig: function () {
            let _this = this;
            return _.concat(
                _this._getIdsColumnsConfig(),
                _this._getDataColumnsConfig(),
                _this._getAddButtonColumnConfig(),
            );
        },

        _getDataColumnsConfig: function () {
            return [
                {title: 'Name', data: 'name'},
                {title: 'Price', data: 'price'},
                {title: 'Category', data: 'category.name'},
            ]
        },

        _getIdsColumnsConfig: function () {
            return [
                {
                    title: 'Id',
                    data: 'id',
                    visible: false,
                },
                {
                    title: 'CategoryId',
                    data: 'category.id',
                    visible: false,
                },
            ];
        },

        _getAddButtonColumnConfig: function () {
            return {
                title: " ",
                orderable: false,
                render: function (data, type, row) {
                    return `<button class="btn btn-primary my-2 my-sm-0 ml-2 btn-add-product-cart" >
                        <i class="fa fa-plus"></i>
                        </button>`
                }
            }
        },

        getProductStore: function () {
            return this.options.productStore;
        },

        _getSearchUrl: function () {
            return this.options.searchUrl;
        },

        _getSearchName: function () {
            return this.options.searchName;
        },

        getTable: function () {
            return this.datatable;
        }

    });
})(jQuery);