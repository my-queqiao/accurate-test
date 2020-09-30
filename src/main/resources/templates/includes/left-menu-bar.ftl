<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
	</head>
	  <!-- 左侧导航栏开始 -->
	 <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <a href="#" class="brand-link">
      <img src="../img/C.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
      style="opacity: .8">
      <span class="brand-text font-weight-light ">精准测试</span>
    </a>

    <div class="sidebar">
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column sidebar-menu" data-widget="treeview" role="menu" data-accordion="false">
        	<li class="nav-item">
	            <a href="${request.contextPath}/index" class="nav-link">
	              <i class="nav-icon fas fa-circle"></i>
	              <p>首页</p>
	            </a>
	         </li>
        	<li class="nav-item">
	            <a href="${request.contextPath}/puductionTask" class="nav-link">
	              <i class="nav-icon fas fa-briefcase"></i>
	              <p>生产任务</p>
	            </a>
	        </li>
        	<li class="nav-item">
	            <a href="${request.contextPath}/changeCode/index" class="nav-link">
	              <i class="nav-icon fas fa-edit"></i>
	              <p>变更代码</p>
	            </a>
	        </li>
        	<li class="nav-item">
	            <a href="${request.contextPath}/testingExample/buildKnowleage" class="nav-link">
	              <i class="nav-icon fas fa-gavel"></i>
	              <p>创建知识库</p>
	            </a>
	        </li>
        	<li class="nav-item">
	            <a href="${request.contextPath}/index2" class="nav-link">
	              <i class="nav-icon fas fa-search"></i>
	              <p>查看知识库</p>
	            </a>
	        </li>
        	<li class="nav-item">
	            <a href="${request.contextPath}/index2" class="nav-link">
	              <i class="nav-icon fas fa-file"></i>
	              <p>覆盖率报告</p>
	            </a>
	        </li>
        	<li class="nav-item">
	            <a href="${request.contextPath}/index2" class="nav-link">
	              <i class="nav-icon fas fa-key"></i>
	              <p>权限设置</p>
	            </a>
	        </li>
	         
          <!-- <li class="nav-item has-treeview menu-close">
            <a href="#" class="nav-link active">
              <i class="nav-icon fas fa-th"></i>
              <p>
                	一级菜单
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="" class="nav-link active">
                  <i class="far fa-circle nav-icon"></i>
                  <p>一级菜单儿子1</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="#" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>一级菜单儿子2</p>
                </a>
              </li>
            </ul>
          </li> -->
        </ul>
      </nav>
    </div>
  </aside>
	  <!-- 左侧导航栏结束 -->
<script>
$(function(){
        console.log("12212121232423");
    $('.sidebar-menu li:not(.treeview) > a').on('click', function(){
        console.log(this.href);
        var $parent = $(this).parent().addClass('active');
        $parent.siblings('.treeview.active').find('> a').trigger('click');
        $parent.siblings().removeClass('active').find('li').removeClass('active');
    });
    $('.sidebar-menu a').each(function(){
        if(this.href === window.location.href){
            $(this).parent().addClass('active')
                    .closest('.treeview-menu').addClass('.menu-open')
                    .closest('.treeview').addClass('active');
        }
    });


});
</script>	  
</html>