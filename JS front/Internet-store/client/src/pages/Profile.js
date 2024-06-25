import React, { useContext, useEffect, useState } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { observer } from 'mobx-react-lite';
import { Context } from '../index';
import { fetchUserData, updateUserData } from '../http/user_API';

const Profile = observer(() => {
    const { user } = useContext(Context);
    const [userData, setUserData] = useState({ phoneNumber: '', name: '', address: '' });
    const [message, setMessage] = useState('');

    useEffect(() => {
        if (user.isAuth) {
            fetchUserData(user.user.userId).then(data => {
                setUserData(data);
            });
        }
    }, [user]);

    const handleChange = (e) => {
        setUserData({ ...userData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        updateUserData(user.user.userId, userData).then(() => {
            setMessage('Информация успешно обновлена');
        }).catch(error => {
            setMessage('Ошибка при обновлении информации');
        });
    };

    return (
        <Container>
            <h2>Личный профиль</h2>
            {message && <Alert variant="info">{message}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="formPhoneNumber">
                    <Form.Label>Телефонный номер</Form.Label>
                    <Form.Control
                        type="text"
                        name="phoneNumber"
                        value={userData.phoneNumber}
                        onChange={handleChange}
                        placeholder="Введите телефонный номер"
                    />
                </Form.Group>
                <Form.Group controlId="formName" className="mt-3">
                    <Form.Label>Имя</Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        value={userData.name}
                        onChange={handleChange}
                        placeholder="Введите имя"
                    />
                </Form.Group>
                <Form.Group controlId="formAddress" className="mt-3">
                    <Form.Label>Адрес</Form.Label>
                    <Form.Control
                        type="text"
                        name="address"
                        value={userData.address}
                        onChange={handleChange}
                        placeholder="Введите адрес"
                    />
                </Form.Group>
                <Button variant="primary" type="submit" className="mt-3">
                    Обновить
                </Button>
            </Form>
        </Container>
    );
});

export default Profile;
