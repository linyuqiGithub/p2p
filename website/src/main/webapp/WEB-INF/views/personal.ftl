<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<!-- 包含一个模板文件,模板文件的路径是从当前路径开始找 -->
		<#include "common/links-tpl.ftl" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		
		<script type="text/javascript">
			$(function () {
				$("#showBindPhoneModal").click(function () {
					/*展示手机绑定窗口*/
					$("#bindPhoneModal").modal("show");
                })
				/*发送验证码点击事件*/
				$("#sendVerifyCode").click(function () {
					//将发送验证码按钮存储在变量中
                    var _this = $("#sendVerifyCode");
                    var phoneNumber = $("#phoneNumber").val();
                    if (phoneNumber) {
                    $.ajax({
                        url: '/sendVerifyCode.do',
                        dataType: 'json',
						type:'POST',
                        data: {phoneNumber: phoneNumber},
                        success: function (data) {
                            if (data.success) {
                                $.messager.confirm("提示","成功发送验证码,请查看",function () {
									//按钮开始倒计时
                                    var time = 90;
									//设置定时器，每1000毫秒(1秒)执行function
                                    var timer = window.setInterval(function () {
                                        time--;
                                        _this.text(time + "秒后重新发送");
										//禁用发送验证码按钮
                                        _this.attr("disabled", true);
										//到时
                                        if (time == 0) {
											//清除定时器
                                            window.clearInterval(timer);
                                            _this.text("发送验证码");
											//取消发送验证码按钮的禁用
                                            _this.attr("disabled", false);
                                        }
                                    }, 1000)
                                })
                            } else {
                               $.messager.popup(data.msg);
                            }
                        }
                    })
                }else {
                        $.messager.popup("请填写手机号码");
					}
                });
				/*点击确定绑定手机*/
				$("#bindPhone").click(function () {
					 $("#bindPhoneForm").ajaxSubmit({
						  dataType: 'json',
                          success:function (data) {
							  if(data.success){
							      $.messager.confirm("提示","绑定成功",function () {
									  window.location.href='/personal.do';
                                  })
							  }else {
								  $.messager.popup(data.msg);
							  }
                          }
					 })
                });
                /*弹出邮箱绑定窗口*/
                $("#showBindEmailModal").click(function () {
					$("#bindEmailModal").modal("show");
                });
				/*邮箱绑定点击事件*/
				$("#bindEmail").click(function () {
					var email = $("#email").val();
					if(email != null){
					    $("#bindEmailForm").ajaxSubmit({
							dataType:'json',
							success:function (data) {
								if(data.success){
								    $.messager.confirm("提示","邮件已发送,请前往确认绑定",function () {
										window.location.reload();
                                    })
								}else {
								    $.messager.popup("邮箱发送失败");
								}
                            }
						})
					}else {
					    $.messager.popup("请输入邮箱");
					}
                })



            })
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<!-- 网页导航 -->
		<!-- 在当前的freemarker的上下文中,添加一个变量,变量的名字叫nav,变量的值叫personal,用于包含页面navbar中哪一个标签处于选中状态 -->
		<#assign currentNav='personal'/>
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
				    <#assign currentMenu='userInfo'/>
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-body el-account">
							<div class="el-account-info">
								<div class="pull-left el-head-img">
									<img class="icon " src="/images/ms1.png" height="200" width="180" />
								</div>
								<div class="pull-left el-head">
									<p>用户名：${(logininfo.username)!""}</p>
									<p>最后登录时间：2015-01-25 15:30:10</p>
								</div>
								<div class="pull-left" style="text-align: center;width: 400px;margin:30px auto 0px auto;">
									<a class="btn btn-primary btn-lg" href="/recharge.do">账户充值</a> &nbsp;
									<a class="btn btn-danger btn-lg" href="/moneyWithdraw.do">账户提现</a>
								</div>
								<div class="clearfix"></div>
							</div>
							
							<div class="row h4 account-info">
								<div class="col-sm-4">
									<#-- 保留两位小数 -->
									账户总额：<span class="text-primary">${account.amount?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									可用金额：<span class="text-primary">${account.usableAmount?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									冻结金额：<span class="text-primary">${account.freezedAmount?string("0.00")}元</span>
								</div>
							</div>
							
							<div class="row h4 account-info">
								<div class="col-sm-4">
									待收利息：<span class="text-primary">${account.unReceiveInterest?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									待收本金：<span class="text-primary">${account.unReceivePrincipal?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									待还本息：<span class="text-primary">${account.unReturnAmount?string("0.00")}元</span>
								</div>
							</div>

							<div class="row h4 account-info">
								<div class="col-sm-4">
									体验金余额：<span class="text-primary">${expAccount.usableAmount?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									体验金冻结金额：<span class="text-primary">${expAccount.freezedAmount?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									临时垫收体验金：<span class="text-primary">${expAccount.unReturnExpAmount?string("0.00")}元</span>
								</div>
							</div>

							<div class="el-account-info top-margin">
								<div class="row">
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shiming.png" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>实名认证</h5>
												<#if userinfo.isRealAuth>
													已认证
												<#else>
												<p>
													未认证
													<a href="/realAuth.do" id="">立刻绑定</a>
												</p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">实名认证之后才能在平台投资</p>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shouji.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>手机认证</h5>
												<#-- 判断用户是否已经完成手机绑定 -->
												<#if userinfo.hasBindPhone>
												<p>
													已认证      <#-- 修改绑定的功能没做 -->
													<a href="javascript:;" >修改绑定</a>
												</p>
												<#else>
                                                    <p>
                                                        未认证
                                                        <a href="javascript:;" id="showBindPhoneModal">立刻绑定</a>
                                                    </p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">可以收到系统操作信息,并增加使用安全性</p>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/youxiang.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>邮箱认证</h5>
												<#-- 判断用户是否已经完成邮箱绑定 -->
										        <#if userinfo.hasBindEmail>
												<p>
													已绑定      <#-- 修改绑定的功能没做 -->
													<a href="javascript:;" >修改绑定</a>
												</p>
												<#else>
                                                    <p>
                                                        未绑定
                                                        <a href="javascript:;" id="showBindEmailModal">去绑定</a>
                                                    </p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">您可以设置邮箱来接收重要信息</p>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/baozhan.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>VIP会员</h5>
												<p>
													普通用户
													<a href="#">查看</a>
												</p>
											</div>
											<div class="clearfix"></div>
											<p class="info">VIP会员，让你更快捷的投资</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<#-- 弹出的绑定手机号的窗口 -->
        <div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone.do">
							<div class="row">
                            <div class="form-group">
                                <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" />
                                </div>
								<div class="col-sm-6">
									<button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">发送验证码</button>
								</div>
                            </div>
							</div>
							<div class="row">
                            <div class="form-group">
                                <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="verifyCode" name="verifyCode" />
                                </div>
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
		<#-- 弹出的绑定邮箱的窗口 -->
        <div class="modal fade" id="bindEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="bindEmailForm" method="post" action="/sendEmail.do">
                            <div class="form-group">
                                <label for="email" class="col-sm-2 control-label">Email:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="email" name="email" />
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="bindEmail">保存</button>
                    </div>
                </div>
            </div>
        </div>
		<#include "common/footer-tpl.ftl" />
	</body>
</html>