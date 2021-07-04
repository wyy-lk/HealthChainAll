<template>
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 头部区域 -->
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect>
            <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
              <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
            </a>
          </li>
          <!-- <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="http://www.layui.com/admin/" target="_blank" title="前台">
              <i class="layui-icon layui-icon-website"></i>
            </a>
          </li> -->
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
          <!-- <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <input type="text" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search" layadmin-event="serach" lay-action="template/search.html?keywords=">
          </li> -->
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">

          <li class="layui-nav-item" lay-unselect>
            <a lay-href="/message/queryAdminMessage" layadmin-event="message" lay-text="消息中心" id ="message">
              <i class="layui-icon layui-icon-notice"></i>

              <!-- 如果有新消息，则显示小圆点 -->
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="theme">
              <i class="layui-icon layui-icon-theme"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="note">
              <i class="layui-icon layui-icon-note"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="fullscreen">
              <i class="layui-icon layui-icon-screen-full"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;">
              <cite id="subadminname">超管</cite>
            </a>
            <dl class="layui-nav-child">

              <dd><a href="/login">退出</a></dd>
            </dl>
          </li>

          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" ><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
          <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
        </ul>
      </div>

      <!-- 侧边菜单 -->
      <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll">
          <div class="layui-logo" lay-href="/homePage">
            <span id="subSchoolName">管理员</span>
          </div>

          <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
            <li data-name="home" class="layui-nav-item layui-nav-itemed">
              <a href="javascript:;" lay-tips="主页" lay-direction="2">
                <i class="layui-icon layui-icon-home"></i>
                <cite>主页</cite>
              </a>
              <dl class="layui-nav-child">
                <dd data-name="console" class="layui-this">
                  <a>个人主页</a>
                </dd>
              </dl>
            </li>
            <li data-name="app" class="layui-nav-item">
              <a href="javascript:;" lay-tips="人员管理" lay-direction="2">
                <i class="layui-icon layui-icon-app"></i>
                <cite>人员管理</cite>
              </a>
              <dl class="layui-nav-child">
                <dd>
              		<a lay-href="/adminManage" target="_blank">管理员管理</a>
              	</dd>
                <dd>
              		<a lay-href="/organManage" target="_blank">机构管理</a>
              	</dd>
                <dd>
              		<a lay-href="/userManage" target="_blank">用户管理</a>
              	</dd>
              </dl>
            </li>
            <li data-name="template" class="layui-nav-item" >
              <a href="javascript:;" lay-tips="机构审核" lay-direction="1">
                <i class="layui-icon layui-icon-template"></i>
                <a lay-href="/organVerify" target="_blank" style="padding-left:30px;">机构审核</a>

              </a>
            </li>

            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="服务管理" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <cite>服务管理</cite>
              </a>
              <dl class="layui-nav-child">
                <dd>
                  <a lay-href="/serviceVerify">服务审核</a>
                </dd>
                <dd>
                  <a lay-href="/serviceManage">服务管理</a>
                </dd>
              </dl>
            </li>
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="合约参数" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/contractInfo" style="padding-left:30px;">合约参数</a>
                <!-- <cite>数据查看</cite> -->
              </a>
            </li>

            <!-- <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="数据查看" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/getData" style="padding-left:30px;">数据查看</a>
                <!-- <cite>数据查看</cite> -->
              <!-- </a>
            </li> --> -->

            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="矿机信息" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/blockInfo" style="padding-left:30px;">矿机信息</a>
                <!-- <cite>矿机信息</cite> -->
              </a>
            </li>
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="我的钱包" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/wallet" style="padding-left:30px;">我的钱包</a>

              </a>
            </li>
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="日志信息" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/logManage" style="padding-left:30px;">日志信息</a>
              </a>
            </li>
          </ul>
        </div>
      </div>
      <!-- 页面标签 -->
      <div class="layadmin-pagetabs" id="LAY_app_tabs">
        <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-down">
          <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
            <li class="layui-nav-item" lay-unselect>
              <a href="javascript:;"></a>
              <dl class="layui-nav-child layui-anim-fadein">
                <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
          <ul class="layui-tab-title" id="LAY_app_tabsheader">
            <li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
          </ul>
        </div>
      </div>


      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
          <iframe src="/homePage" frameborder="0" class="layadmin-iframe"></iframe>
        </div>
      </div>

      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
  </div>
</template>
<script>
export default{
  // mounted(){
  //   layui.use(['layer'], function(){
  //     var layer = layui.layer //弹层
  //     //向世界问个好
  //     layer.msg('!');

  //   });
  //   console.log("kkkkkkkkkkkkkkkkk")
  // }
}
</script>
