/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


app.controller('UsersController', ['$scope', '$rootScope', 'UserService', 'AuthenticationService', 'CourseService', 'DTOptionsBuilder', function ($scope, $rootScope, UserService, AuthenticationService, CourseService, DTOptionsBuilder) {

        //INIT
        UserService.getAllUsers(function (users) {
            $scope.users = users;
        });

        $scope.dtOptions = DTOptionsBuilder.newOptions()
                .withPaginationType('full_numbers')
                .withDisplayLength(10);
        //END INIT


        //USER OPERATIONS
        $scope.updateUser = function () {

            UserService.updateUser($scope.newUserId, $scope.newUserName, $scope.newUserLastName, $scope.newUserTranscript, $scope.newUserCourseName, $scope.newUserBiography, $scope.newUserInterests, function (response) {

                UserService.grantAdmin($scope.newUserId, $scope.admin, function (response) {
                    $scope.users.splice(findIndexById($scope.users, $scope.newUserId), 1);
                    $scope.users.push(response);
                });
                $scope.newUserName = "";
                $scope.newUserLastName = "";
                $scope.newUserTranscript = "";
                $scope.newUserBiography = "";
                $scope.newUserInterests = "";
            });
        };

        $scope.deleteUser = function (user) {
            if (confirm("Da li zelite da obrisete korisnika " + user.email + " ?")) {
                UserService.deleteUser(user.id, function (response) {
                    $scope.users.splice(findIndexById($scope.users, user.id), 1);
                });
            }
        };

        $scope.createUser = function () {
            $rootScope.errorMessage = "";
            $scope.password = $scope.passwordReg;
            $scope.password2 = $scope.passwordRegRep;

            if ($scope.password !== $scope.password2) {
                $rootScope.errorMessage += " Unete lozinke se moraju poklapati.";
            }

            if ($rootScope.errorMessage.length === 0) {
                AuthenticationService.registerUser($scope.emailReg, $scope.passwordReg, function (response) {
                    $scope.users.push(response.user);
                    $scope.emailReg = "";
                    $scope.passwordReg = "";
                    $scope.passwordRegRep = "";
                });
            }
        }
        //HELPER METHODS
        $scope.editUserModalInit = function (user) {
            CourseService.getAllCourses(function (response) {
                $scope.courses = response;
            });
            $scope.newUserId = user.id;
            $scope.newUserName = user.firstName;
            $scope.newUserLastName = user.lastName;
            if (user.course) {
                $scope.newUserCourseName = user.course.name;
            }
            $scope.newUserTranscript = user.studentsTranscript;
            $scope.newUserBiography = user.biography;
            $scope.newUserInterests = user.interests;
            $scope.admin = user.admin;
        }

        $scope.reinit = function () {
            $scope.infoUserName = "";
            $scope.infoUserLastName = "";
            $scope.infoUserEmail = "";
            $scope.infoUserThesisId = "";
            $scope.infoUserThesisName = "";
            $scope.infoUserTranscript = "";
            $scope.infoUserBiography = "";
            $scope.infoUserInterests = "";
            $scope.infoUserCourseName = "";
        }

        $scope.getInfoModalInit = function (user) {
            
            $scope.infoUserName = user.firstName;
            $scope.infoUserLastName = user.lastName;
            $scope.infoUserEmail = user.email;
            
            if (user.thesis) {
                $scope.infoUserThesisId = user.thesis.id;
                $scope.infoUserThesisName = user.thesis.name;
            }
            if (user.course) {
                $scope.infoUserCourseName = user.course.name;
            }
            $scope.infoUserTranscript = user.studentsTranscript;
            $scope.infoUserBiography = user.biography;
            $scope.infoUserInterests = user.interests;

        }

        var findIndexById = function (collection, id) {
            for (i = 0; i < collection.length; i++) {
                if (collection[i].id == id) {
                    return i;
                }
            }
        }
    }]);