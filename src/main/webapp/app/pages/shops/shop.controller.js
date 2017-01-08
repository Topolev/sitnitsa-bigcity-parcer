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
                url: '/api/shops/' + id
            }).then(function (response) {
                vm.shop = response.data;
                console.log(vm.shop);
            }, function () {
                console.log("Error");
            });


            $http({
                method: 'GET',
                url: '/api/ruleExtractCategories/' + id
            }).then(function (response) {
                vm.ruleExtractCategories = response.data.ruleCategories;
                console.log(response.data);
            }, function () {
                console.log("Error");
                vm.ruleExtractCategories = [{'selector': ''}];
            });


            $http({
                method: 'GET',
                url: '/api/ruleExtractProductLink/' + id
            }).then(function (response) {
                vm.ruleExtractProductLink = response.data;
                console.log(vm.ruleExtractProductLink);
            }, function () {
                console.log("Error")
            });


             $http({
             method: 'GET',
             url: '/api/ruleExtractProduct/' + id
             }).then(function (response) {
             vm.ruleExtractProduct = response.data;

             for (var selector of vm.ruleExtractProduct.selectors){
                 switch(selector.field){
                     case "name": {
                         vm.ruleExtractProduct.selectorName = selector.selector;
                         break;
                     }
                     case "image": {
                         vm.ruleExtractProduct.selectorImage = selector.selector;
                         break;
                     }
                     case "composition": {
                         vm.ruleExtractProduct.selectorComposition = selector.selector;
                         break;
                     }
                     case "summary": {
                         vm.ruleExtractProduct.selectorSummary = selector.selector;
                     }
                     case "description": {
                         vm.ruleExtractProduct.selectorDescription = selector.selector;
                     }
                     case "price": {
                         vm.ruleExtractProduct.selectorPrice = selector.selector;
                         break;
                     }
                     case "oldprice": {
                         vm.ruleExtractProduct.selectorOldPrice = selector.selector;
                         break;
                     }
                 }
             }
             console.log(vm.ruleExtractProduct);
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

        /*EXTRACT CATEGORIES*/
        function followToCategories() {
            vm.page = "CATEGORIES";
        }

        vm.addNewCategoryLevel = function () {
            vm.ruleExtractCategories.push({selector: ''});
        }
        vm.deleteLastCategoryLevel = function () {
            vm.ruleExtractCategories.pop();
        }

        vm.extractCategories = function () {
            console.log("EXTRACT CATEGORIES");
            var data = {
                shop: vm.shop,
                ruleCategories: vm.ruleExtractCategories
            }


            $http({
                method: 'POST',
                url: '/api/extractCategories',
                data: data
            }).then(function (response) {
                console.log("Success");
                vm.categories = response.data;
                console.log(vm.categories);
            }, function (response) {
                console.log("Error");
                vm.categories = [];
            });
        }

        /*END EXTRACT CATEGORIES*/


        /*EXTRACT PRODUCT LINKS*/
        function followToProducts() {
            vm.page = "PRODUCTS";
        }

        vm.extractProductLinks = function () {
            $http({
                method: 'POST',
                url: '/api/extractProductLinks',
                data: {
                    shop: vm.shop,
                    selector: vm.ruleExtractProductLink.selector,
                    paginatorTemplate: vm.ruleExtractProductLink.paginatorTemplate,
                    paginatorStartPage: vm.ruleExtractProductLink.paginatorStartPage,
                    paginatorStepChange: vm.ruleExtractProductLink.paginatorStepChange
                }
            }).then(function (response) {
                console.log("Success");
                console.log(response.data);
                vm.productLinks = response.data;
            }, function errorCallback(response) {
                console.log("Error")
            });
        }


        /*END EXTRACT PRODUCT LINKS*/


        /*EXTRACT PRODUCT*/
        function followToProduct() {
            vm.page = "PRODUCT";
        }

        vm.extractProduct = function () {
            $http({
                method: 'POST',
                url: '/api/extractProducts',
                data: {
                    shop: vm.shop,
                    selectors: [
                        {field: "name", selector: vm.ruleExtractProduct.selectorName},
                        {field: "image", selector: vm.ruleExtractProduct.selectorImage},
                        {field: "composition", selector: vm.ruleExtractProduct.selectorComposition},
                        {field: "summary", selector: vm.ruleExtractProduct.selectorSummary},
                        {field: "description", selector: vm.ruleExtractProduct.selectorDescription},
                        {field: "price", selector: vm.ruleExtractProduct.selectorPrice},
                        {field: "oldprice", selector: vm.ruleExtractProduct.selectorOldPrice}
                    ]
                }
            }).then(function (response) {
                console.log("Success");
                console.log(response.data);
                vm.products = response.data;
            }, function errorCallback(response) {
                console.log("Error")
            });
        }


        /*END EXTRACT PRODUCT*/


        vm.saveRules = function(){
            var rulesExtractCategories = {
                shop: vm.shop,
                ruleCategories: vm.ruleExtractCategories
            }
            var ruleExtractProductLink = {
                shop: vm.shop,
                selector: vm.ruleExtractProductLink.selector
            }

            var ruleExtractProduct = {
                shop: vm.shop,
                selectors: [
                    {field: "name", selector: vm.ruleExtractProduct.selectorName},
                    {field: "price", selector: vm.ruleExtractProduct.selectorPrice},
                    {field: "image", selector: vm.ruleExtractProduct.selectorImage}]
            }

            console.log("ATTTTT")
            console.log(ruleExtractProduct);

            $http({
                method: 'POST',
                url: '/api/updateRules',
                data: {
                    shop: vm.shop,
                    rulesExtractCategories: rulesExtractCategories,
                    ruleExtractProductLink: ruleExtractProductLink,
                    ruleExtractProduct: ruleExtractProduct
                }
            }).then(function (response) {
                console.log("Success");
                console.log(response.data);
                vm.products = response.data;
            }, function errorCallback(response) {
                console.log("Error")
            });

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

        function callBackInvalidUrl() {
            vm.isValidUrl = false;
            vm.isCheckedUrl = true;
        }

        vm.checkUrl = function () {
            getCheckUrlPromise().then(function () {
                vm.isValidUrl = true;
                vm.isCheckedUrl = true;
            }, callBackInvalidUrl);
        }


        /*vm.extractCategories = function () {
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
         }*/

/*
        vm.extractProducts = function () {
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
        }*/

/*
        vm.selectorsProduct = [
            {field: "name", selector: '.product-details h1'},
            {field: "price", selector: '.price-new small'},
            {field: "image", selector: '#imgZoom .zoomImg'}
        ]*/

        /*
        vm.extractProduct = function () {
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
        }*/


    }
})();
