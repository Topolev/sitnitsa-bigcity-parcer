(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('extractproducts', {
            parent: 'app',
            url: '/extractproducts',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/main/extractproducts/extractproducts.html',
                    controller: 'ExtractProductsController',
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
