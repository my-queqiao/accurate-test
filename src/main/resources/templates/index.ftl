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
	    <div id="loading" style="color:black; position:absolute;
	    		    right: 200px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;" >权限配置</div>
	    
	    <select id="loginUser" onchange="gradeChange()"
	    style="color:black; position:absolute;right: 10px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;">
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
function gradeChange() {
    var objS = document.getElementById("loginUser");
    var grade = objS.options[objS.selectedIndex].value;
    if(grade == "loginOut"){
    	window.location.href="${request.contextPath}/loginOut";
    }
}

$("#changeCode").click(function () {
	window.location.href="${request.contextPath}/changeCode/index";
});
$("#knowleage").click(function () {
	window.location.href="${request.contextPath}/testingExample/knowledgeBase";
});
$("#coverageReport").click(function () {
	window.location.href="${request.contextPath}/coverageReport/index";
});
</script>
    
    
</body>
</html>