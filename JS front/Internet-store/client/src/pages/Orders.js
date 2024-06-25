import React, { useContext, useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Modal, Form } from 'react-bootstrap';
import { observer } from 'mobx-react-lite';
import { Context } from '../index';
import { fetchOrders, createOrUpdateReview } from '../http/orderAPI';
import ReactStars from 'react-rating-stars-component';

const Orders = observer(() => {
    const { user } = useContext(Context);
    const [orders, setOrders] = useState([]);
    const [showReviewModal, setShowReviewModal] = useState(false);
    const [selectedDevice, setSelectedDevice] = useState(null);
    const [rating, setRating] = useState(1);
    const [comment, setComment] = useState('');

    useEffect(() => {
        if (user.isAuth) {
            fetchOrders(user.user.userId).then(data => {
                setOrders(data);
            });
        }
    }, [user]);

    const formatDate = (timestamp) => {
        const date = new Date(timestamp);
        return date.toLocaleDateString();
    };

    const calculateTotal = (items) => {
        return items.reduce((total, item) => total + item.quantity * item.price, 0);
    };

    const handleReview = (deviceId) => {
        setSelectedDevice(deviceId);
        setShowReviewModal(true);
    };

    const handleReviewSubmit = () => {
        createOrUpdateReview(user.user.userId, selectedDevice, rating, comment)
            .then(() => {
                alert('Отзыв сохранен');
                setShowReviewModal(false);
                setRating(1);
                setComment('');
            })
            .catch(error => {
                alert('Ошибка при сохранении отзыва');
                console.error(error);
            });
    };

    return (
        <Container>
            <Row className="mt-2">
                <Col md={9}>
                    <h2>Заказы</h2>
                    {orders.length > 0 ? (
                        orders.map(order => (
                            <Card key={order.orderId} className="mb-3">
                                <Card.Body>
                                    <Row>
                                        <Col md={6}>
                                            <h5>Заказ №{order.orderId}</h5>
                                            <p>Дата: {formatDate(order.orderDate)}</p>
                                            <p>Статус: {order.status}</p>
                                        </Col>
                                        <Col md={6}>
                                            <h6>Товары:</h6>
                                            <ul>
                                                {order.items.map(item => (
                                                    <li key={item.deviceId}>
                                                        {item.deviceName} - Количество: {item.quantity} - Цена: {item.price} руб.
                                                    </li>
                                                ))}
                                            </ul>
                                            <p><strong>Итого: {calculateTotal(order.items)} руб.</strong></p>
                                            <div className="d-flex justify-content-end">
                                                <Button variant="primary" onClick={() => handleReview(order.orderId)}>Оценить продукт</Button>
                                            </div>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                        ))
                    ) : (
                        <h5>Заказы отсутствуют</h5>
                    )}
                </Col>
            </Row>
            <Modal show={showReviewModal} onHide={() => setShowReviewModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Оценить продукт</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formRating">
                            <Form.Label>Рейтинг</Form.Label>
                            <ReactStars
                                count={5}
                                onChange={(newRating) => setRating(newRating)}
                                size={24}
                                activeColor="#ffd700"
                                value={rating}
                            />
                        </Form.Group>
                        <Form.Group controlId="formComment" className="mt-3">
                            <Form.Label>Комментарий</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={comment}
                                onChange={(e) => setComment(e.target.value)}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowReviewModal(false)}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={handleReviewSubmit}>
                        Сохранить
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
});

export default Orders;
