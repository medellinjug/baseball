var app = angular.module('demo', [])

.controller('Baseball', function($scope, $http) {
	$scope.urlService = "http://127.0.0.1:8080/baseball/play";
	$scope.showPlayList = true;
	$scope.showPhoto = false;
	$scope.photo = false;
	$scope.showDivs = function(playList){
		if (playList){
			$scope.showNewForm = false;
			$scope.showPlayList = true;
		}else{
			$scope.formTitle = "Add New Play"

			$scope.code = "";
			$scope.name = "";
			$scope.type = null;

			/*angular.element(document.getElementById("pic")).val(null);*/
			$scope.showNewForm = true;
			$scope.showPlayList = false;
		}
	}
	
	$scope.reset = function(form) {
    if (form) {
      form.$setPristine();
      form.$setUntouched();
    }
  };
  
	//Get play list
	$scope.getPlays = function(){

		$http.get($scope.urlService).
			then(function(response) {
				$scope.playList = response.data;
			});
		$scope.searchText = "";
	}
	
	//Get play by ID
	$scope.getPlayById = function(id){
		$http.get($scope.urlService+"/"+id).
			then(function(response) {
				$scope.play = response.data;
				$scope.showDetail();				
			});
		$scope.searchText = "";		
	}
	
	//Add/Update Employee
	$scope.submitPlay = function(){
		var addPlay ={
                  code:$scope.code,
                  name:$scope.name,
                  type:$scope.type
                };
		
		var res;
		if ($scope.id == null){
			res = $http.post($scope.urlService, JSON.stringify(addPlay), {
				headers: { 'Content-Type': 'application/json'}
				});
		}else{
			res = $http.put($scope.urlService+"/"+$scope.id, JSON.stringify(addPlay), {
				headers: { 'Content-Type': 'application/json'}});
		}
		res.success(function(data, status, headers, config) {
			$scope.message = data;
			$scope.getPlays();
			$scope.showDivs(true);
			
		});
		res.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});	
	
	}
	
	//Delete play
	$scope.deletePlay = function() {
		res = $http.delete($scope.urlService+"/"+$scope.id);
		res.success(function(data, status, headers, config) {
			$scope.getPlays();
			$scope.showDivs(true);
			
		});
		res.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});	
	}
	
	//Search Plays
	$scope.searchPlays = function (){
            $scope.playList = null;
    		$http.get($scope.urlService+"/plays/"+$scope.searchType+"&"+$scope.searchText).
    			then(function(response) {
    				$scope.playList = response.data;

    			});
    	}
	/*
	$scope.searchPlays = function (){
		$http.get($scope.urlService+"/"+$scope.searchType+"/"+$scope.searchText).
			then(function(response) {
				$scope.playList = response.data;
			});
	}*/
	
	$scope.showDetail = function(){		
		$scope.showDivs(false);
		$scope.showPhoto = true;
		$scope.formTitle = "Update Play"
		$scope.id = $scope.play.id;
		$scope.code = $scope.play.code;
		$scope.name = $scope.play.name;
		$scope.type = $scope.play.type;

	}
	

});

