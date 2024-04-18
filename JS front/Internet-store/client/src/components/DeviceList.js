import React, { useContext } from 'react';
import { observer } from 'mobx-react-lite'
import { Context } from '../index';
import DeviceItem from './DeviceItem';


const DeviceList = observer(() => {
    const { device } = useContext(Context)
    return (
        <div className="row d-flex">

            {device.Devices.map((Device) =>
                <DeviceItem key={Device.id} _device={ Device} />
            )}

        </div>
    );
})

export default DeviceList;