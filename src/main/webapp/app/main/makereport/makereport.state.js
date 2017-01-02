(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('makereport', {
            parent: 'app',
            url: '/makereport',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/main/makereport/makereport.html',
                    controller: 'MakeReportController',
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
