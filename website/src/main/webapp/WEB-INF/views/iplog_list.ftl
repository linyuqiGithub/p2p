<#--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>-->

<#--</head>
<body>

    <!-- 网页顶部导航 &ndash;&gt;
    <#include "common/head-tpl.ftl" />
    <!-- 网页导航 &ndash;&gt;
    <#assign currentNav="personal" />
    <#include "common/navbar-tpl.ftl" />

    <div class="container">
        <div class="row">
            <!--导航菜单&ndash;&gt;
            <div class="col-sm-3">
                <#assign currentMenu="ipLog" />
                <#include "common/leftmenu-tpl.ftl" />
            </div>
            <!-- 功能页面 &ndash;&gt;
            <div class="col-sm-9">
                <form action="/ipLog.do" name="searchForm" id="searchForm" class="form-inline" method="post">
                    <input type="hidden" id="currentPage" name="currentPage" value="" />
                    <div class="form-group">
                        <label>时间范围</label>
                        <input type="text" class="form-control beginDate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'/>
                    </div>
                    <div class="form-group">
                        <label></label>
                        <input type="text" class="form-control endDate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd"))!""}'/>
                    </div>
                    <div class="form-group">
                        <label>状态</label>
                        <select class="form-control" name="state" id="state">
                            <option value="-1">全部</option>
                            <option value="0">登录失败</option>
                            <option value="1">登录成功</option>
                        </select>
                        <script type="text/javascript">
                            //从qo中获取当前查询的登陆状态，根据该状态选中下拉列表
                            $("#state option[value=${qo.state}]").attr("selected",true);
                        </script>
                    </div>
                    <div class="form-group">
                        <button type="button" id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                    </div>
                </form>

                <div class="panel panel-default" style="margin-top: 20px;">
                    <div class="panel-heading">
                        登录日志
                    </div>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>用户</th>
                                <th>登录时间</th>
                                <th>登录ip</th>
                                <th>登录状态</th>
                            </tr>
                        </thead>
                        <tbody>-->
<#list result.data as item>
    <tr>
        <td>${item.username}</td>
        <td>${item.loginTime?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td>${item.ip}</td>
        <td>${item.display}</td>
    </tr>
</#list>

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
<#--	</tbody>
</table>
<div style="text-align: center;">
    &lt;#&ndash; 分页组件 &ndash;&gt;
    <ul id="pagination" class="pagination"></ul>
</div>
</div>
</div>
</div>
</div>
<div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
<div class="modal-dialog" role="document">
<div class="modal-content">
<div class="modal-header">
<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
<h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
</div>
<div class="modal-body">
<form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone.do">
    <div class="form-group">
        <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" />
            <button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">发送验证码</button>
        </div>
    </div>
    <div class="form-group">
        <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="verifyCode" name="verifyCode" />
        </div>
    </div>
</form>
</div>
<div class="modal-footer">
<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
<button type="button" class="btn btn-primary" id="bindPhone">保存</button>
</div>
</div>
</div>
</div>
<#include "common/footer-tpl.ftl" />
</body>
</html>-->