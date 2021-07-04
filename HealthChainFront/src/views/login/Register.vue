<template>
  <div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>智能健康互联</h2>
        <p>注册</p>
      </div>
      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
          <input v-model="account" type="text" name="cellphone" id="LAY-user-login-cellphone" lay-verify="phone" placeholder="手机" class="layui-input">
        </div>

        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
          <input v-model="password" type="password" name="password" id="LAY-user-login-password" lay-verify="pass" placeholder="密码" class="layui-input">
        </div>
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-repass"></label>
          <input v-model="repassword" type="password" name="repass" id="LAY-user-login-repass" lay-verify="required" placeholder="确认密码" class="layui-input">
        </div>
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-nickname"></label>
          <input v-model="organizationName" type="text" name="organizationName" id="LAY-user-login-nickname" lay-verify="organizationName" placeholder="昵称" class="layui-input">
        </div>

        <div class="layui-form-item">
          <button id="regSubmit" class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-reg-submit" @click="register">注 册</button>
        </div>
        <div class="layui-trans layui-form-item layadmin-user-login-other">


          <a href="/" class="layadmin-user-jump-change layadmin-link layui-hide-xs">用已有帐号登入</a>
          <a href="login.html" class="layadmin-user-jump-change layadmin-link layui-hide-sm layui-show-xs-inline-block">登入</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default{
  data(){
    return{
        account: '',           //手机号
        password: '',          //密码
        organizationName: ''   //机构名
    }

  },
  methods:{
    register(){

      const that = this;
      if(this.account.toString == '' || this.organizationName.toString == null || this.password.toString == null || this.repassword.toString == null)
      {
        layui.layer.msg("不能为空")
        return
      }
      //判断两次输入的密码是否一致
      if( this.password.toString() != this.repassword.toString() ){
        layui.layer.msg('两次输入的密码不一致')
        return
      }
      //如何判断用户是否同意用户协议
      layui.layer.msg('正在注册...', {
        icon: 16
        ,shade: 0.5
      });
      //判断是否注册成功
      that.$http.post(http+'/api/org/register', {account:this.account,password:this.password,organizationName:this.organizationName}).then(function({data: res}){
        if('200' === res._code){
          // window.sessionStorage.setItem('token', res._data.token.toString())
          layui.use(['layer'], function(){layui.layer.msg(res._msg)})
          layer.closeAll('loading');
          that.$router.push("/login")
        } else{
          layui.use(['layer'], function(){layui.layer.msg(res._msg)})
          layer.closeAll('loading');
        }
      })
    }
  }
}
</script>
