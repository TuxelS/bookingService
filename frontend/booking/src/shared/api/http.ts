import axios, { type AxiosRequestConfig, type AxiosResponse } from "axios";

interface HttpConfig {
    baseURL: string
    defaultHeaders: Record<string, string>
}

interface HttpResponse<T> {
    data: T | null
    status: number
}

interface HttpClient {
    fetchData: <T>(config: AxiosRequestConfig) => Promise<T | null>
    isSuccess: (config: AxiosRequestConfig) => Promise<boolean>
    setToken: (token: string) => void
    clearToken: () => void
    fetchFull: <T>(config: AxiosRequestConfig) => Promise<HttpResponse<T>>
}

let bearerToken: string | null = null

const httpClient = ({baseURL, defaultHeaders}: HttpConfig ): HttpClient => {
    const DATA_NULL = null
    const ERROR_STATUS = 500
    const axiosInstant = axios.create({
        baseURL,
        headers: defaultHeaders
    })

    const request = async <T> (config: AxiosRequestConfig): Promise<HttpResponse<T>> => {
        const headers = {...config.headers} as NonNullable<typeof config.headers>
        if (bearerToken !== null) headers['Authorization'] = `Bearer ${bearerToken}`
        try {
            const {data, status} = await axiosInstant.request({
                ...config,
                headers
            })
            return {data, status}
        } catch (err: unknown) {
            if (axios.isAxiosError(err) && err.response) {
                return {
                    data: err.response.data ?? DATA_NULL,
                    status: err.response.status ?? ERROR_STATUS
                }
            }
            return {data: DATA_NULL, status: ERROR_STATUS}
        }
    }

    const fetchData = async <T>(config: AxiosRequestConfig): Promise<T | null> => {
        const {data} = await request<T>(config)
        return data
    }

    const isSuccess = async (config: AxiosRequestConfig): Promise<boolean> => {
        const {status} = await request(config)
        return status >= 200 && status < 300
    }

    const setToken = (token: string): void => {
        bearerToken = token
    }

    const fetchFull = async <T>(config: AxiosRequestConfig): Promise<HttpResponse<T>> =>
        request<T>(config)

    const clearToken = (): void => {
        bearerToken = null
    }

    return {
        fetchData,
        isSuccess,
        setToken,
        clearToken,
        fetchFull
    }
}
const baseURL: string = 'http://localhost:8080/api'
const defaultHeaders = {
    Accept: 'application/json',
    'Content-Type': 'application/json'
} as const

const http = httpClient({baseURL, defaultHeaders})

export {http}