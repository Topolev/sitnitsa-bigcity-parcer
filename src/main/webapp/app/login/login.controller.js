(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', 'Principal', 'LoginService','Auth' ,'$state', '$rootScope', 'AuthServerProvider', '$http'];

    function LoginController ($scope, Principal, LoginService, Auth, $state, $rootScope, AuthServerProvider, $http) {
        var vm = this;

        vm.login = login;
        vm.authenticationError = false;

        vm.username = null;
        vm.password = null;
        vm.rememberMe = true;



        function login (event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                $state.go('shops');
                /*$uibModalInstance.close();
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }*/

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is succesful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();

                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function (response) {
                console.log(response);
                console.log(response.status)
                if (response.status == 418){
                    vm.errorMessage = "<strong>Failed to sign in!</strong> User isn't activated";
                } else if (response.status == 419){
                    vm.errorMessage = "<strong>Failed to sign in!</strong> Your company is deactivated. Contact your administrator.";
                } else{
                    vm.errorMessage = "<strong>Failed to sign in!</strong> Please check your credentials and try again.";
                }

                vm.authenticationError = true;
            });
        }




        /*
        function login () {
            var data = 'j_username=' + vm.username +
                '&j_password=' + vm.password +
                '&remember-me=' + vm.rememberMe + '&submit=Login';

            return $http.post('api/authentication', data, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (response) {
                Principal.identity();
                $rootScope.$broadcast('authenticationSuccess');
                $state.go("shops")
            });

        }


        $scope.$on('authenticationSuccess', function() {
            console.log('authenticationSuccess!!!!!!!!!!!!');
            getAccount();
        });

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }


        /*vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }*/
    }
})();
