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
          <div class="col-sm-6">
          
            <a id="btn_add" class="btn btn-success">
                <span>新增</span>
            </a>
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <a id="btn_selectProductionTaskNumber" class="btn btn-success">
                <span>选择生产任务编号</span>
            </a>
          
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
  <!-- 右侧页面结束 -->
</div>
<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel3">请输入生产任务编号：</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span></button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <input id="productionTaskNumber" />
                    </div>
                </div>
                <div class="modal-footer">
                	<button type="button" id="btn_save_productionTaskNumber" class="btn btn-primary" data-dismiss="modal">
                		<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>确认</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                </div>
            </div>
        </div>
    </div>
<script>
$("#childMenuName").html("生产任务");
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    // 统计方法的增删改的个数
});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_departments').bootstrapTable({
            url: '/getPuductionTaskNumber',         //请求后台的URL（*）
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
            singleSelect: true,                 //是否单选模式
            //height: $(window).height() - 200,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            showColumns: false,                  //是否显示所有的列
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showRefresh: false,                  //是否显示刷新按钮
            clickToSelect: true,                //是否启用点击选中行
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            onCheck:function(row){ //点击每一个单选框时触发的操作
                //alert(row.id);
            	selectProductionTaskNumber(row);
              },
            columns: [{
                checkbox: true
            }, 
            {
                field: 'id',
                title: '主键id'
            }, 
            {
                field: 'productionTaskNumber',
                title: '生产任务编号', //align: 'center'
            	//events: operateEvents1
            	//formatter: operateFormatter1
            }, 
            {
                field: 'gitUrl',
                title: 'git地址', //align: 'center'
            }, 
            {
                field: 'masterBranch',
                title: '稳定分支', //align: 'center'
            }, 
            {
                field: 'testBranch',
                title: '测试分支', //align: 'center'
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
var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};
	
    oInit.Init = function () {

    };

    return oInit;
};

$("#btn_add").click(function () {
	$("#myModalLabel3").text("新增生产任务编号：");
    $('#myModal3').modal();
    //$("#tb_departments").bootstrapTable('refresh',opt);//带参数 刷新
});

$("#btn_selectProductionTaskNumber").click(function () {
	var arrselections1 = $("#tb_departments").bootstrapTable('getSelections');
	if (arrselections1.length <= 0) {
        alert('请选择有效数据');
        return;
    }
    var ptNumberId = arrselections1[0].productionTaskNumber;
 // 指定生产任务编号，区分后台数据
	$.post('/selectProductionTaskNumber?productionTaskNumber='+ptNumberId,
			function(json){
				if(json.success == false){
					alert(json.msg);
				}else{
					alert(json.msg);
					//currentProductionTaskNumber = grade;
				}
	});
});

$("#btn_save_productionTaskNumber").click(function(){
	var productionTaskNumber = $("#productionTaskNumber").val();
	if(null == productionTaskNumber  || "" == productionTaskNumber) {
		alert("不能为空");
		return;
	}
	$.post('/addProductionTaskNumber?productionTaskNumber='+productionTaskNumber,
			function(json){
				//$("#loading").hide();
				alert(json.msg);
				$("#tb_departments").bootstrapTable('refresh');
				if(json.success == true){
					
				}else{
				}
		});
});
function selectProductionTaskNumber(row){
    // 指定生产任务编号，区分后台数据
   	$.post('/selectProductionTaskNumber?productionTaskNumber='+row.productionTaskNumber,
   			function(json){
   				if(json.success == false){
   					alert(json.msg);
   				}else{
   					$("#showProductionTaskNumber").html(row.productionTaskNumber);
   					alert(json.msg);
   					//currentProductionTaskNumber = grade;
   				}
   	});
}
</script>
</body>
</html>
