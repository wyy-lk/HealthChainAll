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

          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>

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
              <cite id="account">机构</cite>
            </a>
            <dl class="layui-nav-child">
              <dd><a lay-href="/updatePasswordOrg">修改密码</a></dd>
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
            <span  id="orgName">机构用户</span>
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
            <li data-name="template" class="layui-nav-item" >
              <a href="javascript:;" lay-tips="服务管理" lay-direction="1">
                <i class="layui-icon layui-icon-template"></i>
                <a lay-href="/serviceManageOrg" target="_blank" style="padding-left:30px;">服务管理</a>
              </a>
            </li>

            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="数据查看" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/getDataOrg" style="padding-left:30px;">数据查看</a>
              </a>
            </li>
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="个人信息" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/myInfoOrg" style="padding-left:30px;">个人信息</a>
              </a>
            </li>
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="我的钱包" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/walletOrg" style="padding-left:30px;">我的钱包</a>

              </a>
            </li>
            <!-- <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="上传数据" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/uploadDataOrg" style="padding-left:30px;">上传数据</a>
              </a>
            </li> -->
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="身份审核" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <a lay-href="/authenticationOrg" style="padding-left:30px;">身份审核</a>
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
export default {
  mounted(){
    layui.use(['layer'], function(){
      var layer = layui.layer;
      var $ = layui.$;
      $.ajax({
              url: http+"/api/org/getMyInfo",
              type: 'POST',
              data: {token:window.sessionStorage.getItem('token')},
              success: function (info) {
                if("200" === info._code){
                  $('#orgName').html(info._data.organizationName);
                  $('#account').html(info._data.account);
                }
                else{
                  layer.msg('数据加载失败...')
                }
              }
        });
    })
  }
}
</script>
