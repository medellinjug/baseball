var app = angular.module('demo', [])

.controller('Baseball', function($scope, $http) {

$scope.goHome = function(){
    window.location="index.html";
}


	$scope.urlServicePlay = "http://127.0.0.1:8080/baseball/play";
    $scope.urlServiceStrategy = "http://127.0.0.1:8080/baseball/strategy";
/*
	$scope.urlServicePlay = "baseball/play";
    $scope.urlServiceStrategy = "baseball/strategy";*/

    $scope.showNewStrategy = true;
	$scope.showStrategyPlayList = false;
	$scope.showStrategyResult = false;
    $scope.showStrategyPlay = false;

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

		$http.get($scope.urlServicePlay).
			then(function(response) {
				$scope.playList = response.data;
			});
		$scope.searchText = "";
	}
	
	//Get play by ID
	$scope.getPlayById = function(id){
		$http.get($scope.urlServicePlay+"/"+id).
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
			res = $http.post($scope.urlServicePlay, JSON.stringify(addPlay), {
				headers: { 'Content-Type': 'application/json'}
				});
		}else{
			res = $http.put($scope.urlServicePlay+"/"+$scope.id, JSON.stringify(addPlay), {
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
		res = $http.delete($scope.urlServicePlay+"/"+$scope.id);
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
    		$http.get($scope.urlServicePlay+"/plays/"+$scope.searchType+"&"+$scope.searchText).
    			then(function(response) {
    				$scope.playList = response.data;

    			});
    	}
	/*
	$scope.searchPlays = function (){
		$http.get($scope.urlServicePlay+"/"+$scope.searchType+"/"+$scope.searchText).
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

/*Strategy controller*/

    $scope.goStrategy = function(){

        $scope.formTitle = "Add New Strategy"

        $scope.showNewStrategy = true;
        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = false;

        $scope.height = "";
        $scope.type = null;

        $scope.id = null;


         $scope.form.$setPristine();
	}

	//Create a new or Update Strategy
    $scope.submitStrategy = function(){


        var addStrategy ={
                height:$scope.height,
                type:$scope.type
        };
        var res;
        var newStrategy = true;
        var id;
        if ($scope.id == null){


            res = $http.post($scope.urlServiceStrategy, JSON.stringify(addStrategy), {
                headers: { 'Content-Type': 'application/json'}
                });
        }else{
            id=$scope.id;
            newStrategy = false;
            res = $http.put($scope.urlServiceStrategy+"/"+$scope.id, JSON.stringify(addStrategy), {
                headers: { 'Content-Type': 'application/json'}});
        }



        res.success(function(data, status, headers, config) {
            $scope.message = data;
            /*$scope.getPlays();
            $scope.showDivs(true);
            */


            if(newStrategy){

                $scope.strategy = data;
                $scope.id = $scope.strategy.id;
                $scope.type = $scope.strategy.type;

                $scope.showNewStrategy = false;
                $scope.showStrategyPlayList = true;
                $scope.formTitle = "Generate Strategy"
                $scope.showStrategyResult = false;


            }else{

                $scope.formTitle = "Results of strategy"
                $scope.showStrategyResult = true;
                $scope.showStrategyPlayList = false;
                $scope.getStrategyById($scope.id );
            }
        });

        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
            }
        );
    }


	//Process a Strategy
	$scope.processStrategy = function(){


		var genStrategy ={
          /*strategy:$scope.strategy,*/
            id:$scope.id,
            strategy:$scope.strategy
         };

		var res;
		res = $http.put($scope.urlServiceStrategy+"/"+$scope.id, JSON.stringify(genStrategy), {
        				headers: { 'Content-Type': 'application/json'}
        				});
		/*
		if ($scope.id == null){
			res = $http.post($scope.urlServiceStrategy, JSON.stringify(addPlay), {
				headers: { 'Content-Type': 'application/json'}
				});
		}else{
			res = $http.put($scope.urlServiceStrategy+"/"+$scope.id, JSON.stringify(addPlay), {
				headers: { 'Content-Type': 'application/json'}});
		}*/
		res.success(function(data, status, headers, config) {
			$scope.message = data;
			$scope.getPlays();
			$scope.showDivs(true);

		});
		res.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});

		$scope.showNewStrategy = false;
        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = true;

	}

	//Get strategy by ID
	$scope.getStrategyById = function(id){

		$http.get($scope.urlServiceStrategy+"/"+id).
			then(function(response) {
			    $scope.strategy = response.data;
			});
		$scope.searchText = "";
	}


	//Get strategyPlay by ID
	$scope.getStrategyPlayById = function(idStrategy, idPlay){


		$http.get($scope.urlServiceStrategy+"/play/"+idStrategy+"&"+idPlay).
			then(function(response) {
				$scope.strategyPlay = response.data;
				$scope.quantity = $scope.strategyPlay.quantity;

				$scope.showDetailStrategyPlay();


			});

	}

	$scope.showDetailStrategyPlay = function(){
    		$scope.showStrategyPlay = true;

    		/*$scope.id = $scope.play.id;
    		$scope.code = $scope.play.code;
    		$scope.name = $scope.play.name;
    		$scope.type = $scope.play.type;
            */
    	}


    //Update strategyPlay
    $scope.submitStrategyPlay = function(){


        var updStrategyPlay ={
                  code:$scope.code,
                  name:$scope.name,
                  type:$scope.type,
                  quantity:$scope.quantity,
                  idStrategy:$scope.strategyPlay.idStrategy,
                  play:$scope.strategyPlay.play
                };

        var res;

          res = $http.put($scope.urlServiceStrategy+"/play/"+$scope.strategyPlay.idStrategy, JSON.stringify(updStrategyPlay), {
                headers: { 'Content-Type': 'application/json'}});

        res.success(function(data, status, headers, config) {
            $scope.message = data;

            $scope.showStrategyPlay = false;

            $scope.getStrategyById($scope.strategyPlay.idStrategy);


           // $("#strategyPlayModal").modal('hide');
        });


        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });




    }

    $scope.generateStrategy = function(strategy){


                    $scope.modifyField = false;
    				$scope.viewField = false;


        var genStrategy ={
          /*strategy:$scope.strategy,*/
            id:$scope.id,
            strategy:$scope.strategy,
            date:$scope.strategy.date,
            width:$scope.strategy.width,
            height:$scope.strategy.height,
            type:$scope.strategy.type,
            id:$scope.strategy.id,
            strategyPlayList:$scope.strategy.strategyPlayList
         };


        var res;
        res = $http.put($scope.urlServiceStrategy+"/"+$scope.id, JSON.stringify(genStrategy), {
                        headers: { 'Content-Type': 'application/json'}
                        });

         res.success(function(data, status, headers, config) {
            $scope.message = data;
            $scope.showStrategyPlay = false;
            $scope.getStrategyById($scope.id);
        });



        res.error(function(data, status, headers, config) {

            alert( "failure message: " + JSON.stringify({data: data}));
        });



        $scope.showNewStrategy = false;
        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = true;




      };




 $scope.modify = function(tableData){

        $scope.editingData[tableData.id] = true;
    };


    $scope.update = function(strategyPlay){

       // $scope.editingData[strategyPlay.id] = false;



        $scope.strategyPlay = strategyPlay;


         var updStrategyPlay ={

                          code:$scope.strategyPlay.code,
                          name:$scope.strategyPlay.name,
                          type:$scope.strategyPlay.type,
                          quantity:$scope.strategyPlay.quantity,
                          idStrategy:$scope.strategyPlay.idStrategy,
                          play:$scope.strategyPlay.play,
                          strategyPlay: $scope.strategyPlay
                        };

                var res;

                  res = $http.put($scope.urlServiceStrategy+"/play/"+$scope.strategyPlay.idStrategy, JSON.stringify(updStrategyPlay), {
                        headers: { 'Content-Type': 'application/json'}});

                res.success(function(data, status, headers, config) {
                    $scope.message = data;

                    $scope.showStrategyPlay = false;

                    $scope.getStrategyById($scope.strategyPlay.idStrategy);


                   // $("#strategyPlayModal").modal('hide');
                });


                res.error(function(data, status, headers, config) {
                    alert( "failure message: " + JSON.stringify({data: data}));
                });


    };

});





