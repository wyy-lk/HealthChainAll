import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import "layui-src/dist/css/layui.css"


Vue.config.productionTip = false
axios.defaults.baseURL = 'http://127.0.0.1:8080/api'
Vue.prototype.$http = axios

window.http = 'http://localhost:3000'
window.FILEPATH = 'http://localhost:3000/api/org/audit/download?file='
window.IMAGEPATH = 'http://localhost:3000/api/org/updateOrgInfo/call?file='
import "../public/layuiadmin/style/admin.css"
import "../public/layuiadmin/style/login.css"

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
import "./assets/js/login.js"


