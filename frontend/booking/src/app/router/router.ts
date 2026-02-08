import { createRouter, createWebHistory } from 'vue-router'
import {AUTH_SECTION_ROUTE} from '@/pages/auth'
import LoginView from '@/pages/auth/login/ui/LoginView.vue'
import { BOOKING_ROUTE } from '@/pages/booking/config'
import './guard'
import { setupRouterGuard } from './guard'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/booking'
    },
    AUTH_SECTION_ROUTE,
    {
      ...AUTH_SECTION_ROUTE,
      component: LoginView
    },
    BOOKING_ROUTE
  ],
})

setupRouterGuard(router)

export default router
