import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchBrands, deleteBrand } from '../../http/deviceAPI';

const DeleteBrand = ({ show, onHide }) => {
    const [brands, setBrands] = useState([]);
    const [selectedBrand, setSelectedBrand] = useState(null);

    useEffect(() => {
        fetchBrands().then(data => setBrands(data));
    }, []);

    const handleSelect = (brand) => {
        setSelectedBrand(brand);
    };

    const handleDelete = () => {
        deleteBrand(selectedBrand.id).then(() => {
            setSelectedBrand(null);
            onHide();
        });
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Удалить бренд</Modal.Title>
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
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Отмена</Button>
                <Button variant="danger" onClick={handleDelete}>Удалить</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default DeleteBrand;
