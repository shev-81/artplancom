angular.module('front').controller('coreController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadAnimals = function (){
        // console.log("загружаем");
        $http.get(contextPath + 'api/v1/animals')
            .then(function (response) {
                $scope.AnimalList = response.data;
            });
    }

    $scope.details = function (id){
        // console.log("загружаем");
        $http.get(contextPath + 'api/v1/animals/' + id)
            .then(function (response) {
                $scope.Animal = response.data;
            });
    }


    $scope.loadAnimals();
});