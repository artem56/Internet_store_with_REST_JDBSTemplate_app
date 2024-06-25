import React, { useContext, useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { observer } from 'mobx-react-lite';
import { Context } from '../index';
import { getCartItems, removeFromCart, updateCartItem, createOrder, clearCart } from '../http/cartAPI';

const Cart = observer(() => {
    const { user } = useContext(Context);
    const [cartItems, setCartItems] = useState([]);

    useEffect(() => {
        if (user.isAuth) {
            getCartItems(user.user.userId).then(data => {
                console.log(data); // Для отладки
                setCartItems(data);
            });
        }
    }, [user]);

    const handleRemove = (deviceId) => {
        removeFromCart(user.user.userId, deviceId).then(() => {
            setCartItems(cartItems.filter(item => item.deviceId !== deviceId));
        });
    };

    const handleDecrease = (deviceId) => {
        const item = cartItems.find(item => item.deviceId === deviceId);
        if (item.quantity > 1) {
            updateCartItem(user.user.userId, deviceId, item.quantity - 1).then(() => {
                setCartItems(cartItems.map(item =>
                    item.deviceId === deviceId ? { ...item, quantity: item.quantity - 1 } : item
                ));
            });
        }
    };

    const handleIncrease = (deviceId) => {
        const item = cartItems.find(item => item.deviceId === deviceId);
        updateCartItem(user.user.userId, deviceId, item.quantity + 1).then(() => {
            setCartItems(cartItems.map(item =>
                item.deviceId === deviceId ? { ...item, quantity: item.quantity + 1 } : item
            ));
        });
    };

    const handleCheckout = () => {
        createOrder(user.user.userId, cartItems).then(() => {
            alert('Заказ создан!');
            clearCart(user.user.userId).then(() => {
                setCartItems([]); // Очистить корзину после создания заказа
            });
        }).catch(error => {
            alert('Ошибка при создании заказа');
            console.error(error);
        });
    };

    const parseDeviceInfo = (deviceInfo) => {
        try {
            const info = JSON.parse(deviceInfo);
            return (
                <ul>
                    {info.map((item, index) => (
                        <li key={index}>
                            <strong>{item.title}</strong>: {item.description}
                        </li>
                    ))}
                </ul>
            );
        } catch (e) {
            console.error("Failed to parse device info", e);
            return <p>{deviceInfo}</p>;
        }
    };

    return (
        <Container>
            <Row className="mt-2">
                <Col md={9}>
                    <h2>Корзина</h2>
                    {cartItems.length > 0 ? (
                        cartItems.map(item => (
                            <Card key={item.id} className="mb-3">
                                <Card.Body>
                                    <Row>
                                        <Col md={8}>
                                            <h5>{item.deviceName}</h5>
                                            {parseDeviceInfo(item.deviceInfo)}
                                        </Col>
                                        <Col md={4} className="d-flex align-items-center justify-content-end">
                                            <Button
                                                variant="outline-secondary"
                                                onClick={() => handleIncrease(item.deviceId)}
                                                className="me-2"
                                            >
                                                +
                                            </Button>
                                            <p className="m-0">Количество: {item.quantity}</p>
                                            <Button
                                                variant="outline-secondary"
                                                onClick={() => handleDecrease(item.deviceId)}
                                                className="ms-2"
                                            >
                                                -
                                            </Button>
                                            <Button
                                                variant="danger"
                                                onClick={() => handleRemove(item.deviceId)}
                                                className="ms-4"
                                            >
                                                Удалить
                                            </Button>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                        ))
                    ) : (
                        <h5>Корзина пуста</h5>
                    )}
                </Col>
            </Row>
            {cartItems.length > 0 && (
                <Row className="mt-4">
                    <Col className="d-flex justify-content-end">
                        <Button variant="success" onClick={handleCheckout}>Оплатить</Button>
                    </Col>
                </Row>
            )}
        </Container>
    );
});

export default Cart;
