<template>
<div>
  <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
  <legend id="checkType">您当前的账户类型为：普通机构用户</legend>
  </fieldset>

  <div class="layui-btn-container" style="padding-left:30px;padding-top:50px;">
    <button type="button" class="layui-btn" id="uploadExample">上传文件</button>
    <input type="text" readonly value="查看审核状态" class="layui-btn" @click="check_alert" id="testListAction" />
    <input id = "check_value" style = "display:none;"/>
  </div>
</div>

</template>

<script>
export default {
  mounted(){
    this.check()
    layui.use('upload', function(){
      var $ = layui.$
      ,upload = layui.upload;
      //执行实例
      var uploadInst = upload.render({
        elem: '#uploadExample' //绑定元素
        ,url: http + '/api/org/audit' //上传接口
        ,method:'POST'
        ,auto: true
        ,field: 'file'
        ,data:{token:window.sessionStorage.getItem('token')}
        ,accept: 'file'
        ,before: function(obj){
          this.data = {token:window.sessionStorage.getItem('token')}
        }
        ,done: function(res,index){
            layui.use('layer', function(){
              var layer = layui.layer;
              // layer.msg(index)
              layer.msg(res._msg, {
                time: 2000, //6s后自动关闭
                icon:1
              });
            });
        }
        ,error: function(){
          //请求异常回调
        }
      });
    })
  },
  methods:{
    check(){
      this.$http.post(http+"/api/org/getMyInfo", {token:window.sessionStorage.getItem('token')}).then(function({data: res}){
        if("200" === res._code){
          // this.organizationName = res._data.organizationName,
          // this.type = res._data.type,
          // this.certificateResult = res._data.certificateResult,
          // this.certificateFiles = res._data.certificateFiles,
          // this.certificateTime = res._data.certificateTime
          document.getElementById("checkType").innerHTML = "您当前的账户类型为："+res._data.type+"机构用户"
          document.getElementById("check_value").value = res._data.certificateResult
        }
        else{
          layui.use(['layer'], function(){layui.layer.msg(res._msg)})
        }
      })

    },
    check_alert(){
      layui.use(['layer'], function(){layui.layer.msg(document.getElementById("check_value").value)})
    }
  }
}
</script>>
