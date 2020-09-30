<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<title>精准测试</title>
  		<#include "includes/head.ftl">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
  <#include "includes/head-menu-bar.ftl">
  <#include "includes/left-menu-bar.ftl">
<!--右侧内容开始-->
  <div class="content-wrapper">
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
          
          
          </div>
        </div>
      </div>
    </div>
    <section class="content">
	    <div class="container-fluid">
	    	首页内容
	    </div>
    </section>
  </div>
  <!-- 右侧页面结束 -->
</div>
	
    
<script>
$("#childMenuName").html("首页");
/**左侧菜单栏隐藏*/
 $(".btn").click(function(){
	
	var content = document.getElementById("class_conton");
	var cla = document.getElementById("main")
	var ccc = $(".main")
	 var dis_content = content.style.display;
	 if (dis_content === "block" || dis_content === ""){
		 content.style.display="none"
		ccc.removeClass("col-sm-12 col-sm-offset-3 col-md-10 col-md-offset-2 main")
	 }else{
		 content.style.display="block"
		 cla.className = "col-sm-12 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	 }
});
</script>
</body>
</html>