(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('page', {
            abstract: true,
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: 'app/pages/page.html'
                },
                'navbar@page': {
                    templateUrl: 'app/pages/navbar/navbar.html',
                    controller: 'MainNavbarController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
