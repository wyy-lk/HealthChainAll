<template>
<div id="container">
<h2>系统矿工信息</h2>
<table class="layui-table">
  <colgroup>
    <col width="150">
    <col width="500">
  </colgroup>
  <thead>
    <tr>
      <th>矿工属性</th>
      <th>矿工信息</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>矿工地址</td>
      <td>{{minerInfo.miner}}</td>
    </tr>
    <tr>
      <td>矿工余额</td>
      <td>{{minerInfo.balance}}</td>
    </tr>
    <tr>
      <td>当前区块高度</td>
      <td>{{minerInfo.blockNumber}}</td>
    </tr>
    <tr>
      <td>汽油费</td>
      <td>{{minerInfo.gasPrice}}</td>
    </tr>
  </tbody>
</table>
<h2>区块信息</h2>
<div class="layui-form-item" style="width: 700px; text-aligin: center; margin-left: 25px; margin-top: 5px; position: relative">
    <label class="layui-form-label">区块号：</label>
    <div class="layui-input-block">
      <input v-model="searchNumber" type="number" name="title" required min="0" :max="currentBlockHeight"  lay-verify="required" autocomplete="off" class="layui-input">
    </div>
    <button @click="getBlockInfoByNumber(searchNumber)" type="button" class="layui-btn layui-btn-radius">查询</button>
  </div>
<table class="layui-table">
  <colgroup>
    <col width="150">
    <col width="500">
  </colgroup>
  <thead>
    <tr>
      <th>区块头部</th>
      <th>区块信息</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="(value, key) in blockInfoMap" :key="key">
      <td>{{value}}</td>
      <td>{{blockInfo[key]}}</td>
    </tr>
  </tbody>
</table>
</div>
</template>

<script>
// 区块头部描述映射
const blockInfoMap = {
  "number": "区块高度",
  "size": "区块大小",
  "hash": "区块哈希值",
  "miner": "矿工地址",
  "gasUsed": "消耗的Gas量",
  "gasLimit": "最大Gas量",
  "difficulty": "挖矿难度",
  "totalDifficulty": "挖矿总难度",
  "receiptsRoot": "收据树根哈希值",
  "stateRoot": "状态树根哈希值",
  "transactionsRoot": "交易树根哈希值",
  "nonce": "随机数",
  "timestamp": "时间戳"
}

export default {
  data(){
    return {
      minerInfo: {},
      blockInfo: {},
      currentBlockHeight: 10,
      searchNumber: 1,
      blockInfoMap
    }
  },
  mounted(){
    const that = this
    this.$http.get(http + '/api/admin/getMinerInfo', {}).then(({data: res}) => {this.minerInfo = res._data; this.currentBlockHeight = parseInt(res._data.blockNumber)})
    this.$http.get(http + '/api/admin/getBlockInfo', {params: {num: 1}}).then(({data: res}) => {this.blockInfo = res._data;})

    // 设置计时器，每个12s请求一次区块信息
    setInterval(function(){
      console.log(that.currentBlockHeight)
      that.$http.get(http + '/api/admin/getMinerInfo', {}).then(({data: res}) => {that.minerInfo = res._data; that.currentBlockHeight = parseInt(res._data.blockNumber)})
    }, 12000)
  },
  methods: {
    getBlockInfoByNumber(number){
      if(number < 0 || number > this.currentBlockHeight){
        layer.msg('查询的区块不存在')
        return
      }
      // get发送请求的参数  {params: {num: 1}}
      this.$http.get(http + '/api/admin/getBlockInfo', {params: {num: number}}).then(({data: res}) => {this.blockInfo = res._data;})
    }
  }
}

</script>
<style scoped>
  #container{
    padding-top: 30px;
  }
  h2{
    margin: 0 50px;
    padding: 5px;
    text-align: left;
    font-weight: bolder;
    border-bottom: 2px solid lightgray;
  }
  input[type=number]{
    width: 120px;
    height: 30px;
    /* margin-top: 10px; */
    position: relative;
    top: 5px;
  }
  button{
    width: 100px;
    height: 30px;
    line-height: 30px;
    position: absolute;
    right: 0;
    top: 5px;
  }
  table{
    width: 700px;
    margin-left: 50px;
    margin-bottom: 50px;
  }
  td{
    text-align: left;
  }
</style>
