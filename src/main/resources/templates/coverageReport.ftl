<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>覆盖率报告</title>
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
	    	<span class="col-xs-12 " style="font-size: xx-large;">
	    		<span style="margin-left: ;">覆盖率报告</span>
	    	</span>
	    	
	    </div>
        
        <table id="tb_departments"></table>
    </div>
    <script>
    $(function () {

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
            $('#tb_departments').bootstrapTable({
                url: '/coverageReport/getAll',         //请求后台的URL（*）
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
                singleSelect: true,                 //是否单选模式
                //height: $(window).height() - 200,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                showColumns: false,                  //是否显示所有的列
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showRefresh: false,                  //是否显示刷新按钮
                clickToSelect: false,                //是否启用点击选中行
                //paginationPreText: "上一页",
                //paginationNextText: "下一页",
                showFooter:true,
                columns: [{
                    checkbox: true,
                    footerFormatter:function(value){
                    	return "合计";
                    }
                }, 
                /* {
                    field: 'id',
                    title: '主键id'
                }, */
                {
                    field: 'packageName',
                    title: '包路径',
                    formatter: packageNameFormatter
                }, 
                {
                    field: 'classNumber',
                    title: '类数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.classNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'testedclassNumber',
                    title: '已测试的类数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.testedclassNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'methodNumber',
                    title: '方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.methodNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'methodTestedNumber',
                    title: '已测试的方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.methodTestedNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'addFunNumber',
                    title: '新增方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.addFunNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'addFunTestedNumber',
                    title: '已测试的新增方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.addFunTestedNumber;
                    	});
                    	return sum;
                    }
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
                //dataOfPart: $("#dataOfPart").val(),
                //statu: $("#txt_search_statu").val()
            };
            return temp;
        };
        //var tb_rows=$('#tb_departments').bootstrapTable("getData").length; // 总行数
        return oTableInit;
    };
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
            	 var opt = {
            		        url: "/changeCode/getAll",
            		        silent: true,
            		        query:{
            		            dataOfPart:1,
            		        }
            		    };
                $("#tb_departments").bootstrapTable('refresh',opt);//带参数 刷新
                $("#dataOfPart").val(1);
            });

            $("#btn_edit").click(function () {
               	 var opt = {
               		        url: "/changeCode/getAll",
               		        silent: true,
               		        query:{
               		            dataOfPart:3,
               		        }
               		    };
                $("#tb_departments").bootstrapTable('refresh',opt);//带参数 刷新
                $("#dataOfPart").val(3);
            });

            $("#btn_delete").click(function () {
                  	 var opt = {
                  		        url: "/changeCode/getAll",
                  		        silent: true,
                  		        query:{
                  		            dataOfPart:2,
                  		        }
                  		    };
                $("#tb_departments").bootstrapTable('refresh',opt);//带参数 刷新
                $("#dataOfPart").val(2);
            });
           	$("#btn_all").click(function () {
                     	 var opt = {
                     		        url: "/changeCode/getAll",
                     		        silent: true,
                     		        query:{
                     		            dataOfPart:0,
                     		        }
                     		    };
                   $("#tb_departments").bootstrapTable('refresh',opt);//带参数 刷新
                   $("#dataOfPart").val(0);
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
            					statistics();
	                			$("#tb_departments").bootstrapTable('refresh');
            				}
        		});
            });
        };

        return oInit;
    };
    function packageNameFormatter(value, row, index) {
    	return [
    		"<a title='查看方法变更详情' onclick='package_details(\""+value+"\")'"
    		+"style='background-color: ;cursor: pointer;text-decoration:underline;'>"+value+"</a>",  
    		].join("");
    }
    function package_details(value){
    	//alert(value);
    	alert("点击查看包下面类、方法详情。待开发。");
    }
    </script>
    
    
</body>
</html>