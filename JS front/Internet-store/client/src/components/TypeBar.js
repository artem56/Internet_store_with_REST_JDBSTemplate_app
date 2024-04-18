import React, { useContext } from 'react';
import { observer } from 'mobx-react-lite'
import { Context } from '../index';

const TypeBar = observer((props) => {
    const {device} = useContext(Context)
    return (
        <ul className="list-group">
            {device.Types.map
                (Type => <li className={Type.id === device.selectedType.id ? "card list-group-item bg-warning" : "list-group-item"}
                    onClick={() => {
                        if (Type.id === device.selectedType.id) {
                            return device.setSelectedType({id:undefined})
                        } device.setSelectedType(Type)
                    }}
                    style={{cursor:'pointer'}}
                    key={Type.id}>{Type.name}
                </li>)}
            
        </ul>
    );
})

export default TypeBar;