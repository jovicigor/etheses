app.factory('rejectionInterceptor', ['$rootScope', '$q', '$location',
    '$injector', '$cookieStore',
    function ($rootScope, $q, $location, $injector, $cookieStore) {
        var interceptor = {
            responseError: function (response) {
                if (response.status == 401 || response.status == 403) {
                    //var $http = $injector.get('$http');
                    $rootScope.logout();
                } else {
                    $rootScope.errorMessage = response.data.error;
                }
                return $q.reject(response);
            }
        };
        return interceptor;
    }]);
app.factory('AuthenticationService', ['$http', '$cookieStore', '$rootScope',
    function ($http, $cookieStore, $rootScope) {
        var service = {};
        service.login = function (username, password, callback) {

            $http.post($rootScope.webApiPath + 'authentication', {
                email: username,
                password: password
            }).success(function (response) {
                var loginAttempt = {
                    success: false,
                    ticket: null
                };
                if (!response.hasOwnProperty('error')) {
                    loginAttempt.success = true;
                    loginAttempt.ticket = response.ticket;
                } else {
                    loginAttempt.success = false;
                    loginAttempt.message = response.error;
                }
                callback(loginAttempt);
            });
        };
        service.registerUser = function (username, password, callback) {

            $http.post($rootScope.webApiPath + 'users', {
                email: username,
                password: password
            }).success(function (response) {
                var registrationAttempt = {
                    success: false,
                    user: null
                };
                registrationAttempt.success = true;
                registrationAttempt.user = response;
                callback(registrationAttempt);
            });
        };
        service.invalidateTicket = function () {
            $http({
                method: 'DELETE',
                url: $rootScope.webApiPath + 'authentication'
            });
        }
        return service;
    }]);
