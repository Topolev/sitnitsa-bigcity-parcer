(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('ShopController', ShopController);

    ShopController.$inject = ['$http', '$rootScope', '$state', '$stateParams'];

    function ShopController($http, $rootScope, $state, $stateParams) {
        var vm = this;

        vm.load = load;
        vm.followToCategories = followToCategories;
        vm.followToProducts = followToProducts;
        vm.followToProduct = followToProduct;
        vm.followToShop = followToShop;
        vm.followToShops = followToShops;
        vm.makeReport = makeReport;


        vm.page = "SHOP";

        vm.isCheckedUrl = false;
        vm.isValidUrl = true;

        vm.shop = {};
        vm.rulesextractcategories = {};
        vm.rulesextractproducts = {};
        vm.rulesextractproduct = {};

        vm.categories = [];
        vm.productLinks = [];
        vm.products = [];

        vm.load($stateParams.id);


        function load(id) {
            $http({
                method: 'GET',
                url: '/api/shops/' + id,
            }).then(function (response) {
                vm.shop = response.data;
                console.log(vm.shop);
            }, function () {
                console.log("Error")
            });

            $http({
                method: 'GET',
                url: '/api/rulesextractcategories/' + id,
            }).then(function (response) {
                vm.rulesextractcategories = response.data;
                console.log(vm.rulesextractcategories);
            }, function () {
                console.log("Error")
            });

            $http({
                method: 'GET',
                url: '/api/rulesextractproducts/' + id,
            }).then(function (response) {
                vm.rulesextractproducts = response.data;
                console.log(vm.rulesextractproducts);
            }, function () {
                console.log("Error")
            });

            $http({
                method: 'GET',
                url: '/api/rulesextractproduct/' + id,
            }).then(function (response) {
                vm.rulesextractproduct = response.data;
                console.log(vm.rulesextractproduct);
            }, function () {
                console.log("Error")
            });
        }

        function followToShop() {
            vm.page = "SHOP";
        }

        function followToShops() {
            $state.go("shops");
        }

        function followToCategories() {
            getCheckUrlPromise().then(function () {
                vm.isValidUrl = true;
                vm.isCheckedUrl = true;
                vm.page = "CATEGORIES";
            }, callBackInvalidUrl);
        }

        function followToProducts() {
            vm.page = "PRODUCTS";
        }

        function followToProduct() {
            vm.page = "PRODUCT";
        }

        function makeReport() {
            console.log("MAKE REPOERT")
        }


        function getCheckUrlPromise() {
            return $http({
                method: 'POST',
                url: '/api/checkurl',
                data: vm.shop.url
            });
        }

        function callBackInvalidUrl(){
            vm.isValidUrl = false;
            vm.isCheckedUrl = true;
        }

        vm.checkUrl = function () {
           getCheckUrlPromise().then(function () {
                vm.isValidUrl = true;
                vm.isCheckedUrl = true;
            }, callBackInvalidUrl);
        }


        vm.extractCategories = function () {
            var data = {
                idShop: vm.shop.id,
                rootUrl: vm.shop.url,
                prefix: vm.rulesextractcategories.prefix,
                excludeUrls: vm.rulesextractcategories.excludeUrls,
                selector: vm.rulesextractcategories.selector,
            }
            console.log(data);

            $http({
                method: 'POST',
                url: '/api/extractCategories',
                data: data
            }).then(function (response) {
                console.log("Success");
                vm.categories = response.data;
                buildTreeCategory(response.data);
                console.log(vm.categories);
            }, function (response) {
                console.log("Error");
                vm.categories = [];
            });
        }

        function buildTreeCategory(categories){

        }

        vm.extractProducts = function(){
            $http({
                method: 'POST',
                url: '/api/extractProducts',
                data: {
                    rootUrl: vm.shop.url,
                    categories: vm.categories,
                    selector: vm.rulesextractproducts.selector
                }
            }).then(function (response) {
                console.log("Success");
                console.log(response.data);
                vm.productLinks = response.data;
            }, function errorCallback(response) {
                console.log("Error")
            });
        }



        vm.selectorsProduct = [
            {field: "name", selector: '.product-details h1'},
            {field: "price", selector: '.price-new small'},
            {field: "image", selector: '#imgZoom .zoomImg'}
        ]

        vm.extractProduct = function(){
            var data = {
                rootUrl: vm.shop.url,
                idShop: vm.shop.id,
                productLinks: vm.productLinks,
                selectors: [
                    {field: "name", selector: vm.rulesextractproduct.selectorName},
                    {field: "price", selector: vm.rulesextractproduct.selectorPrice},
                    {field: "image", selector: vm.rulesextractproduct.selectorImage}]
            };
            console.log(data);

            $http({
                method: 'POST',
                url: '/api/extractproduct',
                data: data
            }).then(function (response) {
                console.log("Success");
                console.log(response.data);
                vm.products = response.data;
            }, function (response) {
                console.log("Error");
            });
        }



    }
})();
