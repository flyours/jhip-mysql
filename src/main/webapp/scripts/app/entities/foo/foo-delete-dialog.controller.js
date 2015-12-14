'use strict';

angular.module('jhipsterMysqlApp')
	.controller('FooDeleteController', function($scope, $uibModalInstance, entity, Foo) {

        $scope.foo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Foo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
