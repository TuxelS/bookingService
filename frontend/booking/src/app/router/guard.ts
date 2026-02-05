import { useUserStore } from '@/entities/user'
import { AUTH_SECTION_ROUTE } from '@/pages/auth'
import { BOOKING_LINK } from '@/shared/config'
import type { Router } from 'vue-router'

export const setupRouterGuard = (router: Router): void => {
    router.beforeEach((to, from, next) => {
        const { isUserAuth } = useUserStore()

        if(isUserAuth) {
            if (to.path.startsWith(AUTH_SECTION_ROUTE.path)) {
                return next({ name: BOOKING_LINK.name})
            }

            return next()
        }

        if (to.path.startsWith(AUTH_SECTION_ROUTE.path)) {
            return next()
        }

        return next({name: 'login'})
    })
}
