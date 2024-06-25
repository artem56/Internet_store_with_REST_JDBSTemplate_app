import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchTypes, deleteType } from '../../http/deviceAPI';

const DeleteType = ({ show, onHide }) => {
    const [types, setTypes] = useState([]);
    const [selectedType, setSelectedType] = useState(null);

    useEffect(() => {
        fetchTypes().then(data => setTypes(data));
    }, []);

    const handleSelect = (type) => {
        setSelectedType(type);
    };

    const handleDelete = () => {
        deleteType(selectedType.id).then(() => {
            setSelectedType(null);
            onHide();
        });
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Удалить тип</Modal.Title>
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
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Отмена</Button>
                <Button variant="danger" onClick={handleDelete}>Удалить</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default DeleteType;
