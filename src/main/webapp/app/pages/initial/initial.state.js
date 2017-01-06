(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('initial', {
            parent: 'page',
            url: '/',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'pagecontent@page': {
                    templateUrl: 'app/pages/initial/initial.html'
                }
            }
        })
    }
})();
