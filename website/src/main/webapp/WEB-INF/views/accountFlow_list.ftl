<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>-->
    <script type="text/javascript">
        $(function () {
            $("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));
            $("#pagination").twbsPagination({
                totalPages: ${result.totalPage},
                startPage: ${result.currentPage},
                initiateStartPageClick: false,
                onPageClick: function (event, page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });
        });
    </script>

</head>
<body>

    <#include "common/head-tpl.ftl" />
    <#assign currentNav="personal" />
    <#include "common/navbar-tpl.ftl" />

    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <#assign currentMenu="accountFlow_list" />
                <#include "common/leftmenu-tpl.ftl" />
            </div>
            <div class="col-sm-9">
                <form action="/ipLog.do" name="searchForm" id="searchForm" class="form-inline" method="post">
                    <input type="hidden" id="currentPage" name="currentPage" value="" />
                    <div class="form-group">
                        <label>时间范围</label>
                        <input type="text" class="form-control beginDate" name="beginDate" value=''/>
                    </div>
                    <div class="form-group">
                        <label></label>
                        <input type="text" class="form-control endDate" name="endDate" value=''/>
                    </div>
                    <div class="form-group">
                   <#--     <label>状态</label>
                        <select class="form-control" name="state" id="state">
                            <option value="-1">全部</option>
                            <option value="0">登录失败</option>
                            <option value="1">登录成功</option>
                        </select>-->
                        <script type="text/javascript">
                        </script>
                    </div>
                    <div class="form-group">
                        <button type="button" id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                    </div>
                </form>

                <div class="panel panel-default" style="margin-top: 20px;">
                    <div class="panel-heading">
                        账户流水
                    </div>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>流水记录</th>
                                <th>流水金额</th>
                                <th>流水时间</th>
                                <th>流水类型</th>
                            </tr>
                        </thead>
                        <tbody>
<#list result.data as item>
    <tr>
        <td>${item.note}</td>
        <td>${item.amount}</td>
        <td>${item.actionTime?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td>${item.accountActionTypeName}</td>
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
<#include "common/footer-tpl.ftl" />
</body>
</html>