<div class="el-header" >
		<div class="container" style="position: relative;">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/index.do">首页</a></li>
				    <#-- 没有登陆显示 -->
				    <#if !logininfo??>
					<li><a href="/login.html">登录</a></li>
					<li><a href="/register.html">快速注册</a></li>
					</#if>
                    <#-- 登陆了显示 -->
				    <#if logininfo??>
					<li>
						  <a class="el-current-user" href="/personal.do">${logininfo.username}</a>
					</li>
					<li><a  href="/recharge.do">账户充值  </a></li>
					<li><a  href="/logout.do">注销 </a></li>
                    </#if>
				<li><a href="#">帮助</a></li>
			</ul>
		</div>
</div>
