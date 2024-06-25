import React, { useContext, useState } from 'react';
import { Container, Form, Card, Button, Alert } from 'react-bootstrap';
import { NavLink, useLocation, useNavigate } from 'react-router-dom';
import { registration, login } from '../http/user_API';
import { observer } from 'mobx-react-lite';
import { LOGIN_ROUTE, REGISTRATION_ROUTE, SHOP_ROUTE } from '../utils/consts';
import { Context } from '../index';

const Auth = observer(() => {
    const navigate = useNavigate();
    const location = useLocation();
    const isLogin = location.pathname === LOGIN_ROUTE;
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const { user } = useContext(Context);

    const click = async () => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailRegex.test(email)) {
            try {
                let data;
                if (isLogin) {
                    data = await login(email, password);
                } else {
                    data = await registration(email, password);
                }
                user.setUser(data);  // Исправлено на setUser
                user.setIsAuth(true);
                navigate(SHOP_ROUTE);
            } catch (error) {
                if (error.response && error.response.status === 400) {
                    setError("Пользователя с таким email не существует");
                } else {
                    setError("Произошла ошибка при обработке запроса");
                }
            }
        } else {
            setError("Email неверный");
        }
    }

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ height: window.innerHeight - 54 }}>
            <Card style={{ width: 600 }} className="p-5">
                <h2 className="m-auto">{isLogin ? 'Авторизация' : 'Регистрация'}</h2>
                <Form className="d-flex flex-column">
                    <Form.Control
                        className="mt-4"
                        placeholder="Введите ваш email..."
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <Form.Control
                        className="mt-4"
                        placeholder="Введите ваш пароль..."
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        type="password"
                    />
                    {error && <Alert variant="danger">{error}</Alert>}
                    <div className="d-flex justify-content-between mt-3 pl-3 pr-3">
                        {isLogin ? (
                            <div>
                                Нет аккаунта? <NavLink to={REGISTRATION_ROUTE}>Зарегистрируйтесь</NavLink>
                            </div>
                        ) : (
                            <div>
                                Есть аккаунт? <NavLink to={LOGIN_ROUTE}>Войти</NavLink>
                            </div>
                        )}
                        <Button variant="outline-success" className="btn-outline-success" onClick={click}>
                            {isLogin ? 'Войти' : 'Зарегистрироваться'}
                        </Button>
                    </div>
                </Form>
            </Card>
        </Container>
    );
})

export default Auth;
