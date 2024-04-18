import React, { useContext, useEffect, useState }  from 'react';
import { Row, Col, Modal, Button, Form, Dropdown } from 'react-bootstrap'
import { fetchTypes, fetchBrands,  createDevice} from '../../http/deviceAPI'
import { Context } from '../../index';
import {observer} from 'mobx-react-lite'

const CreateDevice = observer(({ show, onHide }) => {
    const { device } = useContext(Context)
    const [info, setInfo] = useState([])
    const [name, setName] = useState('')
    const [price, setPrice] = useState(0)
    const [file, setFile] = useState(null)

    useEffect(() => {
        fetchTypes().then(data => device.setTypes(data))
        fetchBrands().then(data => device.setBrands(data))

    }, [])

    const addInfo = () => {
        setInfo([...info, {title: '', description: '', number: Date.now() }])
    }
    const deleteInfo = (number) => {
        setInfo(info.filter(i => i.number !== number))
    }

    const selectFile = (e) => {
        setFile(e.target.files[0])
    }

    const changeInfo = (key, value, number) => {
        setInfo(info.map(i => i.number === number ? {...i, [key]: value}: i))
    }
    const addDevice = () => {
        const formData = new FormData()
        formData.append('name', name)
        formData.append('price', `${price}`)
        formData.append('img', file)
        formData.append('typeId', device.selectedType.id)
        formData.append('brandId', device.selectedBrand.id)
        formData.append('info', JSON.stringify(info))
        console.log(JSON.stringify(info))
        createDevice(formData).then(data => onHide(false))
    }

    return (
        <>
     
      <Modal className="" size="lg" show={show} onHide={onHide}>
        <Modal.Header closeButton>
          <Modal.Title>Добавление продукта</Modal.Title>
        </Modal.Header>
        <Modal.Body>
                    <Dropdown className="">
                        <Dropdown.Toggle>{device.selectedType.name || "Выберите тип"}</Dropdown.Toggle>
                        <Dropdown.Menu>{device.Types.map(type =>
                            <Dropdown.Item key={type.id}
                                onClick={() =>  device.setSelectedType(type)}>
                                {type.name}
                            </Dropdown.Item>
                            )}</Dropdown.Menu>
                    </Dropdown>
                     <Dropdown className="">
                        <Dropdown.Toggle>{device.selectedBrand.name || "Выберите бренд"}</Dropdown.Toggle>
                        <Dropdown.Menu>{device.Brands.map(brand =>
                            <Dropdown.Item key={brand.id}
                                onClick={() => device.setSelectedBrand(brand)}>
                                {brand.name}
                            </Dropdown.Item>
                            )}</Dropdown.Menu>
                    </Dropdown>
                  <Form>
                      <Form.Control
                          placeholder={"Введите название продукта"}
                            value={name}
                            onChange={(e)=>setName(e.target.value)}
                             />
                  </Form>
                    <Form>
                      <Form.Control
                          placeholder={"Введите стоимость устройства"}
                             type="number"
                            onChange={(e) => setPrice(Number(e.target.value))}
                             value={price}/>
                  </Form>
                    <Form>
                      <Form.Control
                          placeholder={"Выберите изображение устройства"}
                            type="file" 
                            onChange={(e) => selectFile(e)}
                            />
                  </Form>
                    <hr />
                    <Button varian="outline-black"
                        onClick={addInfo}
                        >Добавить харакеристику</Button>
                    {info.map(i =>
                        <Row className="align-items-center" key={i.number}>
                            <Col className="col-md-4 mt-1"><Form.Control
                          placeholder="Введите характеристику" value={ i.title} onChange={ (e)=>changeInfo('title', e.target.value, i.number)}  /> </Col>:
                            <Col className="col-md-4"><Form.Control
                          placeholder="Введите описание" value={i.description} onChange={(e) => changeInfo('description', e.target.value, i.number)} /> </Col>
                            <Col className="col-md-2 ">
                           <Button variant="outline-danger" onClick={()=>deleteInfo(i.number)} className="" style={{height:30, width: 100}} >Удалить </Button></Col>
                            </Row>)}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-danger" onClick={onHide}>
            Закрыть
          </Button>
          <Button variant="outline-success" onClick={addDevice}>
            Добавить
          </Button>
        </Modal.Footer>
      </Modal>
    </>
    );
})

export default CreateDevice;