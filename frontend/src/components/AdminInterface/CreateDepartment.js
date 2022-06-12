import { useState, useEffect, useRef } from 'react';
import '../../styles/AdminInterface/CreateDepartment.css'
import userStore from "../../UserStore";
import AdminNavBar from './AdminNavBar';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';

function CreateDepartment() {
    const [name, setName] = useState('')
    const toast = useRef(null);

    const createNewDepartment = async (e) => {
        if(name.trim().length>3){
            const response = await fetch(`/api/departments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body:JSON.stringify({
                    title:name
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
        <div id="create-department-container-flex">
        <Toast ref={toast} position="top-left"></Toast>
          {<AdminNavBar/>}
            <div className="fadein animation-duration-1000" id="create-department-container-grid">
                <h3 id="create-department-header">Create a new prize</h3>
                <InputText placeholder="Department title" id="create-department-input" value={name} onChange={(e) => setName(e.target.value)}/>
                
                <Button id="btn-create-department" label="Submit" onClick={createNewDepartment} />
            </div>
        </div>
    )
}

export default CreateDepartment;