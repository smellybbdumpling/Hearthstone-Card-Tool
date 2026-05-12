export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

export interface PageResult<T> {
  total: number
  page: number
  size: number
  records: T[]
}

export interface Card {
  id: number
  cardId: string
  nameCn: string
  nameEn?: string
  cardSet: string
  cardClass: string
  rarity?: string
  cardType?: string
  spellSchool?: string
  cost?: number
  attack?: number
  health?: number
  durability?: number
  description?: string
  flavorText?: string
  artist?: string
  imageUrl?: string
  standardLegal: boolean
}

export interface User {
  id: number
  username: string
  nickname: string
}

export interface LoginResult {
  token: string
  user: User
}
