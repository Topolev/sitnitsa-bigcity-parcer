(function() {
    'use strict';

    angular
        .module('jHipsterExampleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('shops', {
            parent: 'app',
            url: '/shops',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {

                'content@': {
                    templateUrl: 'app/main/shops/shops.html',
                    controller: 'ShopsController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('shop', {
            parent: 'app',
            url: '/shops/:id',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/main/shops/shop.html',
                    controller: 'ShopController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
