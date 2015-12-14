'use strict';

angular.module('jhipsterMysqlApp')
    .controller('FooController', function ($scope, $state, Foo, FooSearch, ParseLinks) {

        $scope.foos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Foo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.foos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            FooSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.foos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.foo = {
                name: null,
                addr: null,
                grade: null,
                birthday: null,
                salary: null,
                hobby: null,
                id: null
            };
        };
    });
