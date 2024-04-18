import React ,{ useContext } from 'react';
import { Context } from '../index';
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import Button from 'react-bootstrap/Button'
import { useNavigate } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import { ADMIN_ROUTE, BASKET_ROUTE, LOGIN_ROUTE, SHOP_ROUTE } from '../utils/consts';
import {observer} from 'mobx-react-lite'
import Container from 'react-bootstrap/Container'
const NavBar = observer((props) => {
    const { user } = useContext(Context)
    const Navigate = useNavigate()
    const logOut = () => {
        user.SetUser({})
        user.setIsAuth(false)
    }
    return (
        <Navbar bg="dark" variant="dark">
            <Container>
                <NavLink style={{ color: 'pink' }} to={SHOP_ROUTE}>AvtOzap</NavLink>
                {user.isAuth ?
                    <Nav className="ms-auto" style={{ color: 'pink' }}>

                        <Button variant={"outline-light"} onClick={() => { logOut(); Navigate(LOGIN_ROUTE) }}>Выйти</Button>
                        <Button variant={"outline-light"} onClick={()=>Navigate(BASKET_ROUTE)} className="ms-2">Корзина</Button>
                        <Button variant={"outline-light"} onClick={()=>Navigate(ADMIN_ROUTE)} className="ms-2">Админ Панель</Button>

                    </Nav>
                    :
                    <Nav className="ms-auto" style={{ color: 'pink' }}>

                        <Button variant={"outline-light"} onClick={() => user.setIsAuth(true)}>Авторизация</Button>

                    </Nav>
                }
            </Container>
           
        </Navbar>
    );
})

export default NavBar;