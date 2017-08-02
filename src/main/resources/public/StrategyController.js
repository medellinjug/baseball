var app = angular.module('demo', [])

.controller('Baseball', function($scope, $http) {

$scope.goHome = function(){
    window.location="index.html";
}


	$scope.urlServicePlay = "http://127.0.0.1:8080/baseball/play";
    $scope.urlServiceStrategy = "http://127.0.0.1:8080/baseball/strategy";
    $scope.urlServicePlayer = "http://127.0.0.1:8080/baseball/player";
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

    $scope.showPlayerList = true;

	$scope.showDivs = function(playList){
		if (playList){
			$scope.showNewForm = false;
			$scope.showPlayList = true;
		}else{
			$scope.formTitle = "Add New Play"
            $scope.id = "";
			$scope.code = "";
			$scope.name = "";
			$scope.type = null;


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
				$scope.showDetailPlay();
			});
		$scope.searchText = "";		
	}
	
	//Add/Update Employee
	$scope.submitPlay = function(){
		var addPlay ={
		          id:$scope.id,
                  code:$scope.code,
                  name:$scope.name,
                  type:$scope.type
                };
		
		var res;
		if ($scope.id == ""){
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

        var params = "&"+$scope.searchText;
        if($scope.searchType!=null){
            params = $scope.searchType+"&"+$scope.searchText;
        }
        $http.get($scope.urlServicePlay+"/plays/"+params).
            then(function(response) {
                $scope.playList = response.data;
            });
    }

	$scope.showDetailPlay = function(){
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

        $scope.showNewStrategy = false;
        $scope.showStrategyList = true;

        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = false;

        $scope.rows = "";
        $scope.type = null;
        $scope.id = null;

        $scope.searchStrategies();
        /*
        $scope.formTitle = "Add New Strategy"
        $scope.showNewStrategy = true;
        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = false;

        $scope.rows = "";
        $scope.type = null;
        $scope.id = null;
        $scope.form.$setPristine();*/
	}

	 $scope.goAddStrategy = function(){
	    $scope.showStrategyList = false;
        $scope.formTitle = "Add New Strategy"
        $scope.showNewStrategy = true;
        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = false;

        $scope.rows = "";
        $scope.type = null;
        $scope.id = null;
        $scope.form.$setPristine();
    }


	//Search
    $scope.searchStrategies = function (){

        $scope.strategyList = null;
        if($scope.searchType!=null && $scope.searchType!=""){
            $http.get($scope.urlServiceStrategy+"/strategies/"+$scope.searchType).
                then(function(response) {
                    $scope.strategyList = response.data;
            });
        }else{

            $http.get($scope.urlServiceStrategy).
                then(function(response) {
                    $scope.strategyList = response.data;
            });

        }

    }

	//Create a new or Update Strategy
    $scope.submitStrategy = function(){
        var addStrategy ={
                rows:$scope.rows,
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
            res = $http.addStrategy($scope.urlServiceStrategy+"/"+$scope.id, JSON.stringify(addStrategy), {
                headers: { 'Content-Type': 'application/json'}});
        }

        res.success(function(data, status, headers, config) {
            $scope.message = data;

            if(newStrategy){

                $scope.strategy = data;
                $scope.id = $scope.strategy.id;
                $scope.type = $scope.strategy.type;

                $scope.showNewStrategy = false;
                $scope.showStrategyPlayList = true;
                $scope.formTitle = "Generate Strategy"
                $scope.showStrategyResult = false;
            } else{
                $scope.formTitle = "Results of strategy"
                $scope.showStrategyResult = true;
                $scope.showStrategyPlayList = false;
                $scope.getStrategyById($scope.id );
            }
        });

        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
            alert( "failure message: " + data.message);
            }
        );
    }

	//Get strategy by ID
	$scope.getStrategyById = function(id){
		$http.get($scope.urlServiceStrategy+"/"+id).
			then(function(response) {
			    $scope.strategy = response.data;
			});
		$scope.searchText = "";

        $scope.showNewStrategy = false;
        $scope.showStrategyPlayList = false;
        $scope.showStrategyResult = true;
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
            rows:$scope.strategy.rows,
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

        $scope.searchType = $scope.strategy.type;
         $scope.formTitle = "Results of strategy"

      };

    $scope.modify = function(tableData){
        $scope.editingData[tableData.id] = true;
    };

    $scope.update = function(strategyPlay){
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
                });

                res.error(function(data, status, headers, config) {
                    alert( "failure message: " + JSON.stringify({data: data}));
                });
    };

    $scope.showDivPlayer = function(playerList){
        if (playerList){
            $scope.showNewForm = false;
            $scope.showPlayerList = true;
        }else{
            $scope.formTitle = "Add New Player";
            $scope.id = null;

            $scope.fullName = "";
            $scope.type = null;


            $scope.player = { playList:[]};
            //$scope.playList = null;
            $scope.player_playList = [];

            $scope.showNewForm = true;
            $scope.showPlayerList = false;

            $scope.playList = null;
        }
    }

      $scope.changePlayerType = function(){
            $scope.searchType= $scope.type;
            $scope.searchText="";
            $scope.searchPlays();
      }

