<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- html <head>标签部分  -->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>蓝源Eloan-P2P平台(系统管理平台)</title>
	<#include "../common/header.ftl"/>
	<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$('#pagination').twbsPagination({
				totalPages : ${result.totalPage},
				startPage : ${result.currentPage},
				visiblePages : 5,
				first : "首页",
				prev : "上一页",
				next : "下一页",
				last : "最后一页",
				onPageClick : function(event, page) {
					$("#currentPage").val(page);
					$("#searchForm").submit();
				}
			});
			/*点击数据字典的某一分类时，表单显示出该数据字典分类下的具体数据字典明细*/
			$(".group_item").click(function () {
				var id = $(this).data("dataid");
				$("#parentId").val(id);
				$("#searchForm").submit();
            })
			/*提交页面刷新后获取此时隐藏域中parentId的值，此时的值就是之前选中的数据字典分类的id值*/
			var parentId = $("#parentId").val();
			if(parentId){
				/*激活此id对应的数据字典分类，并激活使其处于选中状态*/
			    $("[data-dataid="+parentId+"]").closest("li").addClass("active");
			}
			/*添加数据字典明细*/
			$("#addSystemDictionaryItemBtn").click(function () {
				//只有在选中数据字典的具体分类时才可以添加相应的数据字典明细
				if(parentId) {
					$("#editForm")[0].reset();//DOM对象的reset方法无法清空hidden域的数据
					/*为什么需要手动清空systemDictionaryId，因为选择编辑的时候弹出的编辑框中回显了值(hidden),此时的systemDictionaryId是当前数据字典明细的父id*/
					/*如果此时点击另一个数字字典分类，并选择添加该数据字典的明细，此时弹出的编辑框中的systemDictionaryId(hidden)仍是上一个编辑数据明细的父id，所以需要手动清空*/
					$("#systemDictionaryId").val("");
					$("#editFormParentId").val(parentId);
					$("#systemDictionaryItemModal").modal("show");
				}else{
					$.messager.popup("请先选中相应的数据字典分类");
				}
            });

            $("#editForm").ajaxForm({
                dataType:'json',
                success:function (data) {
                    if(data.success){
                        $.messager.confirm("提示","修改成功",function () {
                            $("#searchForm").submit();
                        })
                    }
                }
            });
			$("#saveBtn").click(function () {
				$("#editForm").submit();
            })
			/*点击修改按钮后的弹出的修改框中的数据回显*/
			$(".edit_Btn").click(function () {
				var json = $(this).data("jsonstring");
				$("#systemDictionaryId").val(json.id);
				$("#editFormParentId").val(json.parentId);
				$("#title").val(json.title);
                $("#sequence").val(json.sequence);
                $("#systemDictionaryItemModal").modal("show");
            })



						
		});
		</script>
</head>
<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu="systemDictionaryItem" />
				<#include "../common/menu.ftl" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>数据字典明细管理</h3>
				</div>
				<div class="col-sm-12">
					<!-- 提交分页的表单 -->
					<form id="searchForm" class="form-inline" method="post" action="/systemDictionaryItem_list.do">
						<input type="hidden" id="currentPage" name="currentPage" value="${(qo.currentPage)!1}"/>
						<input type="hidden" id="parentId" name="parentId" value='${(qo.parentId)!""}' />
						<div class="form-group">
						    <label>关键字</label>
						    <input class="form-control" type="text" name="keyword" value="${(qo.keyword!'')}">
						</div>
						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
							<a href="javascript:void(-1);" class="btn btn-success" id="addSystemDictionaryItemBtn">添加数据字典明细</a>
						</div>
					</form>
					<div class="row"  style="margin-top:20px;">
						<div class="col-sm-3">
							<ul id="menu" class="list-group">
								<li class="list-group-item">
									<a href="#" data-toggle="collapse" data-target="#systemDictionary_group_detail"><span>数据字典分组</span></a>
									<ul class="in" id="systemDictionary_group_detail">
										<#--列出所有数据字典分类-->
										<#list systemDictionaryGroups as vo>
										   <li><a class="group_item" data-dataid="${vo.id}" href="javascript:;"><span>${vo.title}</span></a></li>
										</#list>
									</ul>
								</li>
							</ul>
						</div>
						<div class="col-sm-9">
							<table class="table">
								<thead>
									<tr>
										<th>名称</th>
										<th>序列</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<#list result.data as vo>
									<tr>
										<td>${vo.title}</td>
										<td>${vo.sequence!""}</td>
										<td>
											<a href="javascript:void(-1);" class="edit_Btn" data-jsonstring='${vo.jsonString}'>修改</a> &nbsp;
										</td>
									</tr>
								</#list>
								</tbody>
							</table>
							
							<div style="text-align: center;">
								<ul id="pagination" class="pagination"></ul>
							</div>
				
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<#--数据字典明细添加或更新弹窗-->
	<div id="systemDictionaryItemModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">编辑/增加</h4>
	      </div>
	      <div class="modal-body">
	       	  <form id="editForm" class="form-horizontal" method="post" action="systemDictionaryItem_saveOrUpdate.do" style="margin: -3px 118px">
				    <input id="systemDictionaryId" type="hidden" name="id" value="" />
			    	<input type="hidden" id="editFormParentId" name="parentId" value="" />
				   	<div class="form-group">
					    <label class="col-sm-3 control-label">名称</label>
					    <div class="col-sm-6">
					    	<input type="text" class="form-control" id="title" name="title" placeholder="字典值名称">
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-sm-3 control-label">顺序</label>
					    <div class="col-sm-6">
					    	<input type="text" class="form-control" id="sequence" name="sequence" placeholder="字典值显示顺序">
					    </div>
					</div>
			   </form>
		  </div>
	      <div class="modal-footer">
	      	<a href="javascript:void(0);" class="btn btn-success" id="saveBtn" aria-hidden="true">保存</a>
		    <a href="javascript:void(0);" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>