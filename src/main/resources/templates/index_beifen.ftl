<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>精准测试</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/AdminLTE.min.css"/>
    <link rel="stylesheet" href="../css/dashboard.css"/>
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
<body>
	<!-- background="../img/haibin.jpg" class="" style="background-color: aliceblue;background-repeat:repeat-x;" -->
	
	<nav class="navbar navbar-inverse navbar-fixed-top" style="background-color: white;">
      <div class="container-fluid">
        <div class="navbar-header">

          <a class="navbar-brand" href="https://v3.bootcss.com/examples/dashboard/#">精准测试</a>
          <span class="btn glyphicon glyphicon-align-justify" aria-hidden="true"></span>
        </div>
        <div id="navbar" class="navbar-collapse collapse" >
          <ul class="nav navbar-nav navbar-right">
            <li><a href="https://v3.bootcss.com/examples/dashboard/#">Dashboard</a></li>
            <li><a href="https://v3.bootcss.com/examples/dashboard/#">Settings</a></li>
            <li><a href="https://v3.bootcss.com/examples/dashboard/#">Profile</a></li>
            <li>
            <select id="loginUser" onchange="gradeChange()" >
		    	<option><#if Session["loginUser"]?exists>${Session["loginUser"].userName}<#else>未登录</#if></option>
		    	<option value="loginOut">退出</option>
		    </select>
            </li>
          </ul>
        </div>
      </div>
    </nav>
	
	
	<div class="container" > <!-- class="container-fluid" -->
      <div class="row" >
        <div class="col-sm-3 col-md-2 sidebar" id="class_conton"  style="display:black;background-color: #39566A;">
          <ul class="nav sidebar-menu" >
            <li class="active"><a href="${request.contextPath}/index">首页 <span 
            	class="sr-only">(current)</span></a></li>
            <li><a href="#">生产任务</a></li>
            <li><a href="#">变更代码</a></li>
            <li><a href="#">创建知识库</a></li>
            <li><a href="#">查看知识库</a></li>
            <li><a href="#">覆盖率报告</a></li>
            <li><a onclick="show('sub_menu_1')">测试</a>
              <ul id="sub_menu_1" style="display: none;">
                <li><a href=" ">测试1</a></li>
                <li><a href="#">测试2</a></li>
              </ul>
            </li>
            <li><a href="#">权限配置</a></li>
          </ul>
          
        </div>
    <div id="main" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	    
	    <a id="loading" href="${request.contextPath}/permission/index" 
	    style="color:black; position:absolute;cursor: pointer;text-decoration:underline;
	    		    right: 420px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;" >权限配置</a>
	    
	    <div id="productionTaskNumber" style="color:black; width: 200px;
	    position:absolute;right: 150px;top: 10px;z-index:9999;font-size: 20px;font-family: 宋体;">
	    	未选择生产任务</div>
	    
	    
	    <div class="row text-center" style="font-size: xx-large; margin-top: 15%;">
	    	<div class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="productionTask">生产任务</span>
	    	</div>
	    	<span class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="changeCode">变更代码</span>
	    	</span>
	    	<span class="col-xs-1 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="buildKnowleage">创建知识库</span>
	    	</span>
	    </div>
	    <div class="row text-center" style="font-size: xx-large;margin-top: 11%;">
	    	<span class="col-xs-4 " >
	    		<span style="margin-left: ;font-size: xx-large;font-family: 仿宋;" 
	    			class="btn btn-success" id="knowleageDetail">查看知识库</span>
	    	</span>
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
	    
	    <div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel4">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel4">选择生产任务编号：</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="form-group">
	                        <div id="ptnumber"></div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                	<button type="button" id="btn_save_testserverip" class="btn btn-primary" data-dismiss="modal">
	                		<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>确认</button>
	                    <button type="button" class="btn btn-default" data-dismiss="modal">
	                    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	                </div>
	            </div>
	        </div>
	    </div>
	    
    </div>
     </div>
    </div> 
    
<script>
function show(str) {
    var sub_menu = document.getElementById(str);
    var dis_v = sub_menu.style.display;
	console.log("....",dis_v)
    if (dis_v === "block")
        sub_menu.style.display = "none";
    else
        sub_menu.style.display = "block";
}
/* $("#guanbi").click(function(){
	$(".sidebar").hide();
}); */


/**左侧菜单栏隐藏*/
 $(".btn").click(function(){
	
	var content = document.getElementById("class_conton");
	var cla = document.getElementById("main")
	var ccc = $(".main")
	 var dis_content = content.style.display;
	 if (dis_content === "block" || dis_content === ""){
		 content.style.display="none"
		ccc.removeClass("col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main")
	 }else{
		 content.style.display="block"
		 cla.className = "col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	 }
		
}) 
var currentProductionTaskNumber;
getPuductionTaskNumber(); // 获取、展示已有的生产任务编号
function getPuductionTaskNumber() {
	var loginUser=$("#loginUser option:selected").text();
	if(loginUser == "未登录"){
		//alert("请先登陆");
		window.location.href="${request.contextPath}/";
		return;
	}	
	$.post('/getPuductionTaskNumber',
			function(json){
				var cptn = json.currentProductionTaskNumber;
				if(cptn == ""){
					$("#productionTaskNumber").text("未选择生产任务");
				}else{
					currentProductionTaskNumber = cptn;
					$("#productionTaskNumber").text(cptn);
				}
		});
}

$("#productionTask").click(function(){
    window.location.href="${request.contextPath}/proTaskNumberIndex";
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