(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('ExtractProductController', ExtractProductController);

    ExtractProductController.$inject = ['$http', '$rootScope', '$state'];

    function ExtractProductController($http, $rootScope, $state) {
        var vm = this;

        vm.rootUrl = $rootScope.url;
        vm.products = $rootScope.productLinks;


        vm.selectors = {
            name: '.product-details h1',
            price: '.price-new small',
            image: '#imgZoom .zoomImg'
        };

        vm.selectorsProduct = [
            {field: "name", selector: '.product-details h1'},
            {field: "price", selector: '.price-new small'},
            {field: "image", selector: '#imgZoom .zoomImg'}
        ]

        vm.foundSelectors = {};



        vm.checkSelector = function(nameSelector){
            console.log(vm.rootUrl  + vm.products[0]);
            $http({
                method: 'POST',
                url: '/api/checkselector',
                data: {
                    url: vm.rootUrl  + vm.products[0],
                    selector: vm.selectors[nameSelector]
                }
            }).then(function (response) {
                console.log("Success");
                vm.foundSelectors[nameSelector] = response.data.content;
            }, function errorCallback(response) {
                console.log("Error");
                vm.foundSelectors[nameSelector] = '';
            });
        }

        vm.checkSrcImg = function(nameSelector){
            console.log(nameSelector);
            $http({
                method: 'POST',
                url: '/api/checkimgselector',
                data: {
                    url: vm.rootUrl  + vm.products[0],
                    selector: vm.selectors[nameSelector]
                }
            }).then(function successCallback(response) {
                console.log("Success");
                vm.foundSelectors[nameSelector] = response.data.content;
            }, function errorCallback(response) {
                console.log("Error");
                vm.foundSelectors[nameSelector] = '';
            });
        }

        vm.extractProduct = function(){
            var data = {
                rootUrl: vm.rootUrl,
                productUrls: vm.products,
                selectors: vm.selectorsProduct
            };
            console.log(data);

            $http({
                method: 'POST',
                url: '/api/extractproduct',
                data: data
            }).then(function successCallback(response) {
                console.log("Success");
                console.log(response.data);
                vm.results = response.data;
            }, function errorCallback(response) {
                console.log("Error");
            });
        }

        vm.nextStep = function(){
            console.log("nextStep")
            $rootScope.products = vm.results;
            $state.go('makereport');
        }



    }
})();
