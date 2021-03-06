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
        	 <form id="formSearch" class="form-horizontal">
                    <div class="form-group row" style="margin-top:-6px">
                        <label class="control-label col-sm-1" for="txt_search_departmentname">生产任务编号</label>
                        <div class="col-sm-3" style="width:30%;">
                            <!-- <input type="text" class="form-control" id="git_url" onBlur=getBranchs(this)> -->
                            <input type="text" class="form-control" id="productionTaskNumber" disabled>
                        </div>
                        <label class="control-label col-sm-1" for="txt_search_statu">稳定分支</label>
                        <div class="col-sm-2" style="width:12%;">
                            <select id="master_branch" class="form-control" onclick="getBranchs(this);">
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
                            <button type="button" style="margin-left:0px" id="btn_query" class="btn btn-primary">获取变更代码</button>
                        </div>
                    </div>
                </form> 
                
          <div style="font-size: 15px;">
        	<span>方法变更统计：</span>
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
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <a id="" class="btn btn-success"
            	href="${request.contextPath}/changeCode/recommend_testExample" target="_blank">
                <span>推荐测试用例</span>
            </a>
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <a id="" class="btn btn-success" onclick="getTestedMethods();">
                <span>获取已测试方法</span>
            </a>
       	</div>
        <input id="dataOfPart" value="1" style="display:none;"/><!-- 用于列表点击下一页时传值	(点击button按钮时赋值) -->
           
        </div>
     </div>
    </div>
  </div>
  <section class="content">
   <div class="container-fluid">
       <table id="tb_departments"></table>
   </div>
  </section>
</div>
<!--右侧内容结束-->
</div>
    
    <div class="modal fade in" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <h4 class="modal-title" id="myModalLabel">方法详情</h4>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                </div>
	                <div class="modal-body">
	                    <div class="form-group">
	                        <div style="color:blue;" id="">推荐案例：</div>
	                        <div id="recommendTestExample"></div>
	                        <br/>
	                        <div style="color:blue;" id="">变更方法关联的所有案例：</div>
	                        <div id="allTestExample"></div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	                </div>
	            </div>
	        </div>
	    </div>
	    
        <div class="modal fade in" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content" style="width:1000px;margin-left: -40%;">
	                <div class="modal-header">
	                    <h4 class="modal-title" id="myModalLabel2">方法详情</h4>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
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
	    
	    <div class="modal fade in" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <h4 class="modal-title" id="myModalLabel3">请输入测试服务器ip地址：</h4>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
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
    <script>
    $("#childMenuName").html("变更代码");
    var productionTaskNumber = "${Session['loginUser'].productionTaskNumber ? default('')}";
    if(productionTaskNumber == ""){
    	alert("请先选择生产任务编号"); // 后台处理过了，没有指定编号时，这儿不会执行
    }
    if(productionTaskNumber != ""){
    	$("#productionTaskNumber").val(productionTaskNumber);
    }
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
                //height: $(window).height() - 150,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                dataOfPart: $("#dataOfPart").val(), // 增删改全部，四种数据类型
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

    var list = []; // 数组，存放bootstrap-table的行数据。（用于展示变更方法详情）
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
    	window.open("/changeCode/body?id="+id);
    }
    function method_body_details_feiqi(id){
    	for(j = 0; j < list.length; j++) {
    		if(list[j].id == id){
    			var md = list[j].methodBody;
    			var reg = new RegExp("\n","g");//g,表示全部替换。
    			var md2 = md.replace(reg,"<br>");
	    		$('#myModal2').on('show.bs.modal', function (event) {
	           	 //var a = $(event.relatedTarget) // a that triggered the modal
	           	 //var id = a.data('id'), title = a.data('title'), description = a.data('description'); 
	           	 var modal = $(this);
	           	 //modal.find('#cm-modal-title').val(title);
	           	 modal.find('#methodDetail').html(md2);
	           	});
	           $("#myModalLabel2").text("方法详情");
	           $('#myModal2').modal();
	    		
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
    					for(var j = 1; j < list.length+1; j++) {
    						yonglis += j+"."+list[j-1].testCaseNumber+"<br/>";
				    	}
    					$('#myModal2').on('show.bs.modal', function (event) {
				           	 var modal = $(this);
				           	 modal.find('#methodDetail').html(yonglis);
			           	});
			           $("#myModalLabel2").text("关联的测试用例详情");
			           $('#myModal2').modal();
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
	var count = 0;
    function getBranchs(obj){ // obj
    	if(count >= 1){
    		return;
    	}
    	/* if(obj.value == ""){
    		alert("git仓库不能为空");
    		return;
    	} */
    	getData_before();
    	$.ajaxSettings.async = true; //异步,默认就是异步
    	$.post('/changeCode/getBranchList?productionTaskNumber='+productionTaskNumber,
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
    	count++;
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
    							$("#tb_departments").bootstrapTable('refresh');
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