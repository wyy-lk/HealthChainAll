<template>
<div>
  <div id="container">
    <div class="layui-inline">
      <table style="display: inline-block" class="layui-hide" id="orgTable" lay-filter="orgUpadte"></table>
    </div>
  </div>
  <!-- <script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
      <button class="layui-btn layui-btn-sm" lay-event="add"  style = "margin-top:10px;margin-left:30px;position:relative;float:left;">添加</button>
    </div>
  </script> -->
  <script type="text/html" id="handler">
    <!-- <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a> -->
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
    this.initTable()
  },
  methods: {
    // 初始化表格
    initTable(){
      const that = this
      layui.use('table', function(){
        that.table = layui.table
        // 获取数据
        that.table.render({
          id: 'listReload'
          ,elem: '#orgTable'
          ,height: 420
          ,title: '服务表'
          ,url:http+'/api/admin/getOrgInfoList'
          ,method:'post'
          ,where:{token:window.sessionStorage.getItem('token')}
          ,page: true //开启分页
          ,toolbar: 'true'
          ,cols: [[ //表头
          {field: 'id', title: '机构ID',sort: true, align:'center'}
          ,{field: 'account', title: '机构账号', align:'center'}
          ,{field: 'ethAddress', title: '以太坊账号', align:'center'}
          ,{field: 'organizationName', title: '机构名', align:'center'}
          ,{field: 'type', title: '机构类型', align:'center'}
          ,{field: 'introduction', title: '机构介绍', align:'center'}
          ,{field: 'tel', title: '机构电话', align:'center'}
          ,{field: 'certificateResult', title: '认证结果', align:'center'}
    //       ,{field: 'certificateFiles', title: '认证文件', align:'center'}
          ,{field: 'certificateTime', title: '认证时间', align:'center'}
          ,{width: 140, title:'操作', align:'center', toolbar: '#handler'}
          ]]
        })

        // 监听删除按钮
        that.table.on('tool(orgUpadte)', function(obj){
          var data = obj.data;
          switch(obj.event){
            case 'del':
              layer.confirm('真的删除此机构吗', function(index){
                const org = {}
                org.id =data.id;
                that.delOrg(org, index);
              });
              break;
          }
        })
      })
    },


    // 删除机构
    delOrg(org, index){
      const that = this
      layui.use('table', function(){
        var layer = layui.layer;
        that.$http.post(http+'/api/admin/delOrgInfo', {token:window.sessionStorage.getItem('token'),id:org.id}).then(({data: res}) => {
          if('200' === res._code){
            // org.del();
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
    }
  }
}
</script>

<style scoped>
  #container{
    text-align: center;
  }
</style>

