<!-- 钱包 -->
<template>
  <div>

    <!-- 个人信息 -->


    <div class="layui-form layui-card-header layuiadmin-card-header-auto">
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">账户地址:</label>
          <div class="layui-input-block">
            <input type="text" name="loginname" placeholder="请输入" autocomplete="off" class="layui-input borderHidden" v-model="ethAddress" readonly="readonly">
          </div>
        </div>
      </div>
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">账户余额:</label>
          <div class="layui-input-block">
            <input type="text" name="loginname" placeholder="" autocomplete="off" class="layui-input borderHidden" v-model="balance" readonly="readonly">
          </div>
        </div>
      </div>
    </div>

    <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        <div class="layui-form-item">
          <div class="layui-inline">
            <label class="layui-form-label">收款方账号:</label>
            <div class="layui-input-block">
              <input type="text" name="loginname" placeholder="请输入" autocomplete="off" class="layui-input" v-model="recieveAddress">
            </div>
          </div>

          <div class="layui-inline">
            <label class="layui-form-label">转账金额:</label>
            <div class="layui-input-block">
              <input type="text" name="telphone" placeholder="请输入" autocomplete="off" class="layui-input" v-model="transactEth">
            </div>
          </div>

          <div class="layui-inline">
            <label class="layui-form-label">转账备注:</label>
            <div class="layui-input-block">
              <input type="text" name="email" placeholder="请输入" autocomplete="off" class="layui-input" v-model="transactRemarks">
            </div>
          </div>
        </div>
    </div>

    <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        <div class="layui-form-item">
          <div class="layui-inline">
            <button class="layui-btn layuiadmin-btn-admin transBtn" @click="transfer" lay-submit lay-filter="LAY-user-back-search">
              转账
            </button>
          </div>
        </div>
      </div>


    <!-- 所有交易记录 -->
    <table class="layui-hide" id="test"></table>
  </div>


</template>
<style scoped>
.borderHidden{
  background-color: rgba(255,255,255,0);
  width: 500px;
  display:inline-block;
  word-wrap:break-word;
  white-space:normal;
  border:0px;

}
.transBtn{
  width: 300px;
}
</style>
<script>
export default {

  data(){
    return {
        // 页面加载时的数据
        ethAddress: "",
        balance: "",
        transactionRecordList: {},
        // 转账需要的数据
        sendAddress: "",
        recieveAddress: "",
        transactEth: "",
        transactRemarks: ""
    }
  },
 methods: {
  initDta(vueObj){
      const that = this;
      this.$http.post(http+"/api/org/getMyInfo", {token:window.sessionStorage.getItem('token')}).then(function({data: res}){
        if("200" === res._code){

          vueObj.ethAddress = res._data.ethAddress
          that.$http.post(http+"/api/org/getBalance", {token:window.sessionStorage.getItem('token'),ethAddress:res._data.ethAddress}).then(function({data: res_}){
            // layui.use(['layer'], function(){layui.layer.msg('数据加载中...')})
            vueObj.balance = res_._data.balance
          })

          vueObj.transactionRecordList = res._data.transactionRecordList

          // 将列表数据显示在表格中
          // layui.use('table', function(){
          //   var table = layui.table;
          //   table.render({
          //     elem: '#test'
          //     // , url: "" // url 访问 返回值是  {"code": 0,"msg": "","count": 100, "data": []}
          //     ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
          //     ,response: {
          //       statusCode: 200
          //     }
          //     ,data: res._data.transactionRecordList.data     // 列表[]
          //     ,cols: [[
          //       {field:'id',  title: 'id', sort: true}
          //       ,{field:'sendAddress', title: '付款方地址'}
          //       ,{field:'recieveAddress', title: '收款方地址'}
          //       ,{field:'transactEth',  title: '交易金额'}
          //       ,{field:'transactTime', title: '交易时间'}
          //       ,{field:'transactAddr', title: '交易地址'}
          //       ,{field:'transactRemarks', title: '备注'}
          //     ]]
          //   });
          // });


        }else {
          layui.use(['layer'], function(){layui.layer.msg('数据加载失败...')})
        }

      })

   },

   transfer(){
      const that = this;
      if (this.recieveAddress==""||this.transactEth==""||this.transactRemarks=="") {
        return
      }else{
        var json_tranfer = {
          token:window.sessionStorage.getItem('token'),
          receiverEthAddr:this.recieveAddress,
          value:this.transactEth,
          transactRemarks:this.transactRemarks
        }
        layui.use(['layer'], function(){layer.load(2);})
        this.$http.post(http+"/api/org/transfer", json_tranfer).then(function({data: res}){
          if("200" === res._code){
            that.sendAddress = that.ethAddress  // 付款方是本人
            layui.use(['layer'], function(){layui.layer.msg(res._msg)})
            setTimeout(function () {
              location.reload()
            }, 800);
          }else {
            layui.use(['layer'], function(){layui.layer.msg(res._msg)})
          }
        })
      }
  },
 },


 mounted: function() {
   this.initDta(this);
   layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
            var laydate = layui.laydate //日期
            ,laypage = layui.laypage //分页
            ,layer = layui.layer //弹层
            ,table = layui.table //表格
            ,$ = layui.$

            table.render({
              elem: '#test'
              // , url: "" // url 访问 返回值是  {"code": 0,"msg": "","count": 100, "data": []}
              ,url: http + "/api/org/getTransferHistory"
              ,method:'post'
              ,where:{token: window.sessionStorage.getItem('token')}
              ,page: true //开启分页
              ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
              ,cols: [[
                {field:'id',  title: 'id', sort: true}
                ,{field:'sendAddress', title: '付款方地址'}
                ,{field:'recieveAddress', title: '收款方地址'}
                ,{field:'transactEth',  title: '交易金额'}
                ,{field:'transactTime', title: '交易时间'}
                ,{field:'transactRemarks', title: '备注'}
              ]]
            });
          });
 }
}
</script>
