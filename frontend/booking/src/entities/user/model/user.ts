import { useStorage } from "@vueuse/core"
import { defineStore } from "pinia"
import { computed, type Ref } from "vue"


interface User {
    email: string
    token: string
}

interface UserStore {
    isUserAuth: Ref<boolean>
    setUser: (userData: User | null) => void
    getUserEmail: () => string
    getUserToken: () => string
}

export const useUserStore = defineStore('useUserStore', (): UserStore => {
    const user = useStorage<User | null>('user', null, localStorage, {
        serializer: {
            read(raw) {
                return raw ? JSON.parse(raw) : null
            },
            write(value) {
                return value ? JSON.stringify(value) : ''
            }
        }
    })
    const isUserAuth: UserStore['isUserAuth'] = computed(() => !!user.value)
    //const isUserAuth: UserStore['isUserAuth'] = computed(() => true)
    const setUser: UserStore['setUser'] = (userData) => {
        user.value = userData
    }
    const getUserEmail: UserStore['getUserEmail'] = () => {
        if (user.value === null) throw new Error('Логическая ошибка. Пользователь не авторизован')
        return user.value.email 
    }
    const getUserToken: UserStore['getUserToken'] = () => {
        if (user.value === null) throw new Error('Логическая ошибка. Пользователь не авторизован')
        return user.value.token
    } 

    return {isUserAuth, setUser, getUserEmail, getUserToken}
})