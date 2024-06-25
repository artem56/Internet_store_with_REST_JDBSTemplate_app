import React, { createContext } from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import DeviceStore from './store/DeviceStore';
import UserStore from './store/UserStore';

export const Context = createContext(null);

const userStore = new UserStore();
userStore.loadUserFromToken(); // Загружаем пользователя из токена при инициализации

console.log(process.env.REACT_APP_API_URL);

ReactDOM.render(
    <Context.Provider value={{
        user: userStore,
        device: new DeviceStore()
    }}>
        <App />
    </Context.Provider>,
    document.getElementById('root')
);
