//控制层 
app.controller('sellerController', function($scope, $controller, $location,
		sellerService) {

	$controller('baseController', {
		$scope : $scope
	});// 继承

	// // 读取列表数据绑定到表单中
	// $scope.findAll = function() {
	// sellerService.findAll().success(function(response) {
	// $scope.list = response;
	// });
	// }
	//
	// // 分页
	// $scope.findPage = function(page, rows) {
	// sellerService.findPage(page, rows).success(function(response) {
	// $scope.list = response.rows;
	// $scope.paginationConf.totalItems = response.total;// 更新总记录数
	// });
	// }

	// 查询实体
	$scope.findOne = function(id) {
		sellerService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	}

	// 保存
	$scope.save = function() {
		var obj = null;
		if ($scope.entity.sellerId == null) {
			// 商家注册
			sellerService.add($scope.entity).success(function(response) {
				if (response.status == 200) {
					// 跳转到商品登录页面
					location.href = "shoplogin.html";
				} else {
					alert(response.msg);
				}
			});
		} else {
			// 商家修改
			sellerService.update($scope.entity).success(function(response) {
				if (response.status == 200) {
					alert(response.msg);
					
				} else {
					alert(response.msg);
				}
			});
		}

	}

	// 批量删除
	$scope.dele = function() {
		// 获取选中的复选框
		sellerService.dele($scope.selectIds).success(function(response) {
			if (response.status == 200) {
				$scope.reloadList();// 刷新列表
				$scope.selectIds = [];
			}
		});
	}

	$scope.searchEntity = {};// 定义搜索对象

	// 搜索
	$scope.search = function(page, rows) {
		sellerService.search(page, rows, $scope.searchEntity).success(
				function(response) {
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;// 更新总记录数
				});
	};
	$scope.updateSellerStatus = function(id, status) {
		sellerService.updateSellerStatus(id, status).success(function(response) {
			if (response.status == 200) {
				alert(response.msg);
				// 刷新
				$scope.reloadList();
			} else {
				alert(response.msg);
			}
		});
	}
	//商家状态
	$scope.statusArr = ['未审核','审核通过','审核未通过','关闭商家'];
});
