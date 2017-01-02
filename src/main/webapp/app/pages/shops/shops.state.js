(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('shops', {
            parent: 'page',
            url: '/shops',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'pagecontent@page': {
                    templateUrl: 'app/pages/shops/shops.html',
                    controller: 'ShopsController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('shop', {
            parent: 'page',
            url: '/shops/:id',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'pagecontent@page': {
                    templateUrl: 'app/pages/shops/shop.html',
                    controller: 'ShopController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
