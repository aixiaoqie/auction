app.controller('itemController' ,function($scope,$http){
	
	//购买数量的增减
	$scope.addNum=function(num){
		
		$scope.num=$scope.num+num;
		if($scope.num<=0){
			$scope.num=1;
		}		
	}
	
	$scope.specificationItems={};//记录用户选择的规格值
	
	//用户选择规格值
	$scope.selectSpecification=function(name,value){
		$scope.specificationItems[name]=value;
		searchSku();
	}
	//判断规格和选项是否被选中
	$scope.isSelected=function(name,value){
		if( $scope.specificationItems[name]==value  ) {
			return true;
		}else{
			return false;
		}		
	}
	
	//$scope.sku={};//当前用户选择的SKU
	//查询SKU信息
	searchSku=function(){
		//循环SKU列表-匹配spec 
		for(var i=0;i< skuList.length;i++ ){
			//将列表的中spec 与用户选择的spec进行比较
			if( matchObject(skuList[i].spec ,$scope.specificationItems) ){
				$scope.sku=skuList[i];
			}
		}		
		
	}
	
	//匹配对象
	matchObject=function(map1,map2){
		
		for( var k in map1 ){			
			if( map1[k]!= map2[k] ){
				return false;
			}			
		}
		
		for( var k in map2 ){			
			if( map2[k]!= map1[k] ){
				return false;
			}			
		}
		
		return true;
	}
	
	//加载默认的sku
	$scope.loadDefaultSku=function(){
		
		for(var i=0;i<skuList.length;i++){
			if(skuList[i].isDefault=='1'){
				$scope.sku=skuList[i];
			}			
		}
		//如果没有默认的SKU,将第一个作为默认
		if($scope.sku==null){
			$scope.sku=skuList[0];
		}	

		$scope.specificationItems= $scope.sku.spec;
	}
	
	//添加商品到购物车
	$scope.addGoodsToCartList=function(){
		//这是一个跨域的地址
		var url='http://localhost:9105/cart/addGoodsToCartList.do?itemId='+$scope.sku.id+'&num='+$scope.num;
		$http.get(url,{'withCredentials':true}).success(
			function(response){
				if(response.success){
					location.href="http://localhost:9105/cart.html";
				}else{
					alert(response.message);
				}
			}
		);
		
	}
	
	
});