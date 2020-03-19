//抽取服务
app
		.service(
				"loginService",
				function($http) {
					// 获取用户登录名
					this.loadLoginName = function() {
						return $http.get("../login/loadLoginName");
					};
					this.checkLogin = function() {
						var _ticket = $.cookie("TT_TOKEN");
						if (!_ticket) {
							return;
						}
					     $http .jsonp("http://localhost:8084/user/token?callback=JSON_CALLBACK&_ticket" +_ticket ) .success(function(data) { 
								if (data.status == 200) {
									var username = data.data.username;
									var html = username
											+ "，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
									$("#loginbar").html(html);
								}
					    	});
//						$
//								.ajax({
//									url : "http://localhost:8088/user/token/"
//											+ _ticket,
//									dataType : "jsonp",
//									type : "GET",
//									success : function(data) {
//										if (data.status == 200) {
//											var username = data.data.username;
//											var html = username
//													+ "，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
//											$("#loginbar").html(html);
//										}
//									}
//								});
					}
				});
