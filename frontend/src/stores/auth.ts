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
    setSession(token: string, user: User) {
      this.token = token
      this.user = user
      localStorage.setItem('hs_token', token)
      localStorage.setItem('hs_user', JSON.stringify(user))
    },
    async login(username: string, password: string) {
      const result = await api.login({ username, password })
      this.setSession(result.token, result.user)
    },
    async register(username: string, password: string, nickname: string) {
      await api.register({ username, password, nickname })
      await this.login(username, password)
    },
    async refreshCurrentUser() {
      if (!this.token) {
        return false
      }
      try {
        const user = await api.me()
        this.user = user
        localStorage.setItem('hs_user', JSON.stringify(user))
        return true
      } catch {
        this.logout()
        return false
      }
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('hs_token')
      localStorage.removeItem('hs_user')
    }
  }
})
