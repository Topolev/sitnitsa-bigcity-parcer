(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app', {
            abstract: true,
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        console.log("resolve auth");
                        return Auth.authorize();
                    }
                ]
            }
        });
    }
})();
