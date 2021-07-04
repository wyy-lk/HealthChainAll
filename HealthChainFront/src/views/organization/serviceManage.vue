<template>
<div>
  <div id="container">
    <div class="layui-inline">
      <table style="display: inline-block" class="layui-hide" id="serviceTable" lay-filter="serviceUpadte"></table>
    </div>
  </div>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add"  style = "margin-top:10px;margin-left:30px;position:relative;float:left;">添加</button>
  </div>
</script>
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
    //验证资格
    this.$http.post(http+'/api/org/getMyInfo', {token:window.sessionStorage.getItem('token')}).then(({data: res}) => {
      if('200' === res._code){

        if(res._data.type =="普通"){
          layui.use('layer',function(){
            layui.layer.msg("审核通过后的医疗机构才可以访问本页面，请先完成医疗机构审核",{
            time: 5000})
          })
        }else{
          this.initTable()
        }
      } else {
        layer.msg('获取信息失败')
      }
    })


  },
  methods: {
    // 初始化表格
    initTable(){
      const that = this
      layui.use('table', function(){
        that.table = layui.table
        // 获取数据
        that.table.render({
          id: 'listReload',
          elem: '#serviceTable'
          ,height: 420
          ,title: '服务表'
          ,url:http+'/api/org/getMedicalServiceList'
          ,method:'post'
          ,where:{token:window.sessionStorage.getItem('token')}
          ,page: true //开启分页
          ,toolbar: '#toolbarDemo'
          ,defalutToolbar: ['add']
          ,cols: [[ //表头
            {field: 'id', title: '医疗服务ID', width:140, align: 'center'}
            ,{field: 'serviceName', title: '服务名称', width:200, align: 'center'}
            ,{field: 'serviceDetails', title: '服务详情', width: 200, align: 'center'}
            ,{field: 'cost', title: '服务费用（Wei）', width:200, align: 'center'}
            ,{field: 'auditResult', title: '审核状态', width: 200, align: 'center'}
            ,{width: 140, align:'center', toolbar: '#handler'}
          ]]
        })

        // 监听工具条
        that.table.on('toolbar(serviceUpadte)', function(obj){
          if(obj.event === 'add'){
            layer.open({
              type: 1,
              title: '添加服务',
              skin: 'layui-layer-molv',
              content: that.getServiceDialog(),
              btn: ['添加', '取消'],
              yes: function (index, layero) {
                const service = {}
                service.serviceName = document.getElementById('serviceName').value;
                service.cost = document.getElementById('cost').value;
                service.serviceDetails = document.getElementById('serviceDetails').value;
                that.addService(service, index)
              }
            })
          } else{
            layer.msg('未开放')
          }
        });

        // 监听删除和修改按钮
        that.table.on('tool(serviceUpadte)', function(obj){
          var data = obj.data;
          switch(obj.event){
            case 'edit':
              layer.open({
                type: 1,
                title: '修改服务信息',
                skin: 'layui-layer-molv',
                content: that.getServiceDialog(obj.data),
                btn: ['修改', '取消'],
                yes: function (index, layero) {
                  const service = {}

                  service.id =data.id;
                  service.serviceName = document.getElementById('serviceName').value;
                  service.cost = document.getElementById('cost').value;
                  service.serviceDetails = document.getElementById('serviceDetails').value;
                  that.updateService(obj, service, index)
                }
              })
              break;
            case 'del':
              layer.confirm('真的删除此服务吗', function(index){
                const service = {}
                service.id =data.id;
                that.delService(service, index);
              });
              break;
          }
        })
      })
    },

    // 增加服务
    addService(service, index){
      const that = this
      layui.use('table', function(){
        var layer = layui.layer;
        if(service.serviceName === '' || service.serviceDetails === '' || service.cost === ''){
          layer.msg('字段不能为空')
          return
        }
        if(isNaN(service.cost) || parseInt(service.cost) < 0){
          layer.msg('费用输入不合法')
          return
        }
        that.$http.post(http+'/api/org/insertMedicalService', {token:window.sessionStorage.getItem('token'),serviceName:service.serviceName,serviceDetails:service.serviceDetails,cost:service.cost}).then(({data: res}) => {
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
      })
    },

    // 更新服务
    updateService(obj, service, index){
      const that = this
      layui.use('table', function(){
        var layer = layui.layer;
        // layer.msg('字段不能为空')
        if(service.serviceName === '' || service.serviceDetails === '' || service.cost === ''){
          layer.msg('字段不能为空')
          return
        }
        if(isNaN(service.cost) || parseInt(service.cost) < 0){
          layer.msg('费用输入不合法')
          return
        }
        that.$http.post(http+'/api/org/updateMedicalService', {token:window.sessionStorage.getItem('token'),id:service.id,serviceName:service.serviceName,serviceDetails:service.serviceDetails,cost:service.cost}).then(({data: res}) => {
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
      })
    },

    // 删除服务
    delService(service, index){
      const that = this
      layui.use('table', function(){
        var layer = layui.layer;
        that.$http.post(http+'/api/org/delMedicalService', {token:window.sessionStorage.getItem('token'),id:service.id}).then(({data: res}) => {
          if('200' === res._code){
            // service.del();
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
      })
    },

    // 获取服务对话框
    getServiceDialog(service, readonly=''){
      return `<div style="padding: 15px 50px 15px 15px"><label for="serviceName">服务名称：</label><input id="serviceName" autocomplete="off"  ${readonly} value="${service===undefined?'':service.serviceName}" /><br><br>
             <label for="cost">服务费用：</label><input id="cost" autocomplete="off" type="number" min="0" ${readonly} value="${service===undefined?'':service.cost}"/><br><br>
             <label for="serviceDetails">服务详情：</label><textarea id="serviceDetails" cols="25" rows="5" style="resize: none" ${readonly}>${service===undefined?'':service.serviceDetails}</textarea></div>`
    }
  }
}
</script>
<style scoped>
  #container{
    text-align: center;
  }
</style>
