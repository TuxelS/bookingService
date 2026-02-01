interface LoginData {
    token: string
}

import {http} from '@/shared/api'

export const login = async (payload: {
    email: string, 
    password: string
}): Promise<LoginData | null> => {
    const {data, status} = await http.fetchFull<LoginData>({
        url: '/auth/login',
        method: 'POST',
        data: payload
    })
    
    return status === 200 && data !== null ? data : null
}