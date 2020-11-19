<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>精准测试</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-table/bootstrap-table.css"/>
    <link rel="stylesheet" href="../css/font-awesome.min.css"/>
    <link rel="stylesheet" href="../css/ionicons.min.css"/>
    <link rel="stylesheet" href="../css/datatables/dataTables.bootstrap.css"/>
    
    <script src="../js/jquery3.2.1.min.js"></script>
    <script src="../js/bootstrap3.3.7.min.js"></script>
    <link rel="stylesheet" href="../css/bootstrap-table1.15.3.min.css">
    <script src="../js/bootstrap-table1.15.3.min.js"></script>
    <script src="../js/bootstrap-table-zh-CN1.15.3.min.js"></script>
    <script src=""></script>
    <!--@*4、页面Js文件的引用*@
    <script src="~/Scripts/table/Home/Index.js"></script>
    -->
</head>
<body class="container-fiuled" style="background-color: aliceblue;background-repeat:repeat-x;" 
	background="../img/haibin.jpg">
    <div class="panel-body" style="padding-bottom:0px;">
	    <div class="row text-center">
	    	<span class="col-xs-12 " style="font-size: xx-large;">
	    		<span style="margin-left: ;">精准测试项目</span>
	    	</span>
	    </div>
	    <div id="loading" style="color:blue; display:none; position:absolute;
	    		top:120px; left:12em;z-index:9999;font-size: 38px;font-family: 宋体;" >正在请求数据，请稍等...</div>
	    
	    <form class="row text-center" style="margin-left:0px;margin-top:200px;" >
	         <div class="form-group">
	             <label for="user" stype="display:inline;">用户名：</label>
	             <input type="text" class="form-control" id="user" 
	             		style="display:inline;width:200px;"autocomplete="on" />
	         </div>
	         <div class="form-group">
	             <label for="password" style="display:inline;">&nbsp&nbsp&nbsp&nbsp密码：</label>
	             <input type="password" class="form-control" id="password" style="display:inline;width:200px;"autocomplete="off" />
	         </div>
	         <button type="button" id="login" class="btn btn-primary">登录</button>
	         <button type="button" id="register" class="btn btn-primary">注册</button>
         </form>
         
         <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel3">注册</h4>
	                </div>
	                <div class="modal-body text-center">
	                    <div class="form-group">
	                    	<label for="user2" stype="display:inline;">用户名：</label>
	                        <input id="user2" placeholder="" />
	                    </div>
	                    <div class="form-group">
	                    	<label for="password2" stype="display:inline;">&nbsp&nbsp&nbsp&nbsp密码：</label>
	                        <input id="password2" type="password" placeholder="" />
	                    </div>
	                </div>
	                <div class="modal-footer">
	                	<button type="button" id="btn_save_register" class="btn btn-primary" data-dismiss="modal">
	                		<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>确认</button>
	                    <button type="button" class="btn btn-default" data-dismiss="modal">
	                    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	                </div>
	            </div>
	        </div>
	    </div>
    </div>
<script>
    $("#login").click(function () {
    	var user = $("#user").val();
    	var password = $("#password").val();
    	$.post('/login?user='+user+'&password='+password,
				function(json){
    		if(json.success == true){
				window.location.href="${request.contextPath}/puductionTask";   			
    		}else{
    			alert(json.msg);
    		}
		});
    });
    $("#register").click(function () {
    	/* var production_task_number = $("#production_task_number").val();
    	if($.isEmptyObject(production_task_number) == true || $.trim(production_task_number) == ""){
    		alert("编号不能为空");
    		return;
    	}
    	$.post('/register?productionTaskNumber='+production_task_number,
				function(json){
    		alert(json.msg);
		}); */
    	$("#myModalLabel3").text("注册");
        $('#myModal3').modal();
    });
    $("#btn_save_register").click(function(){
    	var user = $("#user2").val();
    	var password = $("#password2").val();
    	if(null == user  || "" == user) {
    		alert("用户名不能为空");
    		return;
    	}
    	if(null == password  || "" == password) {
    		alert("密码不能为空");
    		return;
    	}
		$.post('/register?user='+user+'&password='+password,
				function(json){
					//$("#loading").hide();
					alert(json.msg);
					if(json.success == true){
					}else{
					}
			});
    });
</script>
    
    
</body>
</html>