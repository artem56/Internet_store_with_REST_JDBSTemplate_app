import React, { useState, useEffect, useContext } from 'react';
import { Modal, Button, Form, Dropdown } from 'react-bootstrap';
import { fetchDevices, fetchTypes, fetchBrands, updateDevice } from '../../http/deviceAPI';
import { Context } from '../../index';
import { observer } from 'mobx-react-lite';

const EditDevice = observer(({ show, onHide }) => {
    const { device } = useContext(Context);
    const [devices, setDevices] = useState([]);
    const [selectedDevice, setSelectedDevice] = useState(null);
    const [name, setName] = useState('');
    const [price, setPrice] = useState(0);
    const [file, setFile] = useState(null);
    const [info, setInfo] = useState([]);

    useEffect(() => {
        fetchDevices().then(data => setDevices(data.rows || []));
        fetchTypes().then(data => {
            console.log('Fetched types:', data); // Лог для проверки типов
            device.setTypes(data || []);
        });
        fetchBrands().then(data => {
            console.log('Fetched brands:', data); // Лог для проверки брендов
            device.setBrands(data || []);
        });
    }, [device]);

    const handleSelect = (device) => {
        setSelectedDevice(device);
        setName(device.name);
        setPrice(device.price);
        try {
            setInfo(Array.isArray(device.info) ? device.info : JSON.parse(device.info));
        } catch (error) {
            setInfo([]);
        }
    };

    const addInfo = () => {
        setInfo([...info, { title: '', description: '', number: Date.now() }]);
    };

    const deleteInfo = (number) => {
        setInfo(info.filter(i => i.number !== number));
    };

    const selectFile = (e) => {
        setFile(e.target.files[0]);
    };

    const changeInfo = (key, value, number) => {
        setInfo(info.map(i => i.number === number ? { ...i, [key]: value } : i));
    };

    const handleSubmit = () => {
        const formData = new FormData();
        formData.append('name', name);
        formData.append('price', `${price}`);
        if (file) formData.append('img', file);
        formData.append('typeId', device.selectedType.id);
        formData.append('brandId', device.selectedBrand.id);
        formData.append('info', JSON.stringify(info));
        updateDevice(selectedDevice.id, formData).then(() => {
            setSelectedDevice(null);
            setName('');
            setPrice(0);
            setFile(null);
            setInfo([]);
            onHide();
        });
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Редактировать продукт</Modal.Title>
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
                    <Dropdown className="mt-3">
                        <Dropdown.Toggle>{device.selectedType?.name || "Выберите тип"}</Dropdown.Toggle>
                        <Dropdown.Menu>
                            {device.Types?.map(type => (
                                <Dropdown.Item key={type.id} onClick={() => device.setSelectedType(type)}>
                                    {type.name}
                                </Dropdown.Item>
                            ))}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Dropdown className="mt-3">
                        <Dropdown.Toggle>{device.selectedBrand?.name || "Выберите бренд"}</Dropdown.Toggle>
                        <Dropdown.Menu>
                            {device.Brands?.map(brand => (
                                <Dropdown.Item key={brand.id} onClick={() => device.setSelectedBrand(brand)}>
                                    {brand.name}
                                </Dropdown.Item>
                            ))}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Form.Group controlId="formDeviceName" className="mt-3">
                        <Form.Label>Название продукта</Form.Label>
                        <Form.Control
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="Введите название продукта"
                        />
                    </Form.Group>
                    <Form.Group controlId="formDevicePrice" className="mt-3">
                        <Form.Label>Стоимость устройства</Form.Label>
                        <Form.Control
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(Number(e.target.value))}
                            placeholder="Введите стоимость устройства"
                        />
                    </Form.Group>
                    <Form.Group controlId="formDeviceImage" className="mt-3">
                        <Form.Label>Выберите изображение устройства</Form.Label>
                        <Form.Control
                            type="file"
                            onChange={selectFile}
                        />
                    </Form.Group>
                    <hr />
                    <Button variant="outline-black" onClick={addInfo}>Добавить характеристику</Button>
                    {info.map(i => (
                        <div className="d-flex align-items-center mt-3" key={i.number}>
                            <Form.Control
                                className="me-2"
                                placeholder="Введите характеристику"
                                value={i.title}
                                onChange={(e) => changeInfo('title', e.target.value, i.number)}
                            />
                            <Form.Control
                                className="me-2"
                                placeholder="Введите описание"
                                value={i.description}
                                onChange={(e) => changeInfo('description', e.target.value, i.number)}
                            />
                            <Button variant="outline-danger" onClick={() => deleteInfo(i.number)}>Удалить</Button>
                        </div>
                    ))}
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Закрыть</Button>
                <Button variant="primary" onClick={handleSubmit}>Сохранить</Button>
            </Modal.Footer>
        </Modal>
    );
});

export default EditDevice;
