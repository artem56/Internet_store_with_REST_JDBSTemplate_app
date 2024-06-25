import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import CreateDevice from '../components/models/createDevice';
import CreateType from '../components/models/createType';
import CreateBrand from '../components/models/createBrand';
import EditType from '../components/models/editType';
import EditBrand from '../components/models/editBrand';
import EditDevice from '../components/models/editDevice';
import DeleteType from '../components/models/deleteType';
import DeleteBrand from '../components/models/deleteBrand';
import DeleteDevice from '../components/models/deleteDevice';

const Admin = () => {
    const [brandVisible, setBrandVisible] = useState(false);
    const [typeVisible, setTypeVisible] = useState(false);
    const [deviceVisible, setDeviceVisible] = useState(false);
    const [editTypeVisible, setEditTypeVisible] = useState(false);
    const [editBrandVisible, setEditBrandVisible] = useState(false);
    const [editDeviceVisible, setEditDeviceVisible] = useState(false);
    const [deleteTypeVisible, setDeleteTypeVisible] = useState(false);
    const [deleteBrandVisible, setDeleteBrandVisible] = useState(false);
    const [deleteDeviceVisible, setDeleteDeviceVisible] = useState(false);

    return (
        <div className="container d-flex flex-column">
            <Button className="m-2" variant={'outline-dark'} onClick={() => setTypeVisible(true)}>Добавить тип</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={() => setBrandVisible(true)}>Добавить бренд</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={() => setDeviceVisible(true)}>Добавить продукт</Button>

            <Button className="m-2" variant={'outline-dark'} onClick={() => setEditTypeVisible(true)}>Редактировать тип</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={() => setEditBrandVisible(true)}>Редактировать бренд</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={() => setEditDeviceVisible(true)}>Редактировать продукт</Button>

            <Button className="m-2" variant={'outline-dark'} onClick={() => setDeleteTypeVisible(true)}>Удалить тип</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={() => setDeleteBrandVisible(true)}>Удалить бренд</Button>
            <Button className="m-2" variant={'outline-dark'} onClick={() => setDeleteDeviceVisible(true)}>Удалить продукт</Button>

            <CreateType show={typeVisible} onHide={() => setTypeVisible(false)} />
            <CreateBrand show={brandVisible} onHide={() => setBrandVisible(false)} />
            <CreateDevice show={deviceVisible} onHide={() => setDeviceVisible(false)} />

            <EditType show={editTypeVisible} onHide={() => setEditTypeVisible(false)} />
            <EditBrand show={editBrandVisible} onHide={() => setEditBrandVisible(false)} />
            <EditDevice show={editDeviceVisible} onHide={() => setEditDeviceVisible(false)} />

            <DeleteType show={deleteTypeVisible} onHide={() => setDeleteTypeVisible(false)} />
            <DeleteBrand show={deleteBrandVisible} onHide={() => setDeleteBrandVisible(false)} />
            <DeleteDevice show={deleteDeviceVisible} onHide={() => setDeleteDeviceVisible(false)} />
        </div>
    );
}

export default Admin;
