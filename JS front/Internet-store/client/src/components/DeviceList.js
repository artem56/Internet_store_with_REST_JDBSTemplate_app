import React, { useContext } from 'react';
import { observer } from 'mobx-react-lite'
import { Context } from '../index';
import DeviceItem from './DeviceItem';


const DeviceList = observer(() => {
    const { device } = useContext(Context)
    return (
        <div className="row d-flex">

            {device.Devices.map((Device) =>
            {//Выводим товары выбранных типов

			if ((device.selectedBrand.id ? (Device.brand_brand_id === device.selectedBrand.id) : true) && (device.selectedType.id ? (Device.type_type_id === device.selectedType.id) : true)){
                return <DeviceItem key={Device.id} _device={ Device} />
                }
                else{
                return null;}

            })}

        </div>
    );
})

export default DeviceList;

