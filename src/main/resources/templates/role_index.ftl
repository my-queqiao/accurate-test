<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>精准测试-知识库创建、查看</title>
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
<body class="container-fiuled" style="background-color: aliceblue;">
    <div class="panel-body" style="padding-bottom:0px;">
	    <div class="row text-center">
	    	<!--  <span class="col-xs-8 " style="font-size: xx-large;position:fixed;top: 85px;">
	    		<span style="margin-left: 45%;">知识库创建与查看</span>
	    	</span>-->
	    </div>
	    <div class="row text-center">
	    	<div class="col-xs-6" style="position:fixed;top: 15px;">
	    		<button style="background-color: #5cb85c;color: white;" onclick="guanlian()">确认关联</button>
		        <table id="tb_getAllRoles"></table>
	    	</div>
	    	<div class="col-xs-6">
	    	</div>
	    	<div class="col-xs-6" style="top: 25px;">
	    		<table id="tb_getAllPermissions"></table>
	    	</div>
	    </div>
    </div>
    <script>
    $(function () {
    	var tfte = TableInit_forgetAllPermissions();
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
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                minimumCountColumns: 2,             //最少允许的列数
                singleSelect: true,                 //是否单选模式
                height: $(window).height()-100,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
            };
            return temp;
        };
        return oTableInit;
    };
    
    function onCheck(row){
    	var opt = {
		        url: "/permission/getPermissionsByRoleId?roleId="+row.id,
		        silent: true,
		        query:{
		        }
		    };
    	$("#tb_getAllPermissions").bootstrapTable('refresh',opt);//带参数刷新
    }
    function onUncheck(row){
    	$("#tb_getAllPermissions input:checkbox").attr("checked", false); // 全不选
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
    
    var TableInit_forgetAllPermissions = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_getAllPermissions').bootstrapTable({
                url: '/permission/getAllPermissions',         //请求后台的URL（*）
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
                //height: $(window).height()-200,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                showColumns: false,                  //是否显示所有的列
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showRefresh: false,                  //是否显示刷新按钮
                clickToSelect: true,                //是否启用点击选中行
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
                    field: 'rankDesc',
                    title: '权限描述', //align: 'center'
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
    	var arrselections1 = $("#tb_getAllRoles").bootstrapTable('getSelections'); // 
    	var arrselections2 = $("#tb_getAllPermissions").bootstrapTable('getSelections'); // 
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
        
        var roleId = arrselections1[0].id;
        //var methodChainOriginalIds = JSON.stringify(ms); // 大概是tomcat版本的原因，后台接收数组时报错
        var permissionIds	=	"";
        for(var i=0;i<ms.length;i++){
        	permissionIds+=ms[i];
        	if(i != ms.length-1){
        		permissionIds+=",";
        	}
        }
        $.post('/permission/roleLinkPermissions?roleId='+roleId
        		+'&permissionIds='+permissionIds,
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