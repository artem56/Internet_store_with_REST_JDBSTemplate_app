import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchBrands, updateBrand } from '../../http/deviceAPI';

const EditBrand = ({ show, onHide }) => {
    const [brands, setBrands] = useState([]);
    const [selectedBrand, setSelectedBrand] = useState(null);
    const [value, setValue] = useState('');

    useEffect(() => {
        fetchBrands().then(data => setBrands(data));
    }, []);

    const handleSelect = (brand) => {
        setSelectedBrand(brand);
        setValue(brand.name);
    };

    const handleSubmit = () => {
        updateBrand(selectedBrand.id, { name: value }).then(() => {
            setSelectedBrand(null);
            setValue('');
            onHide();
        });
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Редактировать бренд</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formBrandSelect">
                        <Form.Label>Выберите бренд</Form.Label>
                        <Form.Control as="select" onChange={(e) => handleSelect(brands.find(b => b.id === parseInt(e.target.value)))}>
                            <option value="">Выберите...</option>
                            {brands.map(brand => (
                                <option key={brand.id} value={brand.id}>{brand.name}</option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group controlId="formBrandName" className="mt-3">
                        <Form.Label>Название бренда</Form.Label>
                        <Form.Control
                            type="text"
                            value={value}
                            onChange={(e) => setValue(e.target.value)}
                            placeholder="Введите название бренда"
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

export default EditBrand;
