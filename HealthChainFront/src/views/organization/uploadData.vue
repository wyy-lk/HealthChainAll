<template>
<div id="container">
  <div>
    <h2>
      上传历史
      <div style="float: right; display: inline;">
        <button type="button" class="layui-btn" style="position: relative; bottom: 15px; right: 25px" @click="showUploadDialog">
          <i class="layui-icon">&#xe608;</i> 上传
        </button>
    </div>
    </h2>
  </div>
  <div>
    <div class="layui-inline">
      <table style="display: inline-block" class="layui-hide" id="uploadHistoryTable" lay-filter="uploadHistory"></table>
    </div>
  </div>
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
    this.$http.post('/third/findHealthDataHistory.json', {}).then(({data: res}) => {this.initTable(res)})
  },
  methods: {
    initTable(res){
      const that = this
      layui.use('table', function(){
        that.table = layui.table
        // 获取数据
        that.table.render({
          id: 'listReload'
          ,elem: '#uploadHistoryTable'
          ,height: 420
          ,title: '上传历史表'
          ,page: true //开启分页
          ,defalutToolbar: []
          ,response: {
            statusCode: 200 //规定成功的状态码，默认：0
          }
          ,data: res._data
          ,cols: [[ //表头
            {field: 'no', title: '序号', width:140, align: 'center'}
            ,{field: 'distance', title: '步行距离', width:140, align: 'center'}
            ,{field: 'heat', title: '热量消耗', width: 140, align: 'center'}
            ,{field: 'sleepQuality', title: '睡眠质量', width:140, align: 'center'}
            ,{field: 'heartRate', title: '心率', width: 140, align: 'center'}
            ,{field: 'permitVisit', title: '是否公开', width: 140, align: 'center'}
            ,{field: 'uploadTime', title: '上传时间', width: 140, align: 'center'}
          ]]
        })
      })
    },

    // 显示上传数据对话框
    showUploadDialog(){
      const that = this
      layer.open({
        type: 1,
        title: '上传数据',
        skin: 'layui-layer-molv',
        content: `<div style="padding: 15px 50px 15px 15px"><label for="distance">步行距离：</label><input id="distance"/><br><br>
                  <label for="heat">热量消耗：</label><input id="heat"/><br><br>
                  <label for="sleepQuality">睡眠质量：</label><input id="sleepQuality"/><br><br>
                  <label for="heartRate">心率：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="heartRate"/><br><br>
                  <label for="permitVisit">是否公开：</label><input id="permitVisit" type="checkbox" checked/></div>`,
        btn: ['上传', '取消'],
        yes: function (index, layero) {
          const heathData = {}
          heathData.distance = document.getElementById('distance').value;
          heathData.heat = document.getElementById('heat').value;
          heathData.sleepQuality = document.getElementById('sleepQuality').value;
          heathData.heartRate = document.getElementById('heartRate').value;
          heathData.permitVisit = document.getElementById('permitVisit').value;
          that.upload(heathData, index)
        }
      })
    },

    upload(healthData, index){
      if(healthData.distance === '' || healthData.heat === '' || healthData.sleepQuality === '' || healthData.heartRate === '' || healthData.permitVisit === ''){
        layer.msg('字段不能为空')
        return
      }
      this.$http.get('/third/uploadHealthData.json', {}).then(({data: res}) => {
        layer.close(index)
        layer.msg('上传成功')
        if('200' === res._code){
          this.$http.get('/third/findHealthDataHistory.json', {}).then(({data: res}) => {
            this.table.reload("listReload", {data: res._data})
          })
        } else {
          layer.close(index);
          layer.msg('上传失败')
        }
      })
    }
  }
}

</script>
<style scoped>
  #container{
    padding: 30px 55px 5px 55px;
  }
  h2{
    width: 100%;
    text-align: left;
    font-weight: bolder;
    border-bottom: 2px solid lightgray;
  }
</style>
