<template>
  <div class="layui-form-item">
    <table class="layui-hide" id="demo" lay-filter="test"></table>
    <script type="text/html" id="barDemo">
    <!--
      判断审核状态，0:审核中，1:通过，2:拒绝。
      只要>0都表示已经审核，操作栏只显示删除
    -->
      <!-- <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="pass">通过</a>
      <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="refuse">拒绝</a> -->
      <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
  </div>
</template>

<script>
export default{
  mounted(){
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
      ,$ = layui.$
     

      //执行一个 table 实例
      table.render({
        elem: '#demo'
        ,height: 420
        ,title: '所有服务列表'
        ,url:http + '/api/admin/findMedicalServiceList'
        ,method:'post'
        ,where:{token: window.sessionStorage.getItem('token'), serviceName: '', auditResult: ''}
        ,page: true //开启分页
        ,cols: [[ //表头
           {field: 'id', title: 'ID', unresize:'false',sort: true, align:'center'}
          ,{field: 'serviceName', title: '服务名', align:'center'}
          ,{field: 'serviceDetails', title: '服务介绍', align:'center'}
          ,{field: 'cost', title: '服务花费', align:'center'}
          ,{field: 'oid', title: '所属机构', align:'center'}
          ,{field: 'auditResult', title: '审核结果', align:'center'}
          ,{fixed: 'right', title:'操作', align:'center', toolbar: '#barDemo'}
        ]]
      });
        //监听行工具事件
      table.on('tool(test)', function(obj){
        var data = obj.data
        ,layEvent = obj.event; //获得 lay-event 对应的值
        //console.log(obj)
        if(layEvent === 'del'){
          layer.confirm('真的删除行么', function(index){
            obj.del(); //删除对应行（tr）的DOM结构
            layer.close(index);
            //向服务端发送删除指令
            that.$http.post(http + "/api/admin/deleteService", {token: window.sessionStorage.getItem('token'), id: data.id} ).then(function({data: res}){
              console.log(res._msg);
            })

          });
        }
      });
    });
  }
}

</script>>
