import { createRouter, createWebHistory } from 'vue-router'
import {AUTH_SECTION_ROUTE} from '@/pages/auth'
import {CenteredLayout} from '../layout'
import LoginView from '@/pages/auth/login/ui/LoginView.vue'
import { MAIN_ROUTE } from '@/pages/main/config'
import './guard'
import { setupRouterGuard } from './guard'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    AUTH_SECTION_ROUTE,
    {
      ...AUTH_SECTION_ROUTE,
      component: LoginView
    },
    MAIN_ROUTE
  ],
})

setupRouterGuard(router)

export default router
