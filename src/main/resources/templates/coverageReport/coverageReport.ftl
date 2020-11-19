<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<title>精准测试</title>
  		<#include "../includes/head.ftl">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
  <#include "../includes/head-menu-bar.ftl">
  <#include "../includes/left-menu-bar.ftl">
  <!--右侧内容开始-->
  <div class="content-wrapper">
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-12">
          
	    	<span class="" style="font-size:large;bottom:-20px;position: absolute; left: 82%;top: -10px;">
	    		<a target="_blank" onclick="coverageReport();" style="margin-left: ;color: blue;
	    			cursor: pointer;text-decoration:underline;float:left;">获取测试项目所有方法</a>
	    	</span>
        <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                    <h4 class="modal-title" id="myModalLabel2">请输入目标服务器ip地址：</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="form-group">
	                        <input id="testExampleIp" placeholder="#.#.#.#" />
	                    </div>
	                </div>
	                <div class="modal-footer">
	                	<button type="button" id="btn_save" class="btn btn-primary" data-dismiss="modal">
	                		<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>确认</button>
	                    <button type="button" class="btn btn-default" data-dismiss="modal">
	                    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div id="loading" style="color:blue; display:none; position:absolute;
	    		top:120px; left:12em;z-index:9999;font-size: 38px;font-family: 宋体;" >正在请求数据，请稍等...</div>
        <table id="tb_departments"></table>
        
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
    <script>
    $("#childMenuName").html("覆盖率报告");
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
                    title: '已测试的<br>类数量',
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
                    title: '已测试的<br>方法数量',
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
                    title: '已测试的<br>新增方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.addFunTestedNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'modifyFunNumber',
                    title: '修改方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.modifyFunNumber;
                    	});
                    	return sum;
                    }
                }, 
                {
                    field: 'modifyFunTestedNumber',
                    title: '已测试的<br>修改方法数量',
                    footerFormatter:function(list){
                    	var sum = 0;
                    	$.each(list,function(i,element){
                    		sum += element.modifyFunTestedNumber;
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
    		"<a title='查看包详情' onclick='package_details(\""+value+"\")'"
    		+"style='background-color: ;cursor: pointer;text-decoration:underline;'>"+value+"</a>",  
    		].join("");
    }
    function package_details(value){
    	window.location.href="${request.contextPath}/coverageReport/toClassInfo?packageName="+value;
    }
    function coverageReport(){
        $("#myModalLabel2").text("请输入目标服务器ip地址");
        $('#myModal2').modal();
  }
    function checkIp(ip) {
        var strRegex = '[0-9]+\.{1}[0-9]+\.{1}[0-9]+\.{1}[0-9]+'; // 22.11.3.23.adf.df也可以匹配。
        return new RegExp(strRegex).test(ip);
    }
    $("#btn_save").click(function(){
    	var testExampleIp = $("#testExampleIp").val();
    	if(null != testExampleIp  && "" != testExampleIp) {
    		if(checkIp(testExampleIp)){
    			getAllMethodInfo(testExampleIp);
    		}else{
    			alert("ip地址格式有误");
    		}
    	}else{
    		alert("输入不能为空");
    	}
    });
    function getAllMethodInfo(testExampleIp){
    	$("#loading").show();
    	$.post('/coverageReport/getAllMethodInfo?ipOnTestExample='+testExampleIp,
				function(json){
    		$("#loading").hide();
    				if(json.success == false){
    					alert(json.msg);
    				}else{
    					$("#tb_departments").bootstrapTable('refresh');
    				}
		});
    }
    </script>
    
    
</body>
</html>