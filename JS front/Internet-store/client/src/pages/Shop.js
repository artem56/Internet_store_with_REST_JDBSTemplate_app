import React, { useContext, useEffect } from 'react';
import { Container, Row, Form, Card, Button, Col} from 'react-bootstrap'
import BrandBar from '../components/BrandBar';
import {observer} from'mobx-react-lite'
import DeviceList from '../components/DeviceList';
import TypeBar from '../components/TypeBar';
import { Context } from '../index';
import { fetchBrands, fetchDevices, fetchTypes } from '../http/deviceAPI';

const Shop = observer(() => {
    const { device } = useContext(Context)

    useEffect(
        () => {
            fetchTypes().then(data => device.setTypes(data))
            fetchBrands().then(data => device.setBrands(data))
            fetchDevices().then(data => device.setDevices(data.rows))
        }, [])
    return (
        <Container>
            <Row className="mt-2">
                <Col md={2}>
                    <TypeBar/>
                </Col>
                <Col md={9}>
                    <BrandBar />
                    <DeviceList />
                </Col>
            </Row>
        </Container>
    );
})

export default Shop;