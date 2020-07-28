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
<body class="container-fiuled" style="background-color: aliceblue;background-repeat:repeat-x;background-size:cover;" 
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
	    
	    <div class="row text-center">
	    	<span class="col-xs-4 " style="font-size: xx-large;top: 240px;">
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="changeCode">变更代码</span>
	    	</span>
	    	<span class="col-xs-4 " style="font-size: xx-large;top: 240px;">
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="knowleage">知识库</span>
	    	</span>
	    	<span class="col-xs-4 " style="font-size: xx-large;top: 240px;">
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="coverageReport">覆盖率报告</span>
	    	</span>
	    </div>
    </div>
<script>
var currentProductionTaskNumber;
getPuductionTaskNumber(); // 获取已有的生产任务编号
function getPuductionTaskNumber() {
	var loginUser=$("#loginUser option:selected").text();
	if(loginUser == "未登录"){
		alert("请先登陆");
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

// 新增生产任务编号
function gradeChangeForTask() {
    var objS = document.getElementById("productionTaskNumber");
    var grade = objS.options[objS.selectedIndex].value;
    if(grade == "addProductionTaskNumber"){
    	window.location.href="${request.contextPath}/loginOut";
    }else{
    	// 指定生产任务编号，区分后台数据
    	$.post('/selectProductionTaskNumber?productionTaskNumber='+grade,
    			function(json){
    				if(json.success == false){
    					alert(json.msg);
    				}else{
    					currentProductionTaskNumber = grade;
    				}
    	});
    }
}
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
$("#knowleage").click(function () {
	if(null == currentProductionTaskNumber || "" == currentProductionTaskNumber || "0" == currentProductionTaskNumber){
		alert("请先选择生产任务编号");
		return;
	}
	window.location.href="${request.contextPath}/testingExample/knowledgeBase";
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