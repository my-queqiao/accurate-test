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
<body class="container-fiuled" style="background-color: aliceblue;">
    <div class="panel-body" style="padding-bottom:0px;">
	    <div class="row text-center">
	    	<span class="col-xs-12 " style="font-size: xx-large;">
	    		<span style="margin-left: ;">精准测试项目</span>
	    	</span>
	    	<span class="" style="font-size:large;bottom:-20px;position: absolute;left: 68%;top: 33px;">
	    		<a href="${request.contextPath}/testingExample/knowledgeBase" target="_blank" style="margin-left: ;color: blue;
	    			cursor: pointer;text-decoration:underline;float:left;">知识库</a>
	    	</span>
	    	<span class="" style="font-size:large;bottom:-20px;position: absolute; left: 78%;top: 33px;">
	    		<a target="_blank" onclick="getTestedMethods();" style="margin-left: ;color: blue;
	    			cursor: pointer;text-decoration:underline;float:left;">获取已测试方法</a>
	    	</span>
	    	<span class="" style="font-size:large;bottom:-20px;position: absolute;left: 92%;top: 33px;">
	    		<a href="${request.contextPath}/coverageReport/index" target="_blank" style="margin-left: ;color: blue;
	    			cursor: pointer;text-decoration:underline;float:left;">覆盖率报告</a>
	    	</span>
	    	
	    </div>
	    <div id="loading" style="color:blue; display:none; position:absolute;
	    		top:120px; left:12em;z-index:9999;font-size: 38px;font-family: 宋体;" >正在请求数据，请稍等...</div>
        <div class="panel panel-default" style="height: 50px;">
            <div class="panel-body">
                <form id="formSearch" class="form-horizontal">
                    <div class="form-group" style="margin-top:-6px">
                        <label class="control-label col-sm-1" for="txt_search_departmentname">git仓库</label>
                        <div class="col-sm-3" style="width:30%;">
                            <input type="text" class="form-control" id="git_url" onBlur=getBranchs(this)>
                        </div>
                        <label class="control-label col-sm-1" for="txt_search_statu">稳定分支</label>
                        <div class="col-sm-2" style="width:12%;">
                            <select id="master_branch" class="form-control">
								<option value=""></option>
							</select>
                        </div>
                        <label class="control-label col-sm-1" for="txt_search_statu">测试分支</label>
                        <div class="col-sm-2" style="width:12%;">
                        	<select id="test_branch" class="form-control">
								<option value=""></option>
							</select>
                        </div>
                        <div class="col-sm-2" style="text-align:left;">
                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">获取变更代码</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!--  
        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-success">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <button id="btn_edit" type="button" class="btn btn-primary">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>
            <button id="btn_delete" type="button" class="btn btn-danger">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
            </button>
        </div>
        -->
        
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel">方法详情</h4>
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
	    <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel3">请输入测试服务器ip地址：</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="form-group">
	                        <input id="testServerIp" placeholder="#.#.#.#" />
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
    <!-- 用于列表点击下一页时传值，点击button按钮时赋值 -->
        <input id="dataOfPart" value="1" style="display:none;"/>
        <div>
        	<span style="font-size: 18px;">方法变更统计：</span>
            <a id="btn_add" class="btn btn-success">
                <span>新增：0个</span>
            </a>
            <a id="btn_edit" class="btn btn-primary">
                <span>修改：0个</span>
            </a>
            <a id="btn_delete" class="btn btn-danger">
                <span>删除：0个</span>
            </a>
            <a id="btn_all" class="btn btn-info">
                <span>总计：0个</span>
            </a>
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
        // 统计方法的增删改的个数
        statistics();
    });


    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_departments').bootstrapTable({
                url: '/changeCode/getAll',         //请求后台的URL（*）
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
                singleSelect: false,                 //是否单选模式
                height: $(window).height() - 200,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                /* {
                    field: 'id',
                    title: '主键id'
                }, */
                {
                    field: 'packageName',
                    title: '包路径', //align: 'center'
                	//events: operateEvents1
                	//formatter: operateFormatter1
                }, 
                {
                    field: 'javabeanName',
                    title: '类',
                }, 
                {
                    field: 'methodName',
                    title: '方法',
                    formatter: methodNameFormatter
                }, 
                {
                    field: 'paramType',
                    title: '参数类型',
                }, 
                {
                    field: 'changeType',
                    title: '变更类型',
                    formatter: changeTypeFormatter
                }, 
                {
                    field: 'testingOrNot',
                    title: '是否已完成测试',
                    formatter: testOrNotFormatter
                }, 
                {
                    field: 'linkTestExample',
                    title: '关联的测试用例数量',
                    formatter: linkTestExampleFormatter
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
                dataOfPart: $("#dataOfPart").val(),
                statu: $("#txt_search_statu").val()
            };
            return temp;
        };
        //var tb_rows=$('#tb_departments').bootstrapTable("getData").length; // 总行数
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
    			var md = list[j].methodBody;
    			var reg = new RegExp("\n","g");//g,表示全部替换。
    			var md2 = md.replace(reg,"<br>");
	    		$('#myModal').on('show.bs.modal', function (event) {
	           	 //var a = $(event.relatedTarget) // a that triggered the modal
	           	 //var id = a.data('id'), title = a.data('title'), description = a.data('description'); 
	           	 var modal = $(this);
	           	 //modal.find('#cm-modal-title').val(title);
	           	 modal.find('#methodDetail').html(md2);
	           	});
	           $("#myModalLabel").text("方法详情");
	           $('#myModal').modal();
	    		
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
    function linkTestExampleFormatter(value, row, index) {
    	if(value == ""){
	    	return ['',].join('');
    	}else{
    		var arr = value.split(",");
    		// js函数参数传递字符串，需要转义
	    	return ["<a title='查看测试用例详情' onclick='linkTestExample_details(\""+value+"\")'"
	    		+"style='background-color: ;cursor: pointer;text-decoration:underline;'>"+arr.length+" 个</a>",].join('');
    	}
    }
    function linkTestExample_details(value){
    	$.post('/changeCode/getLinkTestExample?testExampleIds='+value,
				function(json){
    				if(json.success == true){
    					var list = json.list; //遍历集合，model展示
    					var yonglis="";
    					for(var j = 0; j < list.length; j++) {
    						yonglis+=yonglis+list[j].testCaseNumber+"<br/>";
				    	}
    					$('#myModal').on('show.bs.modal', function (event) {
				           	 var modal = $(this);
				           	 modal.find('#methodDetail').html(yonglis);
			           	});
			           $("#myModalLabel").text("关联的测试用例详情");
			           $('#myModal').modal();
				    }
		});
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
    function zhishiku(){
    	
    }
    function statistics(){
    	$.post('/changeCode/statistics',
			function(json){
   				//alert(json.data);
   				var ccs = json.data;
   				var heji = 0;
   				for(var i = 0;i<ccs.length;i++){
   					var cc = ccs[i];
   					if(cc.changeType == 1){
   						$("#btn_add").html("<span>新增："+cc.id+"个</span>");
   					}else if(cc.changeType == 2){
   						$("#btn_delete").html("<span>删除："+cc.id+"个</span>");
   					}else if(cc.changeType == 3){
   						$("#btn_edit").html("<span>修改："+cc.id+"个</span>");
   					}
   					heji = heji + cc.id;
   				}
   				$("#btn_all").html("<span>总计："+heji+"个</span>");
		});
    }
    
    function checkIp(ip) {
        var strRegex = '[0-9]+\.{1}[0-9]+\.{1}[0-9]+\.{1}[0-9]+'; // 22.11.3.23.adf.df也可以匹配。
        return new RegExp(strRegex).test(ip);
    }
    
    function getTestedMethods(){
    	$("#myModalLabel3").text("请输入测试服务器ip地址");
        $('#myModal3').modal();
    }
    $("#btn_save_testserverip").click(function(){
    	var testServerIp = $("#testServerIp").val();
    	if(null != testServerIp  && "" != testServerIp) {
    		if(checkIp(testServerIp)){
    			$("#loading").show();
    			//sendStart(testExampleIp);
    			$.post('/coverageReport/getTestedMethods?testServerIp='+testServerIp,
    					function(json){
    						$("#loading").hide();
    						if(json.success == true){
    							alert("获取成功，数据已存储到后台");
    						}else{
    							alert("获取失败");
    						}
    				});
    		}else{
    			alert("ip地址格式有误");
    		}
    	}else{
    		alert("输入不能为空");
    	}
    });
    </script>
    
    
</body>
</html>