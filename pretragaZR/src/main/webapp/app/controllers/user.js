app.controller('UserController', ['$scope', '$routeParams', 'ThesisService', 'UserService', function ($scope, $routeParams, ThesisService, UserService) {

        $scope.userId = $routeParams.userId;

        UserService.getUserById($scope.userId, function (user) {
            $scope.user = user;
        });

    }]);