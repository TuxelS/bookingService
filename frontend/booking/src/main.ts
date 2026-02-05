
import { createPinia } from 'pinia'
import { createApp } from 'vue'
import { router } from './app/router'
import App from './app'

const app = createApp(App)
    .use(createPinia())
    .use(router)
    

app.mount('#app')