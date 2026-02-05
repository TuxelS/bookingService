import type { RouteLocationRaw } from 'vue-router'

const BOOKING_ROUTE_NAME = 'booking'

export const BOOKING_LINK = {
  name: BOOKING_ROUTE_NAME
} as const satisfies RouteLocationRaw