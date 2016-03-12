
app.controller('SettingsController', ['$scope', '$rootScope', 'CourseService', 'StudiesService', 'SubjectService', 'TagService', 'DTOptionsBuilder', function ($scope, $rootScope, CourseService, StudiesService, SubjectService, TagService, DTOptionsBuilder) {

        //INIT
        $scope.newStudyLevelName = "";
        $scope.newStudyLevelNameShort = "";

        $scope.newCourseName = "";
        $scope.newCourseNameShort = "";
        $scope.newCourseStudies = [];
        
        $scope.newSubjectName = "";
        $scope.newSubjectCourses = [];

        StudiesService.getAllStudies(function (studies) {
            $scope.studies = studies;
        });

        CourseService.getAllCourses(function (courses) {
            $scope.courses = courses;
        });

        SubjectService.getAllSubjects(function (subjects) {
            $scope.subjects = subjects;
        });

        TagService.getAllTags(function (tags) {
            $scope.tags = tags;
        });

        $scope.dtOptions = DTOptionsBuilder.newOptions()
                .withPaginationType('full_numbers')
                .withDisplayLength(10);
        //END INIT


        //STUDIES OPERATIONS

        $scope.createStudyLevel = function () {
            StudiesService.createStudyLevel($scope.newStudyLevelName, $scope.newStudyLevelNameShort, function (response) {
                $scope.studies.push(response);
                $scope.newStudyLevelName = "";
                $scope.newStudyLevelNameShort = "";
                $rootScope.errorMessage = "";
            });
        };

        $scope.updateStudyLevel = function () {
            StudiesService.updateStudyLevel($scope.updateStudyLevelName, $scope.updateStudyLevelNameShort, $scope.updateStudyLevelId, function (response) {
                $scope.studies.splice(findIndexById($scope.studies, $scope.updateStudyLevelId), 1);
                $scope.studies.push(response);
                $rootScope.errorMessage = "";
            });
        };

        $scope.deleteStudyLevel = function (study) {
            if (confirm("Da li zelite da obrisete smer " + study.name + " ?")) {
                StudiesService.deleteStudyLevel(study.id, function (response) {
                    $scope.studies.splice(findIndexById($scope.studies, study.id), 1);
                });
            }
        }

        //COURSE OPERATIONS

        $scope.createCourse = function () {
            for (i = 0; i < $scope.studies.length; i++) {
                var item = $scope.studies[i];
                if (item.checked) {
                    $scope.newCourseStudies.push($scope.studies[i].id);
                    item.checked = false;
                }
            }
            CourseService.createCourse($scope.newCourseName, $scope.newCourseNameShort, $scope.newCourseStudies, function (response) {
                $scope.courses.push(response);
                $scope.newCourseName = "";
                $scope.newCourseNameShort = "";
                $scope.newCourseStudies = [];
                $rootScope.errorMessage = "";
            });
        };

        $scope.updateCourse = function () {
            for (i = 0; i < $scope.studies.length; i++) {
                var item = $scope.studies[i];
                if (item.checked) {
                    $scope.newCourseStudies.push($scope.studies[i].id);
                    item.checked = false;
                }
            }
            CourseService.updateCourse($scope.updateCourseName, $scope.updateCourseShortName, $scope.updateCourseId, $scope.newCourseStudies, function (response) {
                $scope.courses.splice(findIndexById($scope.courses, $scope.updateCourseId), 1);
                $scope.courses.push(response);
                $scope.newCourseStudies = [];
                $rootScope.errorMessage = "";
            });
        };

        $scope.deleteCourse = function (course) {
            if (confirm("Da li zelite da obrisete smer " + course.name + " ?")) {
                CourseService.deleteCourse(course.id, function (response) {
                    $scope.courses.splice(findIndexById($scope.courses, course.id), 1);
                });
            }
        }

        //SUBJECT OPERATIONS

        $scope.createSubject = function () {
            for (i = 0; i < $scope.courses.length; i++) {
                var item = $scope.courses[i];
                if (item.checked) {
                    $scope.newSubjectCourses.push($scope.courses[i].id);
                    item.checked = false;
                }
            }
            SubjectService.createSubject($scope.newSubjectName, $scope.newSubjectCourses, function (response) {
                $scope.subjects.push(response);
                $scope.newSubjectName = "";
                $scope.newSubjectCourses = [];
                $rootScope.errorMessage = "";
            });
        };

        $scope.updateSubject = function () {
            for (i = 0; i < $scope.courses.length; i++) {
                var item = $scope.courses[i];
                if (item.checked) {
                    $scope.newSubjectCourses.push($scope.courses[i].id);
                    item.checked = false;
                }
            }

            SubjectService.updateSubject($scope.updateSubjectId, $scope.updateSubjectName, $scope.newSubjectCourses, function (response) {
                $scope.subjects.splice(findIndexById($scope.subjects, $scope.updateSubjectId), 1);
                $scope.subjects.push(response);
                $scope.updateSubjectName = "";
                $scope.newSubjectCourses = [];
                $rootScope.errorMessage = "";
            });
        };


        $scope.deleteSubject = function (subject) {
            if (confirm("Da li zelite da obrisete predmet " + subject.name + " ?")) {
                SubjectService.deleteSubject(subject.id, function (response) {

                    $scope.subjects.splice(findIndexById($scope.subjects, subject.id), 1);
                });
            }
        };


        //TAG OPERATIONS
        $scope.deleteTag = function (tag) {
            if (confirm("Da li zelite da obrisete tag #" + tag.value + " ?")) {
                TagService.deleteTag(tag.id, function (response) {
                    $scope.tags.splice(findIndexById($scope.courses, tag.id), 1);
                });
            }
        };

        //HELPER METHODS

        $scope.editStudyLevelModalInit = function (study) {
            $scope.updateStudyLevelId = study.id;
            $scope.updateStudyLevelName = study.name;
            $scope.updateStudyLevelNameShort = study.nameShort;
        };

        $scope.editCourseModalInit = function (course) {
            for (i = 0; i < $scope.studies.length; i++) {
                for (j = 0; j < course.studies.length; j++) {
                    if ($scope.studies[i].id === course.studies[j].id) {
                        $scope.studies[i].checked = true;
                    }
                }
            }
            $scope.updateCourseId = course.id;
            $scope.updateCourseName = course.name;
            $scope.updateCourseShortName = course.nameShort;
        };

        $scope.editSubjectModalInit = function (id, subjectName, subjectCourses) {
            for (i = 0; i < $scope.courses.length; i++) {
                for (j = 0; j < subjectCourses.length; j++) {
                    if ($scope.courses[i].id === subjectCourses[j].id) {
                        $scope.courses[i].checked = true;
                    }
                }
            }
            $scope.updateSubjectId = id;
            $scope.updateSubjectName = subjectName;
        };

        /*$scope.getInfo = function (id) {
         index = findIndexById($scope.courses, id);
         var course = $scope.courses[index];
         $scope.courseInfoName = course.name;
         $scope.courseInfoNameShort = course.nameShort;
         }*/

        var findIndexById = function (collection, id) {
            for (i = 0; i < collection.length; i++) {
                if (collection[i].id === id) {
                    return i;
                }
            }
        };
    }]);