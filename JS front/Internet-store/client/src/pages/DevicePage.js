import React, { useEffect, useState } from 'react';
import { Image, Button } from 'react-bootstrap'
import { useParams } from 'react-router-dom';
import Bigstar from '../assets/Bigstar.svg'
import { fetchOneDevice } from '../http/deviceAPI';


const DevicePage = () => {
    const [device, setDevice] = useState({info:[]})
    const { id } = useParams()
    useEffect(() => { fetchOneDevice(id).then(data=> setDevice(data))},[])
    
    
    return (
        <div className="container mt-3 ">
            <div className="row">
            <div className="col-md-4 mt-2">
                    <Image height={220} weight={200} src={process.env.REACT_APP_API_URL + device.img} />
            </div>

            <div className="col-md-4 ">
                <div className="row">
                    <h2>
                            {device.name}
                    </h2>
                    <div className="d-flex align-items-center justify-content-center"
                        style={{background: `url(${Bigstar}) no-repeat center center`, width:210, height:200, backgroundSize: 'cover', fontSize:64}}                    >
                            {device.rating}
                    </div>
                </div>
            </div>
                <div className="col-md-4">
                    <div className="card d-flex flex-column align-items-center justify-content-around"
                        style={{width: 300, height:300, fontSize:32, border: '5px solid laighgray'}}                    >
                        <h3>{device.price} руб.</h3>
                        <Button variant={"outline-dark"}>Добавить в корзину</Button>
                        </div>
                </div>
            </div>
            <div className="row d-flex flex-column m-3">
                <h1>Характеристики</h1>
                {device.info.map((i, index) =>
                    <div className="row" key={i.id} style={{ background: index % 2 === 0 ? 'lightgray' : 'transparent', padding:10}}
                    >
                        {i.title}: {i.description}
                    </div>)}
            </div>
        </div>
    );
}

export default DevicePage;