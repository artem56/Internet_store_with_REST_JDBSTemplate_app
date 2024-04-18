import React, { useContext } from 'react';
import { Image } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom';
import star from '../assets/star.svg'
import { Context } from '../index';
import { DEVICE_ROUTE } from '../utils/consts';
const DeviceItem = ({ _device }) => {
    const {device}= useContext(Context)
    const Navigate = useNavigate()
    return (
        <div className="col-sm-3  mt-3" onClick={() => Navigate(DEVICE_ROUTE + '/' + _device.id)}>
            <div className="card" style={{ width: 150, cursor: 'pointer' }} border={"light"}>
                <Image height={148} width={148} src={process.env.REACT_APP_API_URL + _device.img} />
                <div className="text-black-50 mt-1 d-flex justify-content-between ">
                    <div>
                        {device.Brands.map((brand) => brand.id === _device.brandId ? brand.name : '')}
                    </div>
                    <div className="container d-flex " >
                        <div> {_device.rating} </div>
                        <img height={20} width={20} src={star}  />
                    </div>
                </div>
                <div>
                    {_device.name}
                </div>
            </div>
           </div>
    );
}

export default DeviceItem;