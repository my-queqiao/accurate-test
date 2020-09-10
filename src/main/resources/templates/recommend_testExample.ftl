<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>精准测试-推荐案例</title>
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
	    	<div class="col-xs-12" style="">
		        <table id="tb_testingExample"></table>
	    	</div>
	    	
	    </div>
	    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	        <div class="modal-dialog" role="document" style="width:1000px; height:100px;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel">关联方法链</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="form-group">
	                        <div id="methodDetail"></div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	                </div>
	            </div>
	        </div>
	    </div>
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
            $('#tb_testingExample').bootstrapTable({
                url: '/changeCode/recommendTestExample',         //请求后台的URL（*）
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
                pageSize: 2147483647,                       //每页的记录行数（*）
                pageList: 'all',        //可供选择的每页的行数（*）
                //pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                minimumCountColumns: 2,             //最少允许的列数
                singleSelect: true,                 //是否单选模式
                //height: $(window).height()-100,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                showColumns: false,                  //是否显示所有的列
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showRefresh: false,                  //是否显示刷新按钮
                clickToSelect: false,                //是否启用点击选中行
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                columns: [
                /* {
                    checkbox: true
                }
                ,  */
                {
                    field: 'id',
                    title: '主键', //align: 'center'
                    align: 'center',
                	//events: operateEvents1,
                	//formatter: operateFormatter
                }
                , 
                {
                    field: 'belongProduct',
                    title: '所属产品', //align: 'center'
                    align: 'center',
                	//events: operateEvents1,
                	//formatter: operateFormatter
                }
                ,
                {
                    field: 'function',
                    title: '功能', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'subfunction',
                    title: '子功能', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'testItem',
                    title: '测试项', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'testPoint',
                    title: '测试点', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'testCaseNumber',
                    title: '测试案例编号', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'testOperationExplain',
                    title: '测试操作说明', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'expectedResults',
                    title: '预期结果', //align: 'center'
                    align: 'center',
                },
                {
                    field: 'productionTaskNumber',
                    title: '生产任务编号', //align: 'center'
                    align: 'center',
                }
                ,
                {
                    field: 'teType',
                    title: '案例类型', //align: 'center'
                    align: 'center',
                	//events: operateEvents1,
                    formatter: teTypeFormatter
                }
                ,
                {
                    field: '',
                    title: '操作', //align: 'center'
                    align: 'center',
                	//events: operateEvents1,
                	formatter: operateFormatter1
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
    function teTypeFormatter(value, row, index) {
    	if(value == 2){
	    	return ["<span style='background-color: green;color:white;' >推荐案例</span>"].join("");
    	}else{
    		return ["<span style='color:black;' >关联案例</span>"].join("");
    	}
    }
    function operateFormatter(value, row, index) {
    	return [
    		"<a title='查看关联的方法链' 	onclick='method_body_details("+row.id+")'"
    		+"style='background-color: ;cursor: pointer;text-decoration:underline;'>"+value+"</a>",  
    		].join("");
    }
    function operateFormatter1(value, row, index) {
    	return [
    		"<a class='btn btn-success' id='"+row.id+"start"+"' onclick='testExampleLinkMethod("+row.id+");'>查看关联方法</a>"
    		].join("");
    }
	function testExampleLinkMethod(id){
		$.ajaxSettings.async = false; //同步,默认就是异步
		$.post('/testingExample/getMethodLinkByTestExampleId?testExampleId='+id,
				function(json){
				var list = json.list;
				var yonglis="";
				var number=0;
				for(var j = 1; j < list.length+1; j++) {
					var n = list[j-1];
					if(n.lastMethodId == null || n.lastMethodId == ""){
						// 父节点
						yonglis += "<span style='color:blue;'>"+(++number)+" . "+n.packageName
								+"."+n.javabeanName+"."+n.methodName+"("+n.paramType+")</span><br/>";
						var number2=0;
						for(var k = 1; k < list.length+1; k++) {
							var p = list[k-1];
							if(n.id == p.lastMethodId){
								// 第二级
								yonglis += "<span style='color:black;'>"
									+"&nbsp&nbsp&nbsp&nbsp"+(++number2)+" . "+p.packageName
									+"."+p.javabeanName+"."+p.methodName+"("+p.paramType+")</span><br/>";
									var number3=0;
									for(var k3 = 1; k3 < list.length+1; k3++) {
										var p3 = list[k3-1];
										if(p.id == p3.lastMethodId){
											// 第三级
											yonglis += "<span style='color:green;'>"
												+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+(++number3)
												+" . "+p3.packageName
												+"."+p3.javabeanName+"."+p3.methodName+"("+p3.paramType+")</span><br/>";
												var number4=0;
												for(var k4 = 1; k4 < list.length+1; k4++) {
													var p4 = list[k4-1];
													if(p3.id == p4.lastMethodId){
														// 第四级
														yonglis += "<span style='color:gray;'>"
															+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
															+(++number4)+" . "+p4.packageName
															+"."+p4.javabeanName+"."+p4.methodName+"("+p4.paramType+")</span><br/>";
															var number5=0;
															for(var k5 = 1; k5 < list.length+1; k5++) {
																var p5 = list[k5-1];
																if(p4.id == p5.lastMethodId){
																	// 第五级	（先展示到第五层方法）
																	yonglis += "<span style='color:gray;'>"
																		+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
																		+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
																		+(++number5)+" . "+p5.packageName
																		+"."+p5.javabeanName+"."+p5.methodName
																		+"("+p5.paramType+")</span><br/>";
																}
														    }
													}
											    }	
										}
								    }	
							}
						}
					}
		    	}
				$('#myModal').on('show.bs.modal', function (event) {
		           	 var modal = $(this);
		           	 if(yonglis == ""){
			           	 modal.find('#methodDetail').html("无");
		           	 }else{
			           	 modal.find('#methodDetail').html(yonglis);
		           	 }
	           	});
		});
		$("#myModalLabel").text("关联方法链详情");
        $('#myModal').modal();
	}
	function sendStart(testExampleIp){
		$.post('/testingExample/testExampleStart?testExampleId='+currentTestExampleId+'&ipOnTestExample='+testExampleIp,
				function(json){
    				if(json.success == true){
    					testExampleIp2 = testExampleIp;
    					alert("开始成功");
            			//$("#tb_departments").bootstrapTable('refresh');
            			$("#"+currentTestExampleId+"start").html("执行中。。");
    				}else{
    					alert("开始失败："+json.msg);
    				}
		});
	}
	function endTestExample(id){
		$.post('/testingExample/testExampleEnd?testExampleId='+id+'&ipOnTestExample='+testExampleIp2,
			function(json){
   				if(json.success == true){
   					alert("结束成功");
           			//$("#tb_departments").bootstrapTable('refresh');
           			$("#"+currentTestExampleId+"end").html("已结束");
   				}else{
   					alert("结束失败");
   				}
		});
	}
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
    
    </script>
    
    
</body>
</html>