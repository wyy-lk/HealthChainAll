<template>
<div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
  <legend>收集数据</legend>
</fieldset>

<div class="layui-form">
  <!-- <div class="layui-form-item" style="margin-left:-100px;">
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
  </div> -->
  <table style="display: inline-block" class="layui-hide" id="demo" lay-filter="getData"></table>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <a class="layui-btn layui-btn-sm" href="http://localhost:3000/api/org/audit/download?file=健康数据.xlsx" id="downloadHref"  lay-event="download"  style = "margin-top:10px;margin-left:30px;position:relative;float:left;">下载已授权数据</a>
  </div>
</script>
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
    layer.msg('数据获取中...', {
        icon: 16
        ,shade: 0.5
      });
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


    //执行一个 table 实例
    var that = this;
    table.render({
      elem: '#demo'
      ,id: 'reloadtable'
      ,height: 420
      ,url: http + '/api/org/getAllUsers' //数据接口
      ,method:'post'
      ,where:{token:window.sessionStorage.getItem('token')}
      ,title: '用户表'
      ,toolbar: '#toolbarDemo'
      ,page: true //开启分页
      ,cols: [[ //表头
        {type: 'checkbox', fixed: 'left'}
        ,{field: 'id', title: 'ID', width:80,align:'center', sort: true}
        ,{field: 'sex', title: '性别', width: 165,align:'center', sort: true,templet: function(d){
							if(d.sex==="0"){
								return '女';
							}else{
								return '男';
							}
						}
        }
        ,{field: 'address', title: '地址', width:165,align:'center', sort: true}
        ,{field: 'birth', title: '出生日期', width: 165,align:'center', sort: true}
        ,{field: 'sum', title: '链上数据(条)', width:150,align:'center',sort: true,templet: function(d){
            var sum = "";
            $.ajax({
              url: http+'/api/org/getUserHealthDataCount',
              type: 'POST',
              async: false,
              data: {token:window.sessionStorage.getItem('token'),contractAddr:d.contractAddr},
              success: function (info) {
                if('200' === info._code){
                  sum = info._data
                } else {
                  return "info._data.toString()"
                  layer.msg(info._msg)
                }
              }
            });
            return sum;
          }
        }
        ,{field: 'paid', title: '操作', width: 200, sort: true, align:'center',templet: function(d){
            var paid = "";
            $.ajax({
              url: http+'/api/org/authFromUser',
              type: 'POST',
              async:false,
              data: {token:window.sessionStorage.getItem('token'),contractAddr:d.contractAddr},
              success: function (info) {

                if('200' === info._code){
                  if(info._data==="0"){
                    paid = '<a class="layui-btn layui-btn-xs"  lay-event="edit">获取授权</a>';
                  }else{
                    paid = '<span class="layui-btn layui-btn-primary layui-btn-xs">已授权</span>';
                  }
                } else {
                  layer.msg(info._msg)
                }
              }
          });
          return paid;

          }
        }
      ]]
    });
    that.table.on('tool(getData)', function(obj){
      var data = obj.data;
      switch(obj.event){
        case 'edit':
          layer.open({
            type: 1,
            title: '授权转账',
            skin: 'layui-layer-molv',
            content: "<span style='margin:30px;line-height:4;'>获取该用户授权？</span>",
            btn: ['确认', '取消'],
            yes: function (index, layero) {
              const service = {}
              service.id =data.id;
              service.ethAddress = data.ethAddress;
              service.contractAddr = data.contractAddr;
              $.ajax({
                url: http+'/api/org/getUserAuth',
                type: 'POST',
                async:false,
                data: {token:window.sessionStorage.getItem('token'),id:service.id,ethAddress:service.ethAddress,contractAddr:service.contractAddr},
                success: function (info) {
                  if('200' === info._code){
                    layer.close(index);
                    layer.msg(info._msg)
                    // setTimeout(function () {
                    //   location.reload()
                    // }, 800);
                  } else {
                    layer.close(index);
                    layer.msg(info._msg)
                  }
                }
              });
            }
          })
          break;
      }
    })//table结束

    layer.closeAll('loading');
    });

    // $("#downloadHref").attr('href', '健康数据.xlsx')
  }
}

</script>

