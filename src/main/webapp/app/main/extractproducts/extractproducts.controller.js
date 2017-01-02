(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('ExtractProductsController', ExtractProductsController);

    ExtractProductsController.$inject = ['$http', '$rootScope', '$state'];

    function ExtractProductsController($http, $rootScope, $state) {
        var vm = this;

        vm.categories = $rootScope.categories;
        vm.rootUrl = $rootScope.url;

        vm.selector = '.product-seo a';
        vm.isCheckedSelector = false;
        vm.foundSelector = '';



        vm.checkSelector = function(){
            console.log(vm.rootUrl  + vm.categories[0].href);
            $http({
                method: 'POST',
                url: '/api/checkselector',
                data: {
                    url: vm.rootUrl  + vm.categories[0].href,
                    selector: vm.selector
                }
            }).then(function (response) {
                console.log("Success");
                vm.isCheckedSelector = true;
                vm.foundSelector = response.data.content;
            }, function (response) {
                vm.isCheckedSelector = true;
                vm.foundSelector = '';
            });
        }

        vm.extractProducts = function(){
            $http({
                method: 'POST',
                url: '/api/extractProducts',
                data: {
                    rootUrl: vm.rootUrl,
                    categories: vm.categories,
                    selector: vm.selector
                }
            }).then(function (response) {
                console.log("Success");
                console.log(response.data);
                vm.products = response.data;
            }, function errorCallback(response) {
                console.log("Error")
            });
        }

        vm.nextStep = function(){
            $rootScope.productLinks = vm.products;
            $state.go('extractproduct');
        }

    }
})();
