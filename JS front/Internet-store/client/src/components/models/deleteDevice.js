import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchDevices, deleteDevice } from '../../http/deviceAPI';

const DeleteDevice = ({ show, onHide }) => {
    const [devices, setDevices] = useState([]);
    const [selectedDevice, setSelectedDevice] = useState(null);

    useEffect(() => {
        fetchDevices().then(data => setDevices(data.rows));
    }, []);

    const handleSelect = (device) => {
        setSelectedDevice(device);
    };

    const handleDelete = () => {
        deleteDevice(selectedDevice.id).then(() => {
            setSelectedDevice(null);
            onHide();
        });
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Удалить продукт</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formDeviceSelect">
                        <Form.Label>Выберите продукт</Form.Label>
                        <Form.Control as="select" onChange={(e) => handleSelect(devices.find(d => d.id === parseInt(e.target.value)))}>
                            <option value="">Выберите...</option>
                            {devices.map(device => (
                                <option key={device.id} value={device.id}>{device.name}</option>
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

export default DeleteDevice;
