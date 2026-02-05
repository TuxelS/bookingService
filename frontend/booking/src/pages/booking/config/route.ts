import { BOOKING_LINK } from '@/shared/config'
import type { RouteRecordRaw } from 'vue-router'

export const BOOKING_ROUTE = {
  path: '/booking',
  name: BOOKING_LINK.name,
  component: () => import('../ui')
} as const satisfies RouteRecordRaw