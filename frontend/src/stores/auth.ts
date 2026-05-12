import { defineStore } from 'pinia'
import { api } from '@/api'
import type { User } from '@/api/types'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('hs_token') || '',
    user: JSON.parse(localStorage.getItem('hs_user') || 'null') as User | null
  }),
  getters: {
    isLoggedIn: (state) => Boolean(state.token && state.user)
  },
  actions: {
    async login(username: string, password: string) {
      const result = await api.login({ username, password })
      this.token = result.token
      this.user = result.user
      localStorage.setItem('hs_token', result.token)
      localStorage.setItem('hs_user', JSON.stringify(result.user))
    },
    async register(username: string, password: string, nickname: string) {
      await api.register({ username, password, nickname })
      await this.login(username, password)
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('hs_token')
      localStorage.removeItem('hs_user')
    }
  }
})
