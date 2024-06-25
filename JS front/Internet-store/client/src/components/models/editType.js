import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchTypes, updateType } from '../../http/deviceAPI';

const EditType = ({ show, onHide }) => {
    const [types, setTypes] = useState([]);
    const [selectedType, setSelectedType] = useState(null);
    const [value, setValue] = useState('');

    useEffect(() => {
        fetchTypes().then(data => setTypes(data));
    }, []);

    const handleSelect = (type) => {
        setSelectedType(type);
        setValue(type.name);
    };

    const handleSubmit = () => {
        updateType(selectedType.id, { name: value }).then(() => {
            setSelectedType(null);
            setValue('');
            onHide();
        });
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Редактировать тип</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formTypeSelect">
                        <Form.Label>Выберите тип</Form.Label>
                        <Form.Control as="select" onChange={(e) => handleSelect(types.find(t => t.id === parseInt(e.target.value)))}>
                            <option value="">Выберите...</option>
                            {types.map(type => (
                                <option key={type.id} value={type.id}>{type.name}</option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group controlId="formTypeName" className="mt-3">
                        <Form.Label>Название типа</Form.Label>
                        <Form.Control
                            type="text"
                            value={value}
                            onChange={(e) => setValue(e.target.value)}
                            placeholder="Введите название типа"
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Закрыть</Button>
                <Button variant="primary" onClick={handleSubmit}>Сохранить</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default EditType;
