<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
	</head>
	  <!-- 顶部导航栏开始 -->
	  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
	    <ul class="navbar-nav">
	      <li class="nav-item">
	        <a class="nav-link" data-widget="pushmenu" href="#" role="button">
	          <i class="fas fa-bars"></i>
	        </a>
	      </li>
	      <li class="nav-item d-none d-sm-inline-block">
	        <a href="#" class="nav-link" id="childMenuName"></a>
	      </li>
	    </ul>
	    <ul class="navbar-nav ml-auto">
	      <!-- 显示生产任务编号 -->
	      <li class="nav-item dropdown">
	        <span class="nav-link" href="#" id="showProductionTaskNumber">
	         	 ${Session["loginUser"].productionTaskNumber ? default("未选择生产任务")}
	        </span>
	      </li>
	      <!-- 登陆按钮 -->
	      <li class="dropdown user user-menu open" style="top: 7px;">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
              <span class="hidden-xs">${Session["loginUser"].userName ? default("尚未登陆")}</span>
            </a>
            <ul id="logout2" class="" style="display:none;position: absolute;list-style: none;
						    width: 100px;
						    margin-left: -53px;">
              <!-- Menu Footer   dropdown-menu-->
              <li class="">
                  <a href="#" class="btn" id="logout">退出</a>
              </li>
            </ul>
           </li>
	    </ul>
	  </nav>
	  <!-- 顶部导航栏结束 -->
<script>
$("#logout").click(function(){
	window.location.href="${request.contextPath}/loginOut";	
});

$(document).click(function(){
		 $('#logout2').hide();
	 });
$(".user-menu").click(function(event){
		 event.stopPropagation();
		 if($("#logout2").is(':visible')){
			 $('#logout2').hide();
		    }else{
		    	$('#logout2').show();
		    }
		 });
</script>	  
</html>

