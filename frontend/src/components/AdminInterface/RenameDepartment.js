import { useState, useEffect, useRef } from 'react';
import '../../styles/AdminInterface/RenameDepartment.css'
import userStore from "../../UserStore";
import AdminNavBar from './AdminNavBar';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { InputText } from 'primereact/inputtext';

function RenameDepartment() {
    const [oldDepartment, setOldDepartment] = useState(null)
    const [newDepartmentName, setNewDepartmentName] = useState("")
    const toast = useRef(null);
    const [departmentItems, setDepartmentItems]=useState([])
    let departmentItemsBuilder=[];

    useEffect(()=>{
       getDepartments();

    },[])

    const getDepartments = async () =>{
        const responseDepartments = await fetch(`/api/departments`);
        const result = await responseDepartments.json();
        departmentItemsBuilder = [];
        for(let i=0;i<result.length;i++){
            departmentItemsBuilder.push({
                label:result[i].title,
                value: result[i].id
            })
        }
        setDepartmentItems(departmentItemsBuilder)
    }

    const renameDepartment = async (e) => {
        console.log(e);
        if(newDepartmentName.trim().length>=3){
            const response = await fetch(`/api/departments/${oldDepartment}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body:JSON.stringify({
                    title:newDepartmentName
                })})
                if(response.ok){
                    showToast("success","Modified","Department was modified successfully!")
                } else {
                    showToast('error',"An error occurred","Something went wrong in the database.")
                }
        } else {
            showToast("warn","Text field warning","Please input a valid name.")
        }

    }

    const showToast = (severity, summary, detail) => {
        toast.current.show({severity: severity, summary: summary, detail: detail});
    }

    return (
        <div id="change-department-container-flex">
        <Toast ref={toast} position="top-left"></Toast>
          {<AdminNavBar/>}
            <div className="fadein animation-duration-500" id="change-department-container-grid">
                <h3 id="change-department-header">Change a department's name</h3>
                <Dropdown id="change-department-dropdown" value={oldDepartment} options={departmentItems} onChange={(e) => {setOldDepartment(e.value); console.log(e);}} placeholder="Select the old department"/>
                <InputText placeholder="Enter a new department name" id="change-department-input" value={newDepartmentName} onChange={(e) => setNewDepartmentName(e.target.value)} />
                <Button id="btn-change-department" label="Submit" onClick={renameDepartment} />
            </div>
        </div>
    )

}

export default RenameDepartment;