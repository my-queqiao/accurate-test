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
	    		<span style="margin-left: ;">精准测试首页</span>
	    	</span>
	    </div>
	    <a id="loading" href="${request.contextPath}/permission/index" 
	    style="color:black; position:absolute;cursor: pointer;text-decoration:underline;
	    		    right: 420px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;" >权限配置</a>
	    
	    <select id="productionTaskNumber" onchange="gradeChangeForTask()"
	    style="color:black; position:absolute;right: 150px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;">
	    	<option value="0">请选择生产任务编号</option>
	    	<option value="addProductionTaskNumber">新增生产任务编号</option>
	    </select>
	    <select id="loginUser" onchange="gradeChange()" style="color:black; position:absolute;width: 100px;
	    right: 10px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;">
	    	<option><#if Session["loginUser"]?exists>${Session["loginUser"].userName}<#else>未登录</#if></option>
	    	<option value="loginOut">退出</option>
	    </select>
	    
	    <div class="row text-center" style="font-size: xx-large; margin-top: 15%;">
	    	<span class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="changeCode">变更代码</span>
	    	</span>
	    	<span class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="buildKnowleage">创建知识库</span>
	    	</span>
	    	<span class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="knowleageDetail">查看知识库</span>
	    	</span>
	    </div>
	    <div class="row text-center" style="font-size: xx-large;margin-top: 11%;">
	    	<span class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="coverageReport">覆盖率报告</span>
	    	</span>
	    </div>
	    <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel3">注册</h4>
	                </div>
	                <div class="modal-body text-center">
	                    <div class="form-group">
	                    	<label for="productionTaskNumber2" stype="display:inline;">编号：</label>
	                        <input id="productionTaskNumber2" placeholder="" />
	                    </div>
	                </div>
	                <div class="modal-footer">
	                	<button type="button" id="btn_save_productionTaskNumber" class="btn btn-primary" data-dismiss="modal">
	                		<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>确认</button>
	                    <button type="button" class="btn btn-default" data-dismiss="modal">
	                    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	                </div>
	            </div>
	        </div>
	    </div>
    </div>
<script>
var currentProductionTaskNumber;
getPuductionTaskNumber(); // 获取已有的生产任务编号
function getPuductionTaskNumber() {
	var loginUser=$("#loginUser option:selected").text();
	if(loginUser == "未登录"){
		//alert("请先登陆");
		window.location.href="${request.contextPath}/";
		return;
	}	
	$.post('/getPuductionTaskNumber',
			function(json){
				if(json.success == false){
					alert(json.msg);
				}else{
					var cptn = json.currentProductionTaskNumber;
					currentProductionTaskNumber = cptn;
				    //$("#productionTaskNumber").append(opt);
					$.each(json.list,function(index,item){
						var ptn = item.productionTaskNumber;
						if(null != cptn){
							if(cptn == ptn){
						        var opt=$("<option selected value="+ptn+">"+ptn+"</option>");
						        $("#productionTaskNumber").append(opt);
							}else{
						        var opt=$("<option value="+ptn+">"+ptn+"</option>");
						        $("#productionTaskNumber").append(opt);
							}
						}
					});
				}
	});
}

// 指定生产任务编号
function gradeChangeForTask() {
    var objS = document.getElementById("productionTaskNumber");
    var grade = objS.options[objS.selectedIndex].value;
    if(grade == "addProductionTaskNumber"){
    	$("#myModalLabel3").text("新增生产任务编号");
        $('#myModal3').modal();
    }else{
    	// 指定生产任务编号，区分后台数据
    	$.post('/selectProductionTaskNumber?productionTaskNumber='+grade,
    			function(json){
    				if(json.success == false){
    					alert(json.msg);
    				}else{
    					alert(json.msg);
    					currentProductionTaskNumber = grade;
    				}
    	});
    }
}

$("#btn_save_productionTaskNumber").click(function(){
	var productionTaskNumber = $("#productionTaskNumber2").val();
	if(null == productionTaskNumber  || "" == productionTaskNumber) {
		alert("不能为空");
		return;
	}
	$.post('/addProductionTaskNumber?productionTaskNumber='+productionTaskNumber,
			function(json){
				//$("#loading").hide();
				alert(json.msg);
				if(json.success == true){
				}else{
				}
		});
});
// 退出登陆
function gradeChange() {
    var objS = document.getElementById("loginUser");
    var grade = objS.options[objS.selectedIndex].value;
    if(grade == "loginOut"){
    	window.location.href="${request.contextPath}/loginOut";
    }
}
$("#changeCode").click(function () { // 刚登陆进入首页时为空，请选择的值为0
	if(null == currentProductionTaskNumber || "" == currentProductionTaskNumber || "0" == currentProductionTaskNumber){
		alert("请先选择生产任务编号");
		return;
	}
	window.location.href="${request.contextPath}/changeCode/index";
});
$("#buildKnowleage").click(function () {
	window.location.href="${request.contextPath}/testingExample/buildKnowleage";
});
$("#knowleageDetail").click(function () {
	window.location.href="${request.contextPath}/testingExample/knowleageDetail";
});
$("#coverageReport").click(function () {
	if(null == currentProductionTaskNumber || "" == currentProductionTaskNumber || "0" == currentProductionTaskNumber){
		alert("请先选择生产任务编号");
		return;
	}
	window.location.href="${request.contextPath}/coverageReport/index";
});
</script>
    
    
</body>
</html>