import React, { useContext } from 'react';
import { Context } from '../index';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import { ADMIN_ROUTE, BASKET_ROUTE, LOGIN_ROUTE, SHOP_ROUTE, ORDERS_ROUTE, PROFILE_ROUTE } from '../utils/consts';
import { observer } from 'mobx-react-lite';
import Container from 'react-bootstrap/Container';
import { FaUser } from 'react-icons/fa'; // Импортируем иконку силуэта человека

const NavBar = observer((props) => {
    const { user } = useContext(Context);
    const navigate = useNavigate();
    const logOut = () => {
        user.setUser({});
        user.setIsAuth(false);
    };
    return (
        <Navbar bg="dark" variant="dark">
            <Container>
                <NavLink style={{ color: 'pink' }} to={SHOP_ROUTE}>AvtOzap</NavLink>
                {user.isAuth ?
                    <Nav className="ms-auto" style={{ color: 'pink' }}>
                        <Button variant={"outline-light"} onClick={() => navigate(PROFILE_ROUTE)} className="me-2">
                            <FaUser />
                        </Button>
                        <Button variant={"outline-light"} onClick={() => navigate(BASKET_ROUTE)} className="me-2">Корзина</Button>
                        <Button variant={"outline-light"} onClick={() => navigate(ORDERS_ROUTE)} className="me-2">Заказы</Button>
                        <Button variant={"outline-light"} onClick={() => navigate(ADMIN_ROUTE)} className="me-2">Админ Панель</Button>
                        <Button variant={"outline-light"} onClick={() => { logOut(); navigate(LOGIN_ROUTE); }}>Выйти</Button>
                    </Nav>
                    :
                    <Nav className="ms-auto" style={{ color: 'pink' }}>
                        <Button variant={"outline-light"} onClick={() => navigate(LOGIN_ROUTE)}>Авторизация</Button>
                    </Nav>
                }
            </Container>
        </Navbar>
    );
});

export default NavBar;
