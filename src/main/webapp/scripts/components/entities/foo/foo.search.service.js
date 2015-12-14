'use strict';

angular.module('jhipsterMysqlApp')
    .factory('FooSearch', function ($resource) {
        return $resource('api/_search/foos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
