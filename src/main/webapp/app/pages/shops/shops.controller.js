(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('ShopsController', ShopsController);

    ShopsController.$inject = ['$http', '$rootScope', '$state'];

    function ShopsController($http, $rootScope, $state) {
        var vm = this;

        vm.shops = [];

        vm.load = load;
        vm.showShop = showShop;

        load();


        function load(){
            $http({
                method: 'GET',
                url: '/api/shops',
            }).then(function (response) {
                vm.shops = response.data;
                console.log(vm.shops)
            }, function() {
               console.log("Error")
            });
        }

        function showShop(shop){
            $rootScope.currentShop = shop;
        }

        vm.changeStatus = function(id){
            $http({
                method: 'GET',
                url: '/api/shops/changestatus/' + id,
            }).then(function (response) {
                load();
            }, function() {
                console.log("Error")
            });
        }

    }
})();
