(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('ExtractCategoryController', ExtractCategoryController);

    ExtractCategoryController.$inject = ['$http', '$rootScope', '$state'];

    function ExtractCategoryController($http, $rootScope, $state) {
        var vm = this;


        vm.url = 'http://sportix.by/';
        vm.isCheckedUrl = false;
        vm.isValidUrl = true;


        vm.selector = '.navbar-cat-collapse ul li a';
        vm.isCheckedSelector = false;
        vm.foundSelector = '';

        vm.template = '/catalog/';
        vm.excludeUrl = '';
        vm.categories = [];

        vm.checkUrl = function () {
            $http({
                method: 'POST',
                url: '/api/checkurl',
                data: vm.url
            }).then(function () {
                vm.isValidUrl = true;
                vm.isCheckedUrl = true;
            }, function () {
                vm.isValidUrl = false;
                vm.isCheckedUrl = true;
            });
        }

        vm.checkSelector = function () {
            $http({
                method: 'POST',
                url: '/api/checkselector',
                data: {
                    url: vm.url,
                    selector: vm.selector
                }
            }).then(function (response) {
                console.log("Success");
                vm.isCheckedSelector = true;
                vm.foundSelector = response.data.content;
            }, function errorCallback(response) {
                vm.isCheckedSelector = true;
                vm.foundSelector = '';
            });
        }

        vm.extractCategories = function () {
            var data = {
                url: vm.url,
                mandotoryPartUrl: vm.template,
                excludeUrl: vm.excludeUrl,
                selector: vm.selector
            }
            console.log(data);


            $http({
                method: 'POST',
                url: '/api/extractCategories',
                data: data
            }).then(function (response) {
                console.log("Success");
                vm.categories = response.data;
            }, function (response) {
                console.log("Error");
                vm.categories = [];
            });
        }

        vm.nextView = function(){
            $rootScope.categories = vm.categories;
            $rootScope.url = vm.url;
            $state.go('extractproducts')
        }

    }
})();
