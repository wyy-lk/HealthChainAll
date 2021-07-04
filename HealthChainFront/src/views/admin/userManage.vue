<!-- 所有用户 -->
<template>

  <!-- 用户信息 -->
  <div>
  <table class="layui-hide" id="demo" lay-filter="test"></table>

  <script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <!-- <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a> -->
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
  </script>

  <div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="layuiadmin-form-useradmin" style="padding: 20px 0 0 0;display:none">
    <div class="layui-form-item">
      <label class="layui-form-label">用户名</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="account" readonly="readonly">
      </div>
    </div>

    <div class="layui-form-item">
      <label class="layui-form-label">昵称</label>
      <div class="layui-input-inline">
        <input type="text" name="username" autocomplete="off" class="layui-input" v-model="nickname" readonly="readonly">
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

    <!-- <div class="layui-form-item">
      <label class="layui-form-label">账户余额</label>
      <div class="layui-input-inline">
        <input type="text" name="email" autocomplete="off" class="layui-input" v-model="balance" readonly="readonly">
      </div>
    </div> -->

    <div class="layui-form-item">
      <div class="layui-input-block">
        <button type="button" class="layui-btn" @click="updateUserInfo()" lay-submit lay-filter="setmyinfo">确认修改</button>
        <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
      </div>
    </div>
</div>

  </div>

</template>

<script>
export default {
  data(){
    return {
      userList: [],

      "id": "",
      "account": "",
      "nickname": "",
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
      const that = this;
      this.$http.get("/admin/userInfoData.json", {}).then(function({data: res}){
        if("200" === res._code){
          vueObj.userList = res._data.userList.data

          // 将列表数据显示在表格中
          layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
            var laydate = layui.laydate //日期
            ,laypage = layui.laypage //分页
            ,layer = layui.layer //弹层
            ,table = layui.table //表格
            ,carousel = layui.carousel //轮播
            ,upload = layui.upload //上传
            ,element = layui.element //元素操作
            ,slider = layui.slider //滑块
            ,$ = layui.$

            table.render({
              elem: '#demo'
              ,height: 420
              ,title: '用户管理表'
              ,url:http + '/api/admin/getUserList'
              ,method:'post'
              ,where:{token: window.sessionStorage.getItem('token')}
              ,page: true //开启分页
              ,toolbar: 'true' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
              ,totalRow: true //开启合计行
              // , url: "" // url 访问 返回值是  {"code": 0,"msg": "","count": 100, "data": []}
              ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
              // ,data: vueObj.userList     // 列表[]
              ,cols: [[
                {field:'id', title: 'id', sort: true, align:'center'}
                ,{field:'account', title: '用户名', align:'center'}
                ,{field:'nickname', title: '昵称', align:'center'}
                ,{field:'password', title: '密码', align:'center'}
                ,{field:'ethAddress', title: '以太坊地址', align:'center'}
                ,{field:'sex', title: '性别', align:'center'}
                ,{field:'address', title: '住址', align:'center'}
                ,{field:'birth', title: '出生日期', align:'center'}
                ,{field:'tel', title: '电话', align:'center'}
                // ,{field:'balance', title: '账户余额', align:'center'}
                ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo', align:'center'}  // 工具栏
              ]]
            });

              // 监听表格按钮事件
              table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
              var data = obj.data //获得当前行数据
              ,layEvent = obj.event; //获得 lay-event 对应的值
              if(layEvent === 'detail'){
                layer.msg('查看操作');

                vueObj.id = data.id,
                vueObj.account = data.account,
                vueObj.nickname = data.nickname,
                vueObj.password = data.password,
                vueObj.ethAddress = data.ethAddress,
                vueObj.sex = data.sex,
                vueObj.address = data.address,
                vueObj.birth = data.birth,
                vueObj.tel = data.tel,
                // vueObj.balance = data.balance

                layer.open({
                  type: 1,
                  title: "用户信息",
                  content: $("#layuiadmin-form-useradmin"),  // 显示这个页面
                  shadeClose: true,
                  area: ["80%", "300px"],
                  maxmin: true,
                })

              } else if(layEvent === 'del'){
                layer.confirm('真的删除行么', function(index){
                  obj.del(); //删除对应行（tr）的DOM结构
                  layer.close(index);
                  //向服务端发送删除指令
                  that.$http.post(http + "/api/admin/deleteUser", {token: window.sessionStorage.getItem('token'), id: data.id} ).then(function({data: res}){
                    console.log(res._msg);
                  })

                });
              }
              // else if(layEvent === 'edit'){
              //   layer.msg('编辑操作');
              // }
            });

          });



        }
        else {
          layui.use(['layer'], function(){layui.layer.msg('数据加载失败...')})
        }
      })
    },

    updateUserInfo(){
      const that = this;
      this.$http.post(http + "/api/admin/updateUserInfo", {token: window.sessionStorage.getItem('token'), id: this.id, password: this.password}).then(function({data: res}){
        if("200" === res._code){
          layer.msg("修改成功!")
          location.reload();
        }
        else{
          layui.use(['layer'], function(){layui.layer.msg('数据加载失败...')})
        }
      })
    }

  },
  mounted: function(){
    this.initData(this);
  }
}
</script>
