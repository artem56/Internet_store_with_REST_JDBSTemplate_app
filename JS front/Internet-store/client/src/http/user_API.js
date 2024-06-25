import { $authHost, $host } from "./index";
import jwt_decode from 'jwt-decode';

export const registration = async (email, password) => {
    const { data } = await $host.post('api/user/registration', { email, password, role: 'ADMIN' });
    localStorage.setItem('jwt', data.jwt);
    return jwt_decode(data.jwt);
};

export const login = async (email, password) => {
    const { data } = await $host.post('api/user/login', { email, password });
    localStorage.setItem('jwt', data.jwt);
    return jwt_decode(data.jwt);
};

export const check = async () => {
    const { data } = await $authHost.get('api/user/auth');
    localStorage.setItem('jwt', data.jwt);
    return jwt_decode(data.jwt);
};

export const fetchUserData = async (userId) => {
    const { data } = await $authHost.get('api/user/data', {
        params: {
            userId: userId
        }
    });
    return data;
};

export const updateUserData = async (userId, userData) => {
    const { data } = await $authHost.post('api/user/data', {
        userId: userId,
        ...userData
    });
    return data;
};
