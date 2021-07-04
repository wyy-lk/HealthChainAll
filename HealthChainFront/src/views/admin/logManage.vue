<template>
<div>
<table class="layui-hide" id="demo" lay-filter="test"></table>
<!-- <script type="text/html" id="operate-barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script> -->
</div>
</template>
<script>
export default {
  mounted(){
    layui.use(['laydate', 'laypage', 'layer', 'table'], function(){
      var laydate = layui.laydate //日期
      ,$ = layui.$
      ,laypage = layui.laypage //分页
      ,layer = layui.layer //弹层
      ,table = layui.table //表格

      //执行一个 table 实例
      table.render({
        elem: '#demo'
        ,height: 420
        ,url: http + '/api/admin/getLogList' //数据接口
        ,title: '用户表'
        ,method:'post'
        ,where:{token:window.sessionStorage.getItem('token')}
        ,page: true //开启分页
        ,cellMinWidth: 120
        ,cols: [[ //表头
          {field: 'id', title: 'ID', sort: true, fixed: 'left',align:'center'}
          ,{field: 'operateId', title: '操作人编号',align:'center'}
          ,{field: 'operateDetails', title: '操作详情', sort: true, totalRow: true,align:'center'}
          ,{field: 'operateResult', title: '操作结果', sort: true,align:'center'}
          ,{field: 'operateTime', title: '操作时间', sort: true, totalRow: true,align:'center'}
          // ,{fixed: 'right', align:'center', title:'操作',toolbar: '#operate-barDemo', width:'17%'}
        ]]
      });


    });
  }
}
</script>
