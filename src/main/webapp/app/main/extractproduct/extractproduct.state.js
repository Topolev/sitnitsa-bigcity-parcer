(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('extractproduct', {
            parent: 'app',
            url: '/extractproduct',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/main/extractproduct/extractproduct.html',
                    controller: 'ExtractProductController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
