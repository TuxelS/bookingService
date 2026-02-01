import type {RouteRecordRaw} from 'vue-router'
import {LOGIN_LINK, LOGIN_ROUTE} from '../login'

const AUTH_ROUTE_NAME = 'auth'

export const AUTH_SECTION_LINKS = {
    LOGIN: LOGIN_LINK
} as const 

export const AUTH_SECTION_ROUTE = {
    path: '/auth',
    name: AUTH_ROUTE_NAME,
    children: [LOGIN_ROUTE],
    redirect: LOGIN_LINK
} as const satisfies RouteRecordRaw