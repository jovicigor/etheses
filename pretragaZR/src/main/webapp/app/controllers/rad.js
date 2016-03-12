
app.controller('RadController', ['$scope', '$rootScope', '$routeParams', 'ThesisService', 'CommentService', function ($scope, $rootScope, $routeParams, ThesisService, CommentService) {


//INIT
        $scope.thesisId = $routeParams.thesisId;
        $scope.message = "";
        ThesisService.getThesisById($scope.thesisId, function (response) {
            $scope.thesis = response;
        });
        CommentService.getCommentsByThesisId($scope.thesisId, function (response) {
            $scope.comments = response;
        });
//END INIT

        //Comment operations
        $scope.deleteComment = function (commentId) {
            if (confirm("Da li ste sigurni da zelite da obrisete komentar?")) {
                CommentService.deleteComment(commentId, function (response) {
                    $scope.comments.splice(findIndexById($scope.comments, commentId), 1);
                });
            }
        };

        $scope.postComment = function () {
            if ($scope.message.length > 0) {
                CommentService.postComment($scope.thesisId, $scope.message, function (response) {
                    $scope.comments.push(response);
                    $scope.message = "";
                    $rootScope.errorMessage = "";
                });
            } else {
                alert("Nije moguce postaviti prazan komentar.");
            }
        };

        $scope.download = function (thesisId) {
            var path = $rootScope.webApiPath + 'theses/' + thesisId + '/download';
            window.open(path, '_blank', '');
        };

//helper Function
        var findIndexById = function (collection, id) {
            for (i = 0; i < collection.length; i++) {
                if (collection[i].id === id) {
                    return i;
                }
            }
        }
    }]);
