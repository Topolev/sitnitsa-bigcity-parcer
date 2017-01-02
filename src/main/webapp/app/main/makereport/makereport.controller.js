(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('MakeReportController', MakeReportController);

    MakeReportController.$inject = ['$http', '$rootScope', '$state'];

    function MakeReportController($http, $rootScope, $state) {
        var vm = this;

        vm.rootUrl = $rootScope.url;
        vm.products = $rootScope.products;
        vm.categories = $rootScope.categories;

        console.log(vm.products);

        vm.hasSubdirectories = function(category){
            return idSubCategories.length > 0;
        }

    }
})();
