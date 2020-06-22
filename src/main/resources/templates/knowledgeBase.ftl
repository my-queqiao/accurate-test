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
<div id="loading" style="color:blue; display:none; position:absolute;
	    		top:22px; left:1em;z-index:9999;font-size: 25px;font-family: 宋体;" >上传成功</div>
    <div class="panel-body" style="padding-bottom:0px;">
	    <div class="row text-center">
	    	<!--  <span class="col-xs-8 " style="font-size: xx-large;position:fixed;top: 85px;">
	    		<span style="margin-left: 45%;">知识库创建与查看</span>
	    	</span>-->
	    </div>
	    <div class="row text-center">
	    	<div class="col-xs-3" style="position:fixed;top: 15px;">
	    		<button style="background-color: #5cb85c;color: white;" onclick="guanlian()">确认关联</button>
		        <table id="tb_testingExample"></table>
	    	</div>
	    	<div class="col-xs-3">
	    	</div>
	    	<div class="col-xs-9">
	    		<form style=""
	    			action="${request.contextPath}/testingExample/upload" method="post" enctype="multipart/form-data">
					<input type="file" name="file" style="float: left;" required/>
					<input type="submit" value="上传记录方法链的文件"	style="margin-left: -50%;"/>
				</form>
		        <table id="tb_methodChainOriginal"></table>
	    	</div>
	    </div>
    </div>
    <script>
    if(true == ${success}){
		alert("上传成功");
    }else if(false == ${success}){
		alert("上传失败");
    }
    $(function () {
    	/*方法链*/
    	var tfte = TableInit_forTestingExample();
    	tfte.Init();
    	/*测试用例列表*/
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });


    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_testingExample').bootstrapTable({
                url: '/testingExample/getAll',         //请求后台的URL（*）
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
                columns: [{
                    checkbox: true
                }, 
                {
                    field: 'id',
                    title: '主键id'
                },
                {
                    field: 'testingExampleName',
                    title: '测试用例名称', //align: 'center'
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
                statu: $("#txt_search_statu").val()
            };
            return temp;
        };
        return oTableInit;
    };
    /**function operateFormatter(value, row, index) {
    	return [
    	'<div id="btn_detail" type="button" class="RoleOfA btn-default bt-select">详情/div>',  
    	].join('');
    		'<a title="查看方法变更详情" onclick="method_body_details(\''+row.methodBody+'\')"'
    }*/

    var list = []; // 数组，存放bootstrap-table的行数据。
    var ids = []; // 存放行数据的id
    function methodNameFormatter(value, row, index) {
    	for(j = 0; j < list.length; j++) {
    		ids.push(list[j].id);
    	}
    	if(ids.indexOf(row.id) > -1){ // 有该元素
    	}else{
		    list.push(row);
    	}
    	return [
    		"<a title='查看方法变更详情' onclick='method_body_details("+row.id+")'"
    		+"style='background-color: ;cursor: pointer;text-decoration:underline;'>"+value+"</a>",  
    		].join("");
    }
    function method_body_details(id){
    	for(j = 0; j < list.length; j++) {
    		if(list[j].id == id){
	    		alert(list[j].methodBody);
	    		break;
    		}
    	}
    }
    // 方法传参为英文字母字符串
    function paramTypeFormatter(value, row, index) {
    	if(value.length > 30){
    		var a = value.toString();
	    	return ['<a title="查看完整的参数类型"	 onclick="allParams(\''+value+'\')">'
	    		+'<span>'+value.substring(0,29)+'...'+'</span></a>',
	    		].join('');
    	}else{
	    	return [value,].join('');
    	}
    }
    function changeTypeFormatter(value, row, index) {
    	if(value == 1){
	    	return ['<span>新增</span>',].join('');
    	}else if(value == 2){
	    	return ['<span>已删除</span>',].join('');
    	}else if(value == 3){
	    	return ['<span>修改</span>',].join('');
    	}else{
	    	return ['',].join('');
    	}
    }
    function testOrNotFormatter(value, row, index) {
    	if(value == 1){
	    	return ['<span style="color:green";>已完成测试</span>',].join('');
    	}else{
	    	return ['<span style="color:red";>未测试</span>',].join('');
    	}
    }
    function allParams(value){
    	var a = value;
    	//alert("");
    }
    window.operateEvents1 = {
    		'click .RoleOfA': function(e, value, row, index) {
    			detailmodal.open();
    			$("#dev_id").val(row.id);
    			$("#seq_no").val(row.seq_no);
    			$("#dev_pos").val(row.position);
    			$("#dev_type1").val(row.type);
    			$("#dev_status").val(row.status);
    			$("#fault").val(row.fault);
    			$("#buy_time").val(row.purchase_time);
    			$("#quality_time").val(row.quality_time);
    			$("#inputer").val(row.inputer);
    			$("#maintain_unit").val(row.maintain_unit);
    			for(var i in row) console.log(i);
    		}
    	};
    
    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {};

        oInit.Init = function () {
            $("#btn_add").click(function () {
                alert("add")
            });

            $("#btn_edit").click(function () {
                var arrselections = $("#tb_departments").bootstrapTable('getSelections');
                if (arrselections.length > 1) {
                    alert('只能选择一行进行编辑');

                    return;
                }
                if (arrselections.length <= 0) {
                    alert('请选择有效数据');
                    return;
                }
                alert("edit")
                //$("#myModalLabel").text("编辑");
                //$("#txt_departmentname").val(arrselections[0].DEPARTMENT_NAME);
                //$("#txt_parentdepartment").val(arrselections[0].PARENT_ID);
                //$("#txt_departmentlevel").val(arrselections[0].DEPARTMENT_LEVEL);
                //$("#txt_statu").val(arrselections[0].STATUS);

                //postdata.DEPARTMENT_ID = arrselections[0].DEPARTMENT_ID;
                //$('#myModal').modal();
            });

            $("#btn_delete").click(function () {
                var arrselections = $("#tb_departments").bootstrapTable('getSelections');
                if (arrselections.length <= 0) {
                    alert('请选择有效数据');
                    return;
                }

                Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
                    if (!e) {
                        return;
                    }
                    $.ajax({
                        type: "post",
                        url: "/Home/Delete",
                        data: { "": JSON.stringify(arrselections) },
                        success: function (data, status) {
                            if (status == "success") {
                                toastr.success('提交数据成功');
                                $("#tb_departments").bootstrapTable('refresh');
                            }
                        },
                        error: function () {
                            toastr.error('Error');
                        },
                        complete: function () {

                        }

                    });
                });
            });

            $("#btn_submit").click(function () {
                //postdata.DEPARTMENT_NAME = $("#txt_departmentname").val();
                //postdata.PARENT_ID = $("#txt_parentdepartment").val();
                //postdata.DEPARTMENT_LEVEL = $("#txt_departmentlevel").val();
                //postdata.STATUS = $("#txt_statu").val();
                //$.ajax({
                //    type: "post",
                //    url: "/Home/GetEdit",
                //    data: { "": JSON.stringify(postdata) },
                //    success: function (data, status) {
                //        if (status == "success") {
                //            toastr.success('提交数据成功');
                //            $("#tb_departments").bootstrapTable('refresh');
                //        }
                //    },
                //    error: function () {
                //        toastr.error('Error');
                //    },
                //    complete: function () {

                //    }

                //});
            });

            $("#btn_query").click(function () {
            	var gitUrl = $("#git_url").val();
            	var masterBranch = $("#master_branch").val();
            	var testBranch = $("#test_branch").val();
            	if(gitUrl == ""){
            		alert("git仓库不能为空");
            		return;
            	}
            	if(masterBranch == "" || masterBranch == "0"){
            		alert("稳定分支不能为空");
            		return;
            	}
            	if(testBranch == "" || testBranch == "0"){
            		alert("测试分支不能为空");
            		return;
            	}
            	getData_before();
            	$.post('/changeCode/getChangeData?git_url='+gitUrl+'&master_branch='+masterBranch+'&test_branch='+testBranch,
        				function(json){
            		getData_after();
            				alert(json.res);
            				if(json.success == true){
	                			$("#tb_departments").bootstrapTable('refresh');
            				}
        		});
            });
        };

        return oInit;
    };
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
    function getBranchs(obj){
    	if(obj.value == ""){
    		alert("git仓库不能为空");
    		return;
    	}
    	getData_before();
    	$.ajaxSettings.async = true; //异步,默认就是异步
    	$.post('/changeCode/getBranchList?git_url='+obj.value,
				function(json){
    		getData_after();
    				if(json.success == false){
    					alert(json.res);
    				}else{
    					var data = json.list;
    					var opt=$("<option value='0' selected>请选择</option>");
    					var opt2=$("<option value='0' selected>请选择</option>");
			            $("#master_branch").append(opt);
    			        $("#test_branch").append(opt2);
    					$.each(data,function(index,item){
    			            var opt=$("<option value="+item+">"+item+"</option>");
    			            var opt2=$("<option value="+item+">"+item+"</option>");
    			            $("#master_branch").append(opt);
    			            $("#test_branch").append(opt2);
    			        });
    				}
		});
    }
    
    var TableInit_forTestingExample = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_methodChainOriginal').bootstrapTable({
                url: '/testingExample/getAllMethodChainOriginal',         //请求后台的URL（*）
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
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
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
                    checkbox: true
                }, 
                {
                    field: 'id',
                    title: '主键id'
                },
                {
                    field: 'packageUrl',
                    title: '包路径', //align: 'center'
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
                statu: $("#txt_search_statu").val()
            };
            return temp;
        };
        return oTableInit;
    };
    function guanlian(){
    	var arrselections1 = $("#tb_testingExample").bootstrapTable('getSelections'); // 测试用例列表
    	var arrselections2 = $("#tb_methodChainOriginal").bootstrapTable('getSelections'); // 方法链
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
        
        var testingExampleId = arrselections1[0].id;
        var methodChainOriginalId = JSON.stringify(ms);
        $.post('/testingExample/exampleLinkMethodChain?testingExampleId='+testingExampleId+'&methodChainOriginalId='+methodChainOriginalId,
				function(json){
        	
		});
    }
    </script>
    
    
</body>
</html>