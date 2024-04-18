import React, { useContext } from 'react';
import { observer } from 'mobx-react-lite'
import { Card } from 'react-bootstrap'
import { Context } from '../index';

const BrandBar = observer(() => {
    const {device} = useContext(Context)
    return (
        <div className="row d-flex">
           
            {device.Brands.map((Brand) => 
                <Card className={Brand.id === device.selectedBrand.id ? "col-2 bg-warning p-3" : "col-2 p-3"}
                    onClick={() => {
                        if (Brand.id === device.selectedBrand.id) {
                            return device.setSelectedBrand({ id: undefined })
                        } device.setSelectedBrand(Brand)
                    }}
                    style={{ cursor: 'pointer' }}
                    key={Brand.id}>{Brand.name}
                </Card>
                )}
                
        </div>

    );
})

export default BrandBar;