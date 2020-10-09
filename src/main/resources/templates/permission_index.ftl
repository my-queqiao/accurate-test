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
          <div class="col-sm-12">
          
	    <div class="row text-center">
	    		<div><button style="background-color: #5cb85c;color: white;" onclick="guanlian()">确认关联</button></div>
	    		<div style="color:black; cursor: pointer;text-decoration:underline;margin-left: 80%;
	    		    z-index:9999;font-size: 20px;font-family: 宋体;">
	    		    <a href="${request.contextPath}/permission/roleIndex">角色配置</a>
	    		</div>
	    </div>
	    
      </div>
    </div>
  </div>
  </div>
  <section class="content">
   <div class="container-fluid">
   <div class="row text-center">
   				<div class="col-sm-3">
		        	<table id="tb_getAllUsers"></table>
		        </div>
		        <div class="col-sm-1"></div>
		        <div class="col-sm-7">
	    			<table id="tb_getAllRoles"></table>
	    		</div>
   </div>
   </div>
  </section>
</div>
<!-- 右侧页面结束 -->
</div>	    
    <script>
    $("#childMenuName").html("权限配置");
    $(function () {
    	var tfte = TableInit_forGetAllRoles();
    	tfte.Init();
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        /* var oButtonInit = new ButtonInit();
        oButtonInit.Init(); */
    });


    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_getAllUsers').bootstrapTable({
                url: '/permission/getAllUsers',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                dataType: 'json',  
                toolbar: '#toolbar',                //工具按钮用哪个容器
                theadClasses:'.thead-light',
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                showPaginationSwitch: false,        //是否显示分页数
                sortable: false,                     //是否启用排序
                sortName: "title",                     //是否启用排序
                sortOrder: "desc",                   //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                queryParamsType: '',                //如果要在oTableInit.queryParams方法获取pageNumber和pageSize的值，需要将此值设置为空字符串（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                minimumCountColumns: 2,             //最少允许的列数
                singleSelect: true,                 //是否单选模式
                height: $(window).height()-150,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                showColumns: false,                  //是否显示所有的列
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showRefresh: false,                  //是否显示刷新按钮
                clickToSelect: false,                //是否启用点击选中行
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                onCheck:function(row){ //点击每一个单选框时触发的操作
                  //alert(row.id);
                  onCheck(row);
                },
                onUncheck:function(row){ //取消每一个单选框时对应的操作
                	//alert(row.id);
                	onUncheck(row);
                },
                columns: [{
                    checkbox: true,
                }, 
                {
                    field: 'id',
                    title: '主键id'
                },
                {
                    field: 'userName',
                    title: '用户名', //align: 'center'
                	//events: operateEvents1
                	//formatter: operateFormatter1
                } 
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            // 特别说明：
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    　　　　　// 如果queryParamsType=limit,params包含{limit, offset, search, sort, order}
    　　　　　// 如果queryParamsType!=limit,params包含{pageSize, pageNumber, searchText, sortName, sortOrder}
            var temp = {
                pageSize: params.pageSize,   //页面大小
                pageNumber: params.pageNumber,  //页码
                departmentname: $("#txt_search_departmentname").val(),
            };
            return temp;
        };
        return oTableInit;
    };
    
    function onCheck(row){
    	var opt = {
		        url: "/permission/getRolesByUserId?userId="+row.id,
		        silent: true,
		        query:{
		        }
		    };
    	$("#tb_getAllRoles").bootstrapTable('refresh',opt);//带参数刷新
    }
    function onUncheck(row){
    	$("#tb_getAllRoles input:checkbox").attr("checked", false); // 全不选
    }
    
    function getData_before(){
    	$("#git_url").attr("disabled", "disabled");
    	$("#btn_query").attr("disabled", "disabled");
    	$("#loading").show();
    }
	function getData_after(){
		$("#git_url").attr("disabled", false);
		$("#btn_query").attr("disabled", false);
		$("#loading").hide();
    }
    
    var TableInit_forGetAllRoles = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_getAllRoles').bootstrapTable({
                url: '/permission/getAllRoles',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                dataType: 'json',  
                toolbar: '#toolbar',                //工具按钮用哪个容器
                theadClasses:'.thead-light',
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                showPaginationSwitch: false,        //是否显示分页数
                sortable: false,                     //是否启用排序
                sortName: "title",                     //是否启用排序
                sortOrder: "desc",                   //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                queryParamsType: '',                //如果要在oTableInit.queryParams方法获取pageNumber和pageSize的值，需要将此值设置为空字符串（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 10000,                       //每页的记录行数（*）
                pageList: [10000],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                minimumCountColumns: 2,             //最少允许的列数
                singleSelect: false,                 //是否单选模式
                height: $(window).height()-150,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                showColumns: false,                  //是否显示所有的列
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showRefresh: false,                  //是否显示刷新按钮
                clickToSelect: false,                //是否启用点击选中行
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                columns: [{
                    checkbox: true,
                    formatter: function (value, row, index) {
                    	if(row.checked === 1){
	                    	return {
	                    		checked : true,
	                    	}
	                    }
                    }
                }, 
                {
                    field: 'id',
                    title: '主键id'
                },
                {
                    field: 'roleName',
                    title: '角色名称', //align: 'center'
                	//events: operateEvents1
                	//formatter: operateFormatter1
                },   
                {
                    field: 'description',
                    title: '描述', //align: 'center'
                	//events: operateEvents1
                	//formatter: operateFormatter1
                },   
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            // 特别说明：
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    　　　　　// 如果queryParamsType=limit,params包含{limit, offset, search, sort, order}
    　　　　　// 如果queryParamsType!=limit,params包含{pageSize, pageNumber, searchText, sortName, sortOrder}
            var temp = {   
                pageSize: params.pageSize,   //页面大小
                pageNumber: params.pageNumber,  //页码
                departmentname: $("#txt_search_departmentname").val(),
                statu: $("#txt_search_statu").val()
            };
            return temp;
        };
        return oTableInit;
    };
    function guanlian(){
    	var arrselections1 = $("#tb_getAllUsers").bootstrapTable('getSelections'); // 测试用例列表
    	var arrselections2 = $("#tb_getAllRoles").bootstrapTable('getSelections'); // 方法链
        if (arrselections1.length > 1) {
            alert('只能选择一行进行编辑');
            return;
        }
        if (arrselections1.length <= 0 || arrselections2.length <= 0) {
            alert('请选择有效数据');
            return;
        }
        var ms = [];
        for(var i=0;i<arrselections2.length;i++){
        	ms.push(arrselections2[i].id);
        }
        
        var userId = arrselections1[0].id;
        //var methodChainOriginalIds = JSON.stringify(ms); // 大概是tomcat版本的原因，后台接收数组时报错
        var roleIds	=	"";
        for(var i=0;i<ms.length;i++){
        	roleIds+=ms[i];
        	if(i != ms.length-1){
        		roleIds+=",";
        	}
        }
        $.post('/permission/userLinkRoles?userId='+userId
        		+'&roleIds='+roleIds,
				function(json){
        			if(json.success == true){
        				alert("关联成功");
        				//window.location.href='/testingExample/knowledgeBase';
        			}else{
        				alert("关联失败");
        			}
		});
    }
    </script>
    
    
</body>
</html>