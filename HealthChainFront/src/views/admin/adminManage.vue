<template>
<div>
  <div id="container">
    <div class="layui-inline">
      <table style="display: inline-block" class="layui-hide" id="adminTable" lay-filter="adminUpadte"></table>
    </div>
  </div>

  <script type="text/html" id="handler">
    <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
  </script>
</div>
</template>
<script>

export default {
  data(){
    return {
      table: {}
    }
  },
  mounted(){
    this.$http.post(http + '/api/admin/isSuperAdmin', {token: window.sessionStorage.getItem('token'),account:JSON.parse(window.sessionStorage.getItem('adminInfo')).account}).then(({data: res}) => {this.initTable(res)})
  },
  methods: {
    // 初始化表格
    initTable(res){
      if(res._code !== '200'){
        alert('非root管理员不可见')
        return
      }
      const that = this
      layui.use('table', function(){
        that.table = layui.table
        // 获取数据
        that.table.render({
          id: 'listReload',
          elem: '#adminTable'
          ,height: 420
          ,title: '用户表'
          ,url:http+'/api/admin/getAdminList'
          ,method:'post'
          ,where:{token:window.sessionStorage.getItem('token')}
          ,page: true //开启分页
          ,toolbar: 'default'
          ,defalutToolbar: []
          ,data: res._data
          ,cols: [[ //表头
            {field: 'id', title: '管理员ID', width:140, align: 'center'}
            ,{field: 'account', title: '管理员账号', width:200, align: 'center'}
            ,{field: 'password', title: '密码', width: 200, align: 'center'}
            ,{field: 'ethAddress', title: '以太坊账号', width:200, align: 'center'}
            ,{field: 'authority', title: '权限', width: 200, align: 'center'}
            ,{width: 140, align:'center', toolbar: '#handler'}
          ]]
        })

        // 监听工具条
        that.table.on('toolbar(adminUpadte)', function(obj){
          if(obj.event === 'add'){
            layer.open({
              type: 1,
              title: '添加管理员',
              skin: 'layui-layer-molv',
              content: `<div style="padding: 15px 50px 15px 15px"><label for="account">账户：</label><input id="account" autocomplete="off" /><br><br>
                        <label for="accopasswordunt">密码：</label><input id="password"/><br><br>
                        <!-- <label for="ethAddress">以太坊账号：</label><input id="ethAddress"/><br><br> -->
                        <label for="authority">权限：</label><select id="authority"><option value="normal">normal</option><option value="disable">disable</option>}</select></div>`,
              btn: ['添加', '取消'],
              yes: function (index, layero) {
                const admin = {}
                admin.account = document.getElementById('account').value;
                admin.password = document.getElementById('password').value;
                // admin.ethAddress = document.getElementById('ethAddress').value;
                admin.authority = document.getElementById('authority').value;
                that.addAdmin(admin, index)
              }
            })
          } else{
            layer.msg('未开放')
          }
        });

        // 监听删除和修改按钮
        that.table.on('tool(adminUpadte)', function(obj){
          switch(obj.event){
            case 'edit':
              layer.open({
                type: 1,
                title: '修改管理员',
                skin: 'layui-layer-molv',
                content: `<div style="padding: 15px 50px 15px 15px"><label for="account">账户：</label><input id="account" value="${obj.data.account}"/><br><br>
                          <label for="accopasswordunt">密码：</label><input id="password" value="${obj.data.password}"/><br><br>

                          <label for="authority">权限：</label><select id="authority">${obj.data.authority === 'root'? '<option value="root">root</option>':'<option value="normal">normal</option><option value="disable">disable</option>'}</select></div>`,
                btn: ['修改', '取消'],
                yes: function (index, layero) {
                  const admin = {}
                  admin.account = document.getElementById('account').value;
                  admin.password = document.getElementById('password').value;
                  admin.authority = document.getElementById('authority').value;
                  that.updateAdmin(obj, admin, index)
                }
              })
              break;
            case 'del':
              layer.confirm('真的删除此管理员吗', function(index){
                that.delAdmin(obj, index);
              });
              break;
          }
        })
      })
    },

    // 增加管理员
    addAdmin(admin, index){
      const that = this
      if(admin.account === '' || admin.password === '' || admin.authority === ''){
        layer.msg('字段不能为空')
        return
      }
      that.$http.post(http+'/api/admin/addAdminInfo', {token:window.sessionStorage.getItem('token'), account:admin.account, password:admin.password, authority:admin.authority}).then(({data: res}) => {
          if('200' === res._code){
            layer.msg(res._msg);
            layer.close(index);
            setTimeout(function () {
              location.reload()
            }, 800);
          } else {
            layer.close(index);
            layer.msg('添加失败')
          }
        })
    },

    // 更新管理员
    updateAdmin(obj, admin, index){
      const that = this
      if(admin.account === '' || admin.password === '' || admin.authority === ''){
        layer.msg('字段不能为空')
        return
      }
      that.$http.post(http+'/api/admin/updateAdmin', {token:window.sessionStorage.getItem('token'),id:obj.data.id, account:admin.account, password:admin.password, authority:admin.authority}).then(({data: res}) => {
        if('200' === res._code){
          layer.close(index);
          layer.msg(res._msg)
          setTimeout(function () {
            location.reload()
          }, 800);
        } else {
          layer.close(index);
          layer.msg(res._msg)
        }
      })
    },

    // 删除管理员
    delAdmin(admin, index){
      const that = this
      if(admin.data.authority === 'root'){
        layer.msg('不能删除root用户')
        return
      }
      that.$http.post(http+'/api/admin/deleteAdmin', {token:window.sessionStorage.getItem('token'),id:admin.data.id}).then(({data: res}) => {
          if('200' === res._code){
            layer.close(index);
            layer.msg(res._msg)
            setTimeout(function () {
              location.reload()
            }, 800);
          } else {
            layer.close(index);
            layer.msg(res._msg)
          }
        })
    }
  }
}
</script>
<style scoped>
  #container{
    text-align: center;
  }
</style>
