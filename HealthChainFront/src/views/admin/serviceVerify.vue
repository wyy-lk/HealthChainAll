<!-- 服务审核页面 -->
<template>
  <!-- 需要审核的医疗服务 -->
  <div>
    <table class="layui-hide" id="demo" lay-filter="test"></table>

    <script type="text/html" id="barDemo">
      <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="ispassed">允许通过</a>
      <!-- <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a> -->
      <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="nopassed">不允许通过</a>
    </script>
  </div>

</template>

<script>

export default {
  data(){
    return {

    }
  },
  methods: {
    initData(vueObj){
      const that = this;
          layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
            var laydate = layui.laydate //日期
            ,laypage = layui.laypage //分页
            ,layer = layui.layer //弹层
            ,table = layui.table //表格
            ,carousel = layui.carousel //轮播
            ,upload = layui.upload //上传
            ,element = layui.element //元素操作
            ,slider = layui.slider //滑块

            table.render({
              elem: '#demo'
              ,height: 420
              ,title: '服务审核表'
              ,url:http+'/api/admin/findMedicalServiceList'
              ,method:'post'
              ,where:{token: window.sessionStorage.getItem('token'), serviceName: '', auditResult: '审核中'}
              ,page: true //开启分页

              // , url: "" // url 访问 返回值是  {"code": 0,"msg": "","count": 100, "data": []}
              ,cellMinWidth: 120 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
              ,cols: [[
                {field:'id', title: 'ID', sort: true,align:'center'}
                ,{field:'serviceName', title: '服务名称',align:'center'}
                ,{field:'serviceDetails', title: '服务详情',align:'center'}
                ,{field:'cost', title: '费用',align:'center'}
                ,{field:'oid', title: '机构id',align:'center'}
                ,{field:'auditResult', title: '审核结果',align:'center'}
                ,{fixed: 'right',title:'操作', width: 180, align:'center', toolbar: '#barDemo',align:'center'}  // 工具栏
              ]]
            });

            //监听行工具事件
              table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
                if(layEvent === 'ispassed'){

                  that.$http.post(http + "/api/admin/updateMedicalServcie", {token: window.sessionStorage.getItem('token'), id: data.id,auditResult: '审核通过'}).then(function({data: res}){
                    if(res._code === "200"){
                      layer.msg('审核通过,ok');
                      location.reload();
                    }
                    else{
                      layer.msg('操作失败..');
                      location.reload();
                    }

                  })
                } else if(layEvent === 'nopassed'){

                  that.$http.post(http + "/api/admin/updateMedicalServcie", {token: window.sessionStorage.getItem('token'), id: data.id, auditResult: '审核未通过'}).then(function({data: res}){
                    if(res._code === "200"){
                      layer.msg('审核不通过..');
                      location.reload();
                    }
                    else{
                      layer.msg('操作失败..');
                      location.reload();
                    }

                  })
                }
              });

          });

    },

  },
  mounted: function() {
    this.initData(this)
  }
};
</script>
