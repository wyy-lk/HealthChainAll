<template>
<div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
  <legend>收集数据</legend>
</fieldset>

<div class="layui-form" >
  <div class="layui-form-item" style="margin-left:-100px;">
    <div class="layui-inline" >
      <label class="layui-form-label" style="width:70px;">选择时间</label>
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="timeRange" placeholder=" - " style="width:300px;">
      </div>
    </div>

  </div>
  <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
        <label class="layui-form-label" style="width:70px;">输入地区</label>
        <div class="layui-input-inline">
        <input type="text" class="layui-input" id="region" placeholder="苏州 杭州" style="width:300px;">
      </div>
    </div>
  </div>
  <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
        <label class="layui-form-label" style="width:70px;">输入人数</label>
        <div class="layui-input-inline">
        <input type="number" class="layui-input" id="numberPerson" placeholder="1000人" style="width:300px;">
      </div>
    </div>
  </div>
  <div class="layui-form-item" style="margin-left:-100px;">
      <div class="layui-inline" >
        <label class="layui-form-label" style="width:70px;">输入条数</label>
        <div class="layui-input-inline">
        <input type="number" class="layui-input" id="numberRecord" placeholder="10000条" style="width:300px;">
      </div>
    </div>
  </div>
   <div class="layui-form-item">
    <div class="layui-input-block">
      <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1" style="margin-left:-18px;width:300px;">确认收集</button>

    </div>
  </div>
</div>
</div>
</template>
<script>
export default{
  mounted(){
    layui.use(['laydate', 'laypage', 'layer', 'table'], function(){
    var laydate = layui.laydate //日期
    ,$ = layui.$
    ,laypage = layui.laypage //分页
    ,layer = layui.layer //弹层
    ,table = layui.table //表格
console.log($)
    //范围时间选择控件
    laydate.render({
      elem: '#timeRange'
      ,type: 'datetime'
      ,range: true
    });
    //点击时间控件的提示
    $(document).on('click','#timeRange',function(){
        layer.tips('开始时间和结束时间', '#timeRange');
    });
    //点击地区输入框的提示
    $(document).on('click','#region',function(){
        layer.tips('收集多个地区请用空格间隔', '#region');
    });


    // //执行一个 table 实例
    // table.render({
    //   elem: '#demo'
    //   ,height: 420
    //   ,url: '/layuiadmin/json/table/user.js' //数据接口
    //   ,title: '用户表'
    //   ,page: true //开启分页
    //   ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
    //   ,totalRow: true //开启合计行
    //   ,cols: [[ //表头
    //     {type: 'checkbox', fixed: 'left'}
    //     ,{field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', totalRowText: '合计：'}
    //     ,{field: 'username', title: '用户名', width:80}
    //     ,{field: 'experience', title: '积分', width: 90, sort: true, totalRow: true}
    //     ,{field: 'sex', title: '性别', width:80, sort: true}
    //     ,{field: 'score', title: '评分', width: 80, sort: true, totalRow: true}
    //     ,{field: 'city', title: '城市', width:150}
    //     ,{field: 'sign', title: '签名', width: 200}
    //     ,{field: 'classify', title: '职业', width: 100}
    //     ,{field: 'wealth', title: '财富', width: 135, sort: true, totalRow: true}
    //     ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
    //     ,{fixed: '',title:'操作',width: 165, align:'center', toolbar: '#'}
    //   ]]
    // });


});
  }
}

</script>
