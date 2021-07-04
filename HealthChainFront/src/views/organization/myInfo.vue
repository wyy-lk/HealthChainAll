<template>
<div style="padding: 20px;"  class="layui-form">
    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <label class="layui-form-label">登录账号</label>
      <div class="layui-input-inline">
        <input type="text" lay-verify="required" name="account" autocomplete="off" class="layui-input infoData" v-model="account">
      </div>
      </div>
    </div>
    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <label class="layui-form-label">以太地址</label>
      <div class="layui-input-inline">
        <span class="layui-span ethAddr" id="ethAddress"></span>
      </div>
      </div>
    </div>

    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <label class="layui-form-label">机构名称</label>
      <div class="layui-input-inline">
        <input type="text" lay-verify="required" name="organizationName" autocomplete="off" class="layui-input infoData" v-model="organizationName">
      </div>
      </div>
    </div>

    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <label class="layui-form-label">机构简介</label>
      <div class="layui-input-inline">
        <textarea lay-verify="required" type="text" name="introduction" autocomplete="off" class="layui-textarea infoData" v-model="introduction"/>
      </div>
      </div>
    </div>

    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <label class="layui-form-label">背景图片</label>
      <div class="layui-input-inline">
        <img class="backImage"  id="portrait" src="" />
      </div>
      </div>
    </div>

    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <label class="layui-form-label">修改图片</label>
      <div class="layui-input-inline">
        <button id="uploadImages" name="images" autocomplete="off" class="layui-btn layui-btn-primary infoData">上传图片</button>
      </div>
      </div>
    </div>

    <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
      <div class="layui-input-block">
        <button id="submitBtn" type="button" class="layui-btn infoData"  lay-submit lay-filter="setmyinfo" @mousedown="checkUpload">确认修改</button>

      </div>
      </div>
    </div>
</div>
</template>

<style scoped>
.backImage{
  max-width: 300px;
  max-height: 300px;
}
.ethAddr{
  background-color: rgba(255,255,255,0);
  width: 300px;
  display:inline-block;
  word-wrap:break-word;
  white-space:normal;
  border:0px;
}
.infoData{
  width:300px;
}
</style>

<script>
export default {
  data() {
    return {
      "account": "",
      "ethAddress": "",
      "organizationName": "",
      "introduction": ""
    }
  },
  methods: {
    initData(vueObj){


      this.$http.post(http+"/api/org/getMyInfo", {token:window.sessionStorage.getItem('token')}).then(function({data: res}){
        if("200" === res._code){
          // layui.use(['layer'], function(){layui.layer.msg(res._code)})
          vueObj.account = res._data.account,
          document.getElementById("portrait").src = IMAGEPATH+ res._data.pic,
          document.getElementById("ethAddress").innerHTML = res._data.ethAddress,//不可编辑，不可上传
          vueObj.organizationName = res._data.organizationName,
          vueObj.introduction = res._data.introduction
        }
        else{
          layui.use(['layer'], function(){layui.layer.msg('数据加载失败...')})
        }
      })
    },
    checkUpload(){
      const that = this;
      if (''==that.account ||''==that.organizationName ||''==that.introduction) {
        return
      }else{
        var check_data = {
              stop:"1",
              token:window.sessionStorage.getItem('token'),
              portrait:document.getElementById("portrait").src,
              account:that.account,
              organizationName:that.organizationName,
              introduction:that.introduction
            }
        that.$http.post(http+'/api/org/updateOrgInfo', check_data).then(({data: res}) => {
          if('200' === res._code){
            layer.msg(res._msg);
            setTimeout(function () {
              parent.location.reload()
            }, 800);
          } else {
            layer.msg(res._msg)
          }
        })
      }
    }
  },
  mounted: function(){
    this.initData(this)
    const that = this;
      layui.use('upload', function(){
      var $ = layui.$
      ,upload = layui.upload;
      //执行实例
      var uploadInst = upload.render({
        elem: '#uploadImages' //绑定元素
        ,url: http + '/api/org/updateOrgInfo' //上传接口
        ,method:'POST'
        ,auto: false
        ,field: 'file'
        ,bindAction: '#submitBtn'
        ,accept: 'images'
        ,acceptMime: 'image/*'
        ,before: function(obj){
          if (''==that.account ||''==that.organizationName ||''==that.introduction) {
            this.data = {
              stop:"0",
              token:window.sessionStorage.getItem('token'),
              account:that.account,
              organizationName:that.organizationName,
              introduction:that.introduction
            }
          }else{
            this.data = {
              stop:"1",
              token:window.sessionStorage.getItem('token'),
              account:that.account,
              organizationName:that.organizationName,
              introduction:that.introduction
            }
          }
        }
        ,done: function(res,index){
          if (res._code=="200") {
            layui.use(['layer'], function(){layui.layer.msg(res._msg)})
            setTimeout(function () {
              parent.location.reload()
            }, 800);
          } else {

          }

        }
        ,error: function(){
          //请求异常回调
        }
      });
    })
  }
}
</script>
