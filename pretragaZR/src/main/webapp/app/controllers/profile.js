app.controller('ProfileController', ['$scope', '$rootScope', 'ThesisService', 'UserService', function ($scope, $rootScope, ThesisService, UserService) {
       
        UserService.getUserInfo(function (user) {
            
            $rootScope.user = user;
        });

    }]);