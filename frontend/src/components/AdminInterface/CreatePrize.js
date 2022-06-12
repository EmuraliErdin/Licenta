import { useState, useEffect, useRef } from 'react';
import '../../styles/AdminInterface/CreatePrize.css'
import userStore from "../../UserStore";
import AdminNavBar from './AdminNavBar';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';

function CreatePrize() {
    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [necessaryLevel, setNecesarryLevel] = useState('')
    const toast = useRef(null);

    const createPrize = async (e) => {
        if(name.trim().length>3){
            const response = await fetch(`/api/prizes`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body:JSON.stringify({
                    name:name,
                    necessaryLevel:parseInt(necessaryLevel),
                    description:description
                })})
    
                if(response.ok){
                    showToast("success","Created","Department was created successfully!")
                } else {
                    showToast('error',"An error occurred","Something went wrong in the database.")
                } 
        }
        else{
            showToast("warn","Text field warning","Please input a valid name for a department.")
        }

    }

    const showToast = (severity, summary, detail) => {
        toast.current.show({severity: severity, summary: summary, detail: detail});
    }

    return (
        <div id="create-prize-container-flex">
        <Toast ref={toast} position="top-left"></Toast>
          {<AdminNavBar/>}
            <div className="fadein animation-duration-1000" id="create-prize-container-grid">
                <h3 id="create-prize-header">Create a new prize</h3>
                <InputText placeholder="Prize title" id="create-prize-input-title" value={name} onChange={(e) => setName(e.target.value)}/>
                <InputText placeholder="Short prize description" id="create-prize-input-description" value={description} onChange={(e) => setDescription(e.target.value)}/>
                <InputText keyfilter="money" placeholder="Level necessary for prize" id="create-prize-level" value={necessaryLevel} onChange={(e) => setNecesarryLevel(e.target.value)}/>
                <Button id="btn-create-prize" label="Submit" onClick={createPrize} />
            </div>
        </div>
    )
}

export default CreatePrize;