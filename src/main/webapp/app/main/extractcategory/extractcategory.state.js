(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('category', {
            parent: 'app',
            url: '/extractcategory',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/main/extractcategory/extractcategory.html',
                    controller: 'ExtractCategoryController',
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
