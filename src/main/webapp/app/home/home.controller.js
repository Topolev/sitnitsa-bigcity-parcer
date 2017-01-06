(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService','Auth' ,'$state', '$rootScope', '$http'];

    function HomeController ($scope, Principal, LoginService, Auth, $state, $rootScope, $http) {
        var vm = this;

        vm.url = "http://artbuket.by/";

        vm.selectorProductLink = ".product-preview";

        vm.extractProductOnCategoryPage = true;

        vm.rulesextractproduct = {};
        vm.rulesextractproduct.selectorName = ".product-preview .title a";
        vm.rulesextractproduct.selectorPrice = ".product-preview .price-block span";
        vm.rulesextractproduct.selectorImage = ".product-preview .preview a img";

        vm.levelCategoryRule = [
            {selector: 'ul.navbar-usual-menu > li'},
            {selector: 'ul > li'},
            {selector: 'ul > li'}
        ];

        vm.addNewCategoryLevel = function(){
            vm.levelCategoryRule.push({selector: ''});
        }
        vm.deleteLastCategoryLevel = function(){
            vm.levelCategoryRule.pop();
        }


        vm.extractCategories = function () {
            var data = {
                rootUrl: vm.url,
                ruleCategory:  vm.levelCategoryRule
            }

            $http({
                method: 'POST',
                url: '/api/buildCategories',
                data: data
            }).then(function (response) {
                console.log("Success");
                vm.categories = response.data;
                console.log(vm.categories);
                buildTreeCategories(vm.categories)
            }, function (response) {
                console.log("Error");
                vm.categories = [];
            });
        }

        vm.extractProductLinks = function(){
            var ruleExtractProduct = null;
            if (vm.extractProductOnCategoryPage){
                 ruleExtractProduct = {
                    rootUrl: vm.url,
                    selectors: [
                        {field: "name", selector: vm.rulesextractproduct.selectorName},
                        {field: "price", selector: vm.rulesextractproduct.selectorPrice},
                        {field: "image", selector: vm.rulesextractproduct.selectorImage}]
                }
            }

            var data = {
                rootUrl: vm.url,
                selector: vm.selectorProductLink,
                rulesExtractProduct: ruleExtractProduct
            }

            $http({
                method: 'POST',
                url: '/api/buildProductLink',
                data: data
            }).then(function (response) {
                console.log("Success");
                vm.productLink = response.data;
                console.log(vm.productLink);
            }, function (response) {
                console.log("Error");
                vm.productLink = [];
            });



        }

        /*
        function buildTreeCategories(categories){
            for (var i = 0; i<categories.length; i++){
                console.log("lev0:" + categories[i].name);
                if (categories[i].children != null){
                    showChildrenCategories(1, categories[i].children);
                }
            }
        }

        function showChildrenCategories(level, categories){
            for (var i = 0; i<categories.length; i++) {
                console.log("lev" + level + categories[i].name);
                if (categories[i].children != null){
                    showChildrenCategories(level + 1, categories[i].children);
                }
            }
        }
*/







 /*       vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;

        $scope.$on('authenticationSuccess', function () {
            console.log("HomeController: authenticationSuccess");
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                console.log('HomeController: getAccount():');
                console.log(account);
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
*/
    }
})();
