<template>
<div align="center">
  <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>修改密码</legend>
  </fieldset>

  <div class="layui-form">
    <div class="layui-form-item">
      <div class="layui-inline" >
      <label class="layui-form-label">当前密码</label>
      <div class="layui-input-inline">
        <input type="password" id="oldPassword" name="oldPassword" lay-verify="required" lay-verType="tips" class="layui-input">
      </div>
      </div>
    </div>
    <div class="layui-form-item">
       <div class="layui-inline" >
      <label class="layui-form-label">新密码</label>
      <div class="layui-input-inline">
        <input type="password" name="password" lay-verify="required|pass" lay-verType="tips" autocomplete="off" id="LAY_password" class="layui-input">
      </div>
      <!-- <div class="layui-form-mid layui-word-aux">6到16个字符</div> -->
    </div>
     </div>
    <div class="layui-form-item">
      <div class="layui-inline" >
      <label class="layui-form-label">确认新密码</label>
      <div class="layui-input-inline">
        <input type="password" name="repassword" lay-verify="required|repass" lay-verType="tips" autocomplete="off" class="layui-input">
      </div>
    </div>
    </div>
    <div class="layui-form-item">
      <div class="layui-input-block">
        <button class="layui-btn" id="alterPassword" lay-submit lay-filter="alterPassword">确认修改</button>
      </div>
    </div>
  </div>
</div>

</template>
<script>
export default {
  mounted(){
    layui.use(['table'], function(){
      var $ = layui.$
      ,layer = layui.layer
	    ,form = layui.form
	    ,admin = layui.admin,
			table = layui.table;

	    form.on('submit(alterPassword)', function (data) {
        var formData = data.field;

        if(formData.password != formData.repassword){
          layer.msg("两次密码输入不一致！");
          return;
        }
        $.ajax({
              url: http+'/api/org/updatePassword',
              type: 'POST',
              data: {token:window.sessionStorage.getItem('token'),password:formData.oldPassword,newPassword:formData.password},
              success: function (info) {
                  if (info._code === "200") {
                    layer.msg(info._msg);
                      // setTimeout(function () {

                      //     location.href = '/login'
                      //   }, 800);
                  }else{
                    layer.msg(info._msg);
                  }
              }
        });
		  });
    });
  }
}
</script>
