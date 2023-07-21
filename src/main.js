import { createApp } from 'vue'
import App from './App.vue'
import VForm3 from 'vform3-builds'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/router'
import JsonViewer from 'vue-json-viewer'
import 'highlight.js/styles/stackoverflow-light.css'
import hljs from 'highlight.js/lib/core';
import javascript from 'highlight.js/lib/languages/javascript';
import hljsVuePlugin from "@highlightjs/vue-plugin";
import {createPinia } from "pinia"

hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('C++', javascript);
hljs.registerLanguage('C', javascript);
hljs.registerLanguage('Rust', javascript);
hljs.registerLanguage('Go', javascript);
hljs.registerLanguage('Java', javascript);
const app = createApp(App);
const pinia = createPinia();
app.use(ElementPlus)
app.use(VForm3)
app.use(router)
app.use(JsonViewer)
app.use(hljsVuePlugin)
app.use(pinia)
app.mount('#app')
