 import { $authHost, $host } from "./index"
import jwt_decode from 'jwt-decode'
export const registration = async (email, password) => {
    const { data } = await $host.post('api/user/registration', { email, password, role: 'ADMIN' })
    localStorage.removeItem('jwt')
    localStorage.setItem('jwt',data.jwt)
    return jwt_decode(data.jwt)
}
export const login = async (email, password) => {
    const {data} = await $host.post('api/user/login', { email, password })
    localStorage.removeItem('jwt')
    localStorage.setItem('jwt',data.jwt)
    return jwt_decode(data.jwt)
}
export const check = async () => {
    const {data} = await $authHost.get('api/user/auth')
    localStorage.removeItem('jwt')
    localStorage.setItem('jwt',data.jwt)
    return jwt_decode(data.jwt)
}