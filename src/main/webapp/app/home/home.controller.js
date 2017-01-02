(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService','Auth' ,'$state', '$rootScope'];

    function HomeController ($scope, Principal, LoginService, Auth, $state, $rootScope) {
        var vm = this;

        vm.account = null;
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

    }
})();
