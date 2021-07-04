<template>
<div>
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>智能健康互联</h2>
        <p>------以太坊------</p>
      </div>
      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
          <input v-model="account" type="text" name="admnumber" id="LAY-user-login-username" lay-verify="required" placeholder="用户名" class="layui-input" >
        </div>
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
          <input v-model="password" type="password" name="admpassword" id="LAY-user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
        </div>
        <div class="layui-form-item">
          <div class="layui-row">
            <div class="layui-col-xs7">
              <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
              <input type="text" name="vercode" id="LAY-user-login-vercode" placeholder="图形验证码" class="layui-input input-val">
            </div>
            <div class="layui-col-xs5">
              <div style="margin-left: 10px;">
                 <canvas id="canvas" width="100" height="43"></canvas>
              </div>
            </div>
          </div>
        </div>
        <!-- <div class="layui-form-item" style="margin-bottom: 20px;">
          <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
          <a href="forget.html" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">忘记密码？</a>
        </div> -->
        <div class="layui-form-item">
          <a href="/register" class="layadmin-user-jump-change layadmin-link layui-hide-xs">没有账号？去注册>></a>
          <button @click="login" class="layui-btn layui-btn-fluid btn" lay-submit lay-filter="LAY-user-login-submit">登 入</button>
        </div>
        <!-- <div class="layui-trans layui-form-item layadmin-user-login-other">
          <label>社交账号登入</label>
          <a href="javascript:;"><i class="layui-icon layui-icon-login-qq"></i></a>
          <a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
          <a href="javascript:;"><i class="layui-icon layui-icon-login-weibo"></i></a>

          <a href="" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
        </div> -->
      </div>
    </div>
  </div>
</div>
</template>

<script>
import $ from "jquery/dist/jquery.js"
var allow = 0
export default {
  mounted(){
    $(function(){
      var show_num = [];
      draw(show_num);

      $("#canvas").on('click',function(){
          draw(show_num);
      })
      $(".btn").on('mousedown',function(){
          var val = $(".input-val").val().toLowerCase();
          var num = show_num.join("");
          if(val==''){
            layer.msg('请输入验证码！');
            allow = 0;
          }else if(val == num){
            allow = 1;

              draw(show_num);

          }else{
            layer.msg('验证码错误！请重新输入！');
            allow = 0;
              $(".input-val").val('');
              draw(show_num);
          }
      })
    })
    function draw(show_num) {
      var canvas_width=$('#canvas').width();
      var canvas_height=$('#canvas').height();
      var canvas = document.getElementById("canvas");//获取到canvas的对象，演员
      var context = canvas.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
      canvas.width = canvas_width;
      canvas.height = canvas_height;
      var sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
      var aCode = sCode.split(",");
      var aLength = aCode.length;//获取到数组的长度

      for (var i = 0; i <= 3; i++) {
          var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
          var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
          var txt = aCode[j];//得到随机的一个内容
          show_num[i] = txt.toLowerCase();
          var x = 10 + i * 20;//文字在canvas上的x坐标
          var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
          context.font = "bold 23px 微软雅黑";

          context.translate(x, y);
          context.rotate(deg);

          context.fillStyle = randomColor();
          context.fillText(txt, 0, 0);

          context.rotate(-deg);
          context.translate(-x, -y);
      }
      for (var i = 0; i <= 5; i++) { //验证码上显示线条
          context.strokeStyle = randomColor();
          context.beginPath();
          context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
          context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
          context.stroke();
      }
      for (var i = 0; i <= 30; i++) { //验证码上显示小点
          context.strokeStyle = randomColor();
          context.beginPath();
          var x = Math.random() * canvas_width;
          var y = Math.random() * canvas_height;
          context.moveTo(x, y);
          context.lineTo(x + 1, y + 1);
          context.stroke();
      }
    }

    function randomColor() {//得到随机的颜色值
        var r = Math.floor(Math.random() * 256);
        var g = Math.floor(Math.random() * 256);
        var b = Math.floor(Math.random() * 256);
        return "rgb(" + r + "," + g + "," + b + ")";
    }
  },
  data(){
    return {
      account: '',
      password: '',
    }
  },
  methods: {
    login(){
      if(0 == allow){
        return;
      }
      const that = this;
      this.$http.post(http+'/api/org/login', {account:this.account,password:this.password}).then(function({data: res}){
        if('200' === res._code){
          // 设置token，记录登录状态

          window.sessionStorage.setItem('token', res._data.token.toString())
          window.sessionStorage.setItem('userInfo', res._data.userInfo.toString())
          location.href = "/organization"  // 调转到admin路由
        } else{
          layui.use(['layer'], function(){layui.layer.msg('用户名或密码不正确')})
        }
      })
    }
  }

}
</script>
