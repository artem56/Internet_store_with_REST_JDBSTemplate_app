import React, { useState } from 'react';
import {Button} from 'react-bootstrap'
import CreateDevice from '../components/models/createDevice';
import CreateType from '../components/models/createType';
import CreateBrand from '../components/models/createBrand';
const Admin = () => {
    const [brandVisible, setBrandVisible] = useState(false)
    const [typeVisible, setTypeVisible] = useState(false)
    const [deviceVisible, setDeviceVisible] = useState(false)
    return (
        <div className="container d-flex flex-column ">
            
            <Button className="m-2" variant={'outline-dark'} onClick={ () => setTypeVisible (true) } >Добавить тип</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={ () => setBrandVisible (true) } >Добавить бренд</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={ () => setDeviceVisible (true) } >Добавить продукт</Button>
            
            
            <CreateType show={typeVisible} onHide={setTypeVisible}/>
            <CreateBrand show={brandVisible} onHide={setBrandVisible}/>
            <CreateDevice show={deviceVisible} onHide={setDeviceVisible}/>
        </div>
    );
}

export default Admin;