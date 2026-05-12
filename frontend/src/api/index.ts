import { request } from './request'
import type { ApiResponse, Card, LoginResult, PageResult, User } from './types'

export interface CardQuery {
  keyword?: string
  cardSet?: string
  cardClass?: string
  rarity?: string
  cardType?: string
  cost?: number
  page?: number
  size?: number
  _ts?: number
}

export const api = {
  async register(payload: { username: string; password: string; nickname: string }) {
    const { data } = await request.post<ApiResponse<User>>('/auth/register', payload)
    return data.data
  },
  async login(payload: { username: string; password: string }) {
    const { data } = await request.post<ApiResponse<LoginResult>>('/auth/login', payload)
    return data.data
  },
  async me() {
    const { data } = await request.get<ApiResponse<User>>('/auth/me')
    return data.data
  },
  async cards(params: CardQuery) {
    const { data } = await request.get<ApiResponse<PageResult<Card>>>('/cards', { params })
    return data.data
  },
  async card(id: number) {
    const { data } = await request.get<ApiResponse<Card>>(`/cards/${id}`)
    return data.data
  },
  async favorite(cardId: number) {
    await request.post(`/favorites/${cardId}`)
  },
  async unfavorite(cardId: number) {
    await request.delete(`/favorites/${cardId}`)
  },
  async favorites(params: { page: number; size: number }) {
    const { data } = await request.get<ApiResponse<PageResult<Card>>>('/favorites', { params })
    return data.data
  },
  async favoriteStatus(cardId: number) {
    const { data } = await request.get<ApiResponse<{ favorite: boolean }>>(`/favorites/${cardId}/status`)
    return data.data.favorite
  }
}
