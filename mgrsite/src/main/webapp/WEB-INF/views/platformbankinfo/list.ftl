<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>蓝源Eloan-P2P平台(系统管理平台)</title>
	<#include "../common/header.ftl"/>
	<link rel="stylesheet" href="/css/bank.css">
	<script type="text/javascript" src="/js/bank.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
	
	<script type="text/javascript">
	$(function(){
		$('#pagination').twbsPagination({
			totalPages : ${result.totalPage}||1,
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

		$("#query").click(function(){
			$("[name=currentPage]").val("1");
			$("#searchForm").submit();
		});

		$("#editForm").ajaxForm({
			success:function(data){
			    if(data.success){
			        $.messager.confirm("提示","修改成功",function(){
			            window.location.href="/companyBank_list.do";
					})
				}else {
			        $.messager.popup("修改失败");
				}
			}
		})

		$(".edit_Btn").click(function(){
			var me=$(this);
			var json = me.data("json");
			$("#editForm input[name=id]").val(json.id);
			$("#editForm input[name=bankName]").val(json.bankName);
			//下拉列表中的银行列表数据回显
			$("#bankType option[value="+json.bankName+"]").attr("selected",true);
			$("#editForm input[name=accountName]").val(json.accountName);
			$("#editForm input[name=bankNumber]").val(json.bankNumber);
			$("#editForm input[name=forkName]").val(json.forkName);
			$("#companyBankModal").modal("show");
		});
		
		$("#saveBtn").click(function(){
			$("#editForm").submit();
		});
		
		$("#addCompanyBankBtn").click(function(){
			$("#editForm")[0].reset();
			$("bank_id").val();
			$("#companyBankModal").modal("show");
		});
		/*从bank.js获取的JSON对象,添加到平台账户添加窗口中的下拉列表中*/
		/*提交表单的时候提交的是name=bankName对应的value，也就是数字k*/
		for(var k in SITE_BANK_TYPE_NAME_MAP){
			var v=SITE_BANK_TYPE_NAME_MAP[k];
			$("<option value='"+k+"'>"+v+"</option>").appendTo($("#bankType"));
		}
	});
	</script>
</head>

<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu="companyBank" />
				<#include "../common/menu.ftl" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>平台账户管理</h3>
				</div>
				<div class="row">
					<!-- 提交分页的表单 -->
					<form id="searchForm" class="form-inline" method="post" action="/companyBank_list.do">
						<input type="hidden" name="currentPage" value=""/>
						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
							<a href="javascript:void(-1);" class="btn btn-success" id="addCompanyBankBtn">添加平台账户</a>
						</div>
					</form>
				</div>
				<div class="row">
					<table class="table">
						<thead>
							<tr>
								<th>银行</th>
								<th>开户人</th>
								<th>账号</th>
								<th>开户行</th>
							</tr>
						</thead>
						<tbody>
						<#list result.data as vo>
							<tr>
								<td><div class="bank bank_${vo.bankName}"></div></td>
								<td>${vo.accountName}</td>
								<td>${vo.bankNumber}</td>
								<td>${vo.forkName}</td>
								<td>
									<a href="javascript:void(-1);" class="edit_Btn" data-json='${vo.jsonString}'>修改</a> &nbsp;
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
	
	<div id="companyBankModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">编辑/增加</h4>
	      </div>
	      <div class="modal-body">
       		<form id="editForm" class="form-horizontal" method="post" action="/companyBank_saveOrUpdate.do" style="margin: -3px 118px">
			    <input type="hidden" name="id" id="bank_id" value="" />
			   	<div class="form-group">
				    <label class="col-sm-3 control-label">选择银行</label>
				    <div class="col-sm-6">
				   		<select id="bankType" class="form-control" autocomplete="off" name="bankName">
				   		</select>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-sm-3 control-label">开户人</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" name="accountName" />
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-sm-3 control-label">账号</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" name="bankNumber" />
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-sm-3 control-label">开户行</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" name="forkName" />
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