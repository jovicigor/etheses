
app.controller('AdvancedSearchController', ['$scope', '$rootScope', '$routeParams', 'ThesisService', 'StudiesService', 'CourseService', 'SubjectService', 'TagService', function ($scope, $rootScope, $routeParams, ThesisService, StudiesService, CourseService, SubjectService, TagService) {

        //init the lists
        StudiesService.getAllStudies(function (response) {
            $scope.studies = response;
        });
        CourseService.getAllCourses(function (response) {
            $scope.courses = response;
        });
        SubjectService.getAllSubjects(function (response) {
            $scope.subjects = response;
        });

        TagService.getAllTags(function (response) {
            $scope.tags = response;
            for (i = 0; i < $scope.tags.length; i++) {
                $scope.tags[i].checked = false;
            }
        });


        //flag to hide 'pretraga' and course list
        $rootScope.logingIn = true;

        $scope.numberOfItemsPerLoad = 3;
        $scope.pageNumber = 1;
        $scope.totalPages = -1;

        $scope.searchPerformed = false;

        $scope.searchStudyLevel = "";
        $scope.searchSubject = "";
        $scope.searchCourse = "";
        $scope.searchTags = [];
        $scope.searchKeyWord = "";
        $scope.theses = [];

        if ($scope.tags) {
            for (i = 0; i < $scope.tags.length; i++) {
                $scope.tags[i].checked = false;
            }
        }

        if ($routeParams.expression) {
            $scope.expression = $routeParams.expression;
            ThesisService.getThesesPage(null, null, $scope.expression, [], null, null, null, null, null, function (response) {
                if (response.content.length === 0) {
                    $scope.noResults = true;
                }
                $scope.theses = response.content;
            });
            $rootScope.expression = "";
        }


        if ($routeParams.courseName) {
            $scope.searchCourseName = $routeParams.courseName;
            ThesisService.getThesesPage(null, null, null, [], null, null, $scope.searchCourseName, null, null, function (response) {
                if (response.content.length === 0) {
                    $scope.noResults = true;
                }
                $scope.theses = response.content;
            });
            $scope.searchCourseName = "";
        }

        if ($routeParams.tag) {
            $scope.searchTag = [$routeParams.tag];
            ThesisService.getThesesPage(null, null, null, $scope.searchTag, null, null, null, null, null, function (response) {
                if (response.content.length === 0) {
                    $scope.noResults = true;
                }
                $scope.theses = response.content;
            });
            $scope.searchTag = [];
        }



//FUNCTIONS DEFINITION

        $scope.performSearch = function () {

            $scope.searchTags = [];
            $scope.theses = [];
            $scope.pageNumber = 1;
            if ($scope.tags) {
                for (i = 0; i < $scope.tags.length; i++) {
                    if ($scope.tags[i].checked === true) {
                        $scope.searchTags.push($scope.tags[i].value);
                    }
                }
            }
            ThesisService.getThesesPage($scope.pageNumber, $scope.numberOfItemsPerLoad, $scope.searchKeyWord, $scope.searchTags, null, $scope.searchSubject, $scope.searchCourse, $scope.searchStudyLevel, null, function (response) {
                if (response.content.length === 0) {
                    $scope.noResults = true;
                }
                $scope.theses = response.content;
                $scope.totalPages = response.totalPages;
                $scope.searchPerformed = true;
                $scope.pageNumber++;
            });

        };
        $scope.loadMore = function () {

            ThesisService.getThesesPage($scope.pageNumber, $scope.numberOfItemsPerLoad, $scope.searchKeyWord, null, null, $scope.searchSubject, $scope.searchCourse, $scope.searchStudyLevel, null, function (response) {
                for (i = 0; i < response.content.length; i++) {
                    $scope.theses.push(response.content[i]);
                }
                $scope.pageNumber++;


            });
        };

        $scope.download = function (thesisId) {
            var path = '/zavrsni_radovi/webapi/theses/' + thesisId + '/download';
            window.open(path, '_blank', '');
        };


//END FUNCTION DEFINITION
//

    }]);