app.factory('Base64', function () {
    return {
        encode: function (input) {
            var sufix = "";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            for (var i = 0; i < 5; i++)
                sufix += possible.charAt(Math.floor(Math
                        .random()
                        * possible.length));
            var prefix = "CASH_";
            return prefix + input + sufix;
        },
        decode: function (input) {
            var doKaraktera = input.length - 5;
            return input.substring(5, doKaraktera);
        }
    };
});
app.factory('UserService', [
    '$http', '$rootScope',
    function ($http, $rootScope) {
        var service = {};
        service.getAllUsers = function (callback) {
            $http.get($rootScope.webApiPath + 'users', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getUserInfo = function (callback) {
            $http.get($rootScope.webApiPath + 'authentication', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getUserById = function (id, callback) {
            $http.get($rootScope.webApiPath + 'users/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteUser = function (id, callback) {
            $http.delete($rootScope.webApiPath + 'users/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.updateUser = function (id, firstName, lastName, studentsTranscript, courseName, biography, interests, callback) {
            $http.put($rootScope.webApiPath + 'users/' + id, {
                firstName: firstName,
                lastName: lastName,
                studentsTranscript: studentsTranscript,
                courseName: courseName,
                biography: biography,
                interests: interests
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.grantAdmin = function (id, isAdmin, callback) {
            $http.put($rootScope.webApiPath + 'users/' + id + "/admin", {
                admin: isAdmin
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);
app.factory('StudiesService', ['$http', '$rootScope', function ($http, $rootScope) {
        var service = {};
        service.getAllStudies = function (callback) {
            $http.get($rootScope.webApiPath + 'studies', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.createStudyLevel = function (name, nameShort, callback) {
            $http.post($rootScope.webApiPath + 'studies/', {
                name: name,
                nameShort: nameShort
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteStudyLevel = function (id, callback) {
            $http.delete($rootScope.webApiPath + 'studies/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.updateStudyLevel = function (name, nameShort, id, callback) {
            $http.put($rootScope.webApiPath + 'studies/' + id, {
                name: name,
                nameShort: nameShort
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);
app.factory('CourseService', ['$http', '$rootScope', function ($http, $rootScope) {
        var service = {};
        //pitanje je kada se dodaju predmeti
        service.getAllCourses = function (callback) {
            $http.get($rootScope.webApiPath + 'courses', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getCourseById = function (id, callback) {
            $http.get($rootScope.webApiPath + 'courses/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.createCourse = function (name, nameShort, studiesIDs, callback) {
            $http.post($rootScope.webApiPath + 'courses/', {
                name: name,
                nameShort: nameShort,
                studiesIDs: studiesIDs
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteCourse = function (id, callback) {
            $http.delete($rootScope.webApiPath + 'courses/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.updateCourse = function (name, nameShort, id, studiesIDs, callback) {
            $http.put($rootScope.webApiPath + 'courses/' + id, {
                name: name,
                nameShort: nameShort,
                studiesIDs: studiesIDs
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);
app.factory('SubjectService', ['$http', '$rootScope', function ($http, $rootScope) {
        var service = {};
        service.getAllSubjects = function (callback) {
            $http.get($rootScope.webApiPath + 'subjects', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getSubjectById = function (id, callback) {
            $http.get($rootScope.webApiPath + 'subjects/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.createSubject = function (name, courseIDs, callback) {
            $http.post($rootScope.webApiPath + 'subjects/', {
                name: name,
                courseIDs: courseIDs
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteSubject = function (id, callback) {
            $http.delete($rootScope.webApiPath + 'subjects/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.updateSubject = function (id, name, courseIDs, callback) {
            $http.put($rootScope.webApiPath + 'subjects/' + id, {
                name: name,
                courseIDs: courseIDs
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);
app.factory('ThesisService', [
    '$http', '$rootScope',
    function ($http, $rootScope) {
        var service = {};
        service.getThesesPage = function (pageNumber, pageSize, thesisName, tagValues, matchLimit, subjectName, courseName, studiesName, sortField, callback) {
            var url = $rootScope.webApiPath + "theses/advanced_search?";
            if (tagValues) {
                for (i = 0; i < tagValues.length; i++) {
                    url += 'tagValues=' + tagValues[i] + "&";
                }
            }
            $http({
                method: 'GET',
                url: url,
                params: {
                    pageNumber: pageNumber,
                    pageSize: pageSize,
                    thesisName: thesisName,
                    matchLimit: matchLimit,
                    subjectName: subjectName,
                    courseName: courseName,
                    studiesName: studiesName,
                    sortField: sortField
                }
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getAllTheses = function (callback) {
            $http.get($rootScope.webApiPath + 'theses', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getThesisById = function (id, callback) {
            $http.get($rootScope.webApiPath + 'theses/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.getThesisByUserId = function (id, callback) {
            $http.get($rootScope.webApiPath + 'theses/', {
                params: {
                    userID: id
                }}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteThesis = function (id, callback) {
            $http.delete($rootScope.webApiPath + 'theses/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.createThesis = function (name, grade, defenseDate, description, subjectName, userId, mentorId, tags, userName, userEmail, callback) {
            $http.post($rootScope.webApiPath + 'theses/', {
                name: name,
                grade: grade,
                defenseDate: defenseDate,
                description: description,
                subjectName: subjectName,
                userId: userId,
                mentorId: mentorId,
                tags: tags,
                userName: userName,
                userEmail: userEmail
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.updateThesis = function (id, name, grade, defenseDate, description, subjectName, userId, mentorId, tags, userName, userEmail, callback) {
            $http.put($rootScope.webApiPath + 'theses/' + id, {
                name: name,
                grade: grade,
                defenseDate: defenseDate,
                description: description,
                subjectName: subjectName,
                userId: userId,
                mentorId: mentorId,
                tags: tags,
                userName: userName,
                userEmail: userEmail
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);
app.factory('TagService', ['$http', '$rootScope', function ($http, $rootScope) {
        var service = {};
        service.getAllTags = function (callback) {
            $http.get($rootScope.webApiPath + 'tags', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteTag = function (id, callback) {
            $http.delete($rootScope.webApiPath + 'tags/' + id, {}).success(
                    function (response) {
                        callback(response);
                    });
        }
        ;
        return service;
    }]);
app.factory('CommentService', ['$http', '$rootScope', function ($http, $rootScope) {
        var service = {};
        service.getCommentsByThesisId = function (thesisId, callback) {
            $http.get($rootScope.webApiPath + 'theses/' + thesisId + "/comments", {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.deleteComment = function (commentId, callback) {
            $http.delete($rootScope.webApiPath + 'theses/comments/' + commentId, {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        service.postComment = function (thesisId, message, callback) {

            $http.post($rootScope.webApiPath + 'theses/' + thesisId + "/comments", {
                message: message
            }).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);
app.factory('ThesisUploadService', ['$http', '$rootScope', function ($http, $rootScope) {
        var service = {};
        service.uploadFile = function (thesisId, file, callback) {
            var fd = new FormData();
            fd.append('file', file);
            $http.post($rootScope.webApiPath + 'theses/' + thesisId + '/upload', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                    .success(function (response) {
                        callback(response);
                    })
                    .error(function () {
                    });
        };
        service.deleteFile = function (fileId, callback) {
            $http.delete($rootScope.webApiPath + 'theses/files/' + fileId, {})
                    .success(function (response) {
                        callback(response);
                    });
        };
        service.getAllFiles = function (callback) {
            $http.get($rootScope.webApiPath + 'theses/files', {}).success(
                    function (response) {
                        callback(response);
                    });
        };
        return service;
    }]);