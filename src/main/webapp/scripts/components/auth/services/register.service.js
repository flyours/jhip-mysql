'use strict';

angular.module('jhipsterMysqlApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


