import { makeAutoObservable } from 'mobx';
import jwt_decode from 'jwt-decode';

export default class UserStore {
    constructor() {
        this._isAuth = false;
        this._user = {};
        makeAutoObservable(this);
    }

    setIsAuth(bool) {
        this._isAuth = bool;
    }

    setUser(user) {
        this._user = user;
    }

    get isAuth() {
        return this._isAuth;
    }

    get user() {
        return this._user;
    }

    loadUserFromToken() {
        const token = localStorage.getItem('jwt');
        if (token) {
            const decodedUser = jwt_decode(token);
            this.setUser(decodedUser);
            this.setIsAuth(true);
        }
    }
}
