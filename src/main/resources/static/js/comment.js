let host = "http://localhost:8080/home";
var app = angular.module("MSStore", []);
app.controller("comment", function($scope,$http) {
	$scope.pid = 0;
	$scope.comments = [];
	$scope.loadComment = function(id){
		$scope.pid = id;
		$http.get(`${host}/${id}/product-comment`).then(function(response) {
    		$scope.comments = response.data;
    		$scope.comments.forEach(function(cmt){
				const d = new Date(cmt.ngayTao);
				cmt.ngayTao = new Date(d.getTime() - d.getTimezoneOffset() * 60000).toISOString().substring(0, 19).replace("T"," ");
			})
    		$scope.comments.sort(function(a, b){return new Date(b.ngayTao) - new Date(a.ngayTao)});

    		console.log($scope.comments);
    		
  		});
	}
	
	$scope.comment = async function(){
		if($scope.cmt == undefined) return;
		var date = new Date();		
		var data = ['HungTH',$scope.pid,$scope.cmt];
  		await  $.ajax({
            url: `${host}/product-comment`,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success:function(res){
				$scope.cmt = undefined;
				const d = new Date(res.ngayTao);
				res.ngayTao = new Date(d.getTime() - d.getTimezoneOffset() * 60000).toISOString().substring(0, 19).replace("T"," ");
    			$scope.comments.push(res);
    			$scope.comments.sort(function(a, b){return new Date(b.ngayTao) - new Date(a.ngayTao)});
    			$scope.$apply();
                console.log('Them TASK OK')
            },
            error:function(resp){
                console.log(positions);
            }
        });
	}
	
  
});