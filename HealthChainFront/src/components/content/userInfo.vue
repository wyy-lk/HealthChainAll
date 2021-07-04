<template>
<div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="layuiadmin-form-useradmin" style="padding: 20px 0 0 0;">
    <div class="layui-form-item">
      <label class="layui-form-label">用户名</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="account" readonly="readonly">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">昵称</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="nickName" readonly="readonly">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">密码</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="password">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">以太坊地址</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="ethAddress" readonly="readonly">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">居住地址</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="address">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">出生日期</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="birth">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">手机号码</label>
      <div class="layui-input-inline">
        <input type="text" name="phone" autocomplete="off" class="layui-input" v-model="tel" >
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">账户余额</label>
      <div class="layui-input-inline">
        <input type="text" name="email" autocomplete="off" class="layui-input" v-model="balance" readonly="readonly">
      </div>
    </div>

    <!--
      <div class="layui-form-item" lay-filter="sex">
      <label class="layui-form-label">选择性别</label>
      <div class="layui-input-block">
        <input type="radio" name="sex" value="男" title="男" checked>
        <input type="radio" name="sex" value="女" title="女">
      </div>
    </div>
    -->

    <div class="layui-form-item">
      <div class="layui-input-block">
        <button type="button" class="layui-btn" @click="updateUserInfo()" lay-submit lay-filter="setmyinfo">确认修改</button>
        <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
      </div>
    </div>
</div>
</template>

<script>
export default {
  data() {
    return {
      "id": "",
      "account": "",
      "nickName": "",
      "password": "",
      "ethAddress": "",
      "sex": "",
      "address": "",
      "birth": "",
      "tel": "",
      "balance": ""
    }
  },
  methods: {
    initData(vueObj){

      this.$http.post(httP + "/api/admin/findUserInfo", {token: window.sessionStorage.getItem('token'), }).then(function({data: res}){
        if("200" === res._code){
          console.log(res)
          vueObj.id = res._data.id,
          vueObj.account = res._data.account,
          vueObj.nickName = res._data.nickName,
          vueObj.password = res._data.password,
          vueObj.ethAddress = res._data.ethAddress,
          vueObj.sex = res._data.sex,
          vueObj.address = res._data.address,
          vueObj.birth = res._data.birth,
          vueObj.tel = res._data.tel,
          vueObj.balance = res._data.balance
        }
        else{
          layui.use(['layer'], function(){layui.layer.msg('数据加载失败...')})
        }
      })
    },

    updateUserInfo(){
      const that = this;
      this.$http.get("/admin/userInfo_back.json", {}).then(function({data: res}){
        if("200" === res._code){
          that.password = res._data.password,
          that.address = res._data.address,
          that.tel = res._data.tel
          layer.msg("修改成功!")
        }
        else{
          layui.use(['layer'], function(){layui.layer.msg('数据加载失败...')})
        }
      })
    }
  },
  mounted: function(){
    this.initData(this)
  }
}
</script>
