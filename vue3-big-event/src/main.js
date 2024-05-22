import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import '@/assets/main.scss'
import 'element-plus/dist/index.css'
import pinia from './stores'
import ElementPlus from 'element-plus'
import locale from 'element-plus/dist/locale/zh-cn.min.mjs'
const app = createApp(App)

app.use(pinia)
app.use(router)
app.use(ElementPlus, { locale })
app.mount('#app')