//add/update player
    $scope.submitPlayer = function(){


       if($scope.id==null){

            var addPlayer ={

                fullName:$scope.fullName,
                type:$scope.type,
                 playList:$scope.player.playList
            };
        }else{
          var updPlayer ={
                        id:$scope.player.id,
                        fullName:$scope.fullName,
                        type:$scope.type,
                        playList:$scope.player.playList
                    };
        }

        var res;


        if ($scope.id == null){

            res = $http.post($scope.urlServicePlayer, JSON.stringify(addPlayer), {
                headers: { 'Content-Type': 'application/json'}
            });
        }else{

            res = $http.put($scope.urlServicePlayer+"/"+$scope.id, JSON.stringify(updPlayer), {
                headers: { 'Content-Type': 'application/json'}});
        }

        res.success(function(data, status, headers, config) {
            $scope.message = data;
            $scope.getPlayers();
            $scope.showDivPlayer(true);

        });
        res.error(function(data, status, headers, config) {
            alert( "failure message: " + + JSON.stringify({data: data}));
        });

    }

    $scope.deletePlayer = function() {
        res = $http.delete($scope.urlServicePlayer+"/"+$scope.id);
        res.success(function(data, status, headers, config) {
            $scope.getPlayers();
            $scope.showDivPlayer(true);

        });
        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });
    }


    $scope.getPlayers = function(){

        $http.get($scope.urlServicePlayer).
        then(function(response) {
            $scope.playerList = response.data;
        });
        $scope.searchText = "";
    }

    $scope.getPlayerById = function(id){
        $http.get($scope.urlServicePlayer+"/"+id).
        then(function(response) {
            $scope.player = response.data;
            $scope.showDetailPlayer();
        });
        $scope.searchText = "";
    }

    $scope.showDetailPlayer = function(){
        //$scope.showDivPlayer(false);
        $scope.showNewForm = true;
        $scope.showPlayerList = false;
        $scope.formTitle = "Update Player"

        $scope.id = $scope.player.id;
        $scope.fullName = $scope.player.fullName;
        $scope.type = $scope.player.type;

        $scope.searchType= $scope.player.type;
        $scope.searchText="";
        $scope.searchPlays();
//https://vitalets.github.io/checklist-model/ helpful URL TO CHECK BOX
//https://material.angularjs.org/latest/demo/checkbox
    }

    $scope.checkPlaySelected = function (play, playList){
        var i=0, len=playList.length;
        for (; i<len; i++) {
          if (playList[i].code == play.code) {
            return true;
          }
        }
        return false;
    }

    $scope.togglePlay = function (play, playList) {
        var idx = -1;
        var i=0, len=playList.length;

        for (; i<len; i++) {

          if ( playList[i].code == play.code) {
            idx = i;

            break
          }
        }


        if (idx > -1) {
          playList.splice(idx, 1);
        }else {
          playList.push(play);
        }



    };

    $scope.checkSelected = function (item, list){
        return list.indexOf(item) > -1;
    }

    $scope.toggle = function (item, list) {
        var idx = list.indexOf(item);

        if (idx > -1) {
          list.splice(idx, 1);
        }
        else {
          list.push(item);
        }
    };

    //Search Players
    $scope.searchPlayers = function (){
        $scope.playerList = null;
        var params = "&"+$scope.searchText;
        if($scope.searchType!=null){
            params = $scope.searchType+"&"+$scope.searchText;
        }

        $http.get($scope.urlServicePlayer+"/players/"+params).
        then(function(response) {
            $scope.playerList = response.data;

        });
    }

});





