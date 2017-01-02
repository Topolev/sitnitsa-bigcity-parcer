(function () {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('MainNavbarController', MainNavbarController);

    MainNavbarController.$inject = ['$http', '$rootScope', '$state', '$stateParams', 'Principal', 'Auth', '$scope'];

    function MainNavbarController($http, $rootScope, $state, $stateParams, Principal, Auth, $scope) {
        var vm = this;

        vm.logout = logout;

        getAccount();


        $scope.$on('authenticationSuccess', function() {
            console.log('MainNavbar: authenticationSuccess')
            getAccount();
        });

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                console.log("Navbar")
                console.log(account);
            });
        }

        function logout() {
            console.log("Logout")
            Auth.logout();
            $state.go('login');
        }

    }
})();
