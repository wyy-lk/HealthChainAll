<template>
<div>
  <div id="container">
    <div class="layui-inline">
      <table style="display: inline-block" class="layui-hide" id="orgAuditTable" lay-filter="orgAuditUpadte"></table>
    </div>
  </div>
  <script type="text/html" id="handler">
    <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
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
          id: 'listReload',
          elem: '#orgAuditTable'
          ,height: 420
          ,title: '审核表'
          ,url:http+'/api/admin/getOrgInfoList'
          ,method:'post'
          ,where:{token:window.sessionStorage.getItem('token'), certificateResult: '审核中'}
          ,page: true //开启分页
          ,toolbar: 'true'
          ,cellMinWidth: 120
          ,cols: [[ //表头
          {field: 'account', title: '机构账号', align:'center'}
          ,{field: 'organizationName', title: '机构名', align:'center'}
          ,{field: 'type', title: '机构类型', align:'center'}
          ,{field: 'introduction', title: '机构介绍', align:'center'}
          ,{field: 'tel', title: '机构电话', align:'center'}
          ,{field: 'certificateResult', title: '认证状态', align:'center'}
          ,{title: '下载认证文件', align:'center', width: 140, templet:function(d){
            return '<a class="layui-btn layui-btn-xs" href = " '+FILEPATH+d.certificateFiles+'">已上传，点击下载</a>'
          }}
          ,{field: 'certificateTime', title: '认证时间', align:'center'}
          ,{ title:'操作', align:'center', toolbar: '#handler'}

          ]]
        })

        // 监听修改按钮
        that.table.on('tool(orgAuditUpadte)', function(obj){
          var data = obj.data;
          switch(obj.event){
            case 'edit':
              layer.open({
                type: 1,
                title: '修改认证状态',
                skin: 'layui-layer-molv',
                content: that.getAuditDialog(obj.data),
                btn: ['确认', '取消'],
                yes: function (index, layero) {
                  const org = {}

                  org.id = data.id;
                  org.certificateResult = document.getElementById('certificateResult').value
                  that.updateOrgAudit(obj, org, index)
                }
              })
              break;
          }
        })
      })
    },

    // 更新机构审核状态
    updateOrgAudit(obj, org, index){
      const that = this
      layui.use('table', function(){
        var layer = layui.layer;
        if(org.certificateResult === ''){
          layer.msg('字段不能为空')
          return
        }
        that.$http.post(http+'/api/admin/updateOrgInfo', {token:window.sessionStorage.getItem('token'),id:org.id,certificateResult:org.certificateResult}).then(({data: res}) => {
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


    // 获取修改审核对话框
    getAuditDialog(org, readonly=''){
      return  `<div style="padding: 15px 50px 15px 15px"> <label for="certificateResult">认证状态：</label><select id="certificateResult">${org.certificateResult ='<option value="审核通过">审核通过</option><option value="审核未通过">审核未通过</option>'}</select></div>`
    }
  }
}
</script>
<style scoped>
  #container{
    text-align: center;
  }
</style>
