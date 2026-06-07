import axios from 'axios'
import type { ApiResponse } from './types'

export const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('hs_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  (response) => {
    const body = response.data as ApiResponse<unknown>
    if (body.code !== 0) {
      return Promise.reject(new Error(body.message || '请求失败'))
    }
    return response
  },
  (error) => {
    const status = error.response?.status
    if (status === 401 || status === 403) {
      localStorage.removeItem('hs_token')
      localStorage.removeItem('hs_user')
      if (window.location.pathname !== '/login') {
        window.location.href = '/login'
      }
      return Promise.reject(new Error('登录已失效，请重新登录'))
    }
    const body = error.response?.data as Partial<ApiResponse<unknown>> | undefined
    const message = body?.message || error.message || '请求失败'
    return Promise.reject(new Error(message))
  }
)
