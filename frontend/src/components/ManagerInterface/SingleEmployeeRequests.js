import userStore from '../../UserStore'
import EmployeeNavBar from '../EmployeeInterface/EmployeeNavBar'
import { useState, useRef, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { ConfirmDialog } from 'primereact/confirmdialog';
import '../../styles/ManagerInterface/EmployeesRequests.css'
import ManagerNavBar from './ManagerNavBar';

function EmployeesRequests() {
    const [requests, setRequests] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [cfDialogVisible, setCfDialogVisible] = useState(false)
    const [rjDialogVisible, setRjDialogVisible] = useState(false)
    let resp;
    const toast = useRef(null);
    const typeSelectItems = [
        {label: 'Overtime', value: 'ADD_HOURS'},
        {label: 'Early leave', value: 'SUBTRACT_HOURS'},
    ];

    useEffect(()=>{
        getRequests()
    },[])

    const getRequests = async () => {
        const responseRequests = await fetch(`/api/employee/${userStore.employee.departmentId}/requests`)
        const requestsList = await responseRequests.json();
        
        for(let i=0;i<requestsList.length;i++){
            if(requestsList[i].type==='ADD_HOURS'){
                requestsList[i].type = 'Overtime'
            } 
            else{
                requestsList[i].type = 'Leaving early'
            }
            if(requestsList[i].status === 'PENDING'){
                requestsList[i].status = 'Pending'
            }
            else if(requestsList[i].status === 'ACCEPTED'){
                requestsList[i].status = 'Accepted'
            }
            else if(requestsList[i].status === 'REFUSED'){
                requestsList[i].status = 'Refused'
            }
        }
        setRequests(requestsList)
        setTimeout(() => {
            setIsLoading(false)
        }, 500);
    }
     
    const acceptRequest = (e) =>{
        updateRequest('ACCEPTED', e.id)
    }
    const denyRequest = (e) =>{
        updateRequest('REFUSED', e.id)
    }

    const updateRequest = async (status, requestId)=> { 
        try {
            const response = await fetch(`/api/requests/${requestId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    status:status,
                    employeeLogId: userStore.employee.id
                })
            })

            if (response.ok) {
                showSuccess()
            }
            else {
                showError(response.statusText)
            }

            getRequests()
        } catch (err) {
            console.warn(err);
        }
    }

        const getButton = (e) =>{

            if(e.status == 'Pending'){
                return (
                <div>
                    <Button icon="pi pi-check" className="p-button-rounded p-button-success"  onClick={() => acceptRequest(e)} aria-label="Search" />
                    <Button icon="pi pi-times" className="p-button-rounded p-button-danger" onClick={() => denyRequest(e)} aria-label="Cancel" />
                </div>
                )
            }
            else{
                return (
                <div>
                    <Button icon="pi pi-check"  disabled = {true} className="p-button-rounded p-button-success" aria-label="Search" />
                    <Button icon="pi pi-times"  disabled = {true} className="p-button-rounded p-button-danger" aria-label="Cancel" />
                </div>
                )
            }
        
    }

    const showError = (text) => {
        toast.current.show({severity:'error', summary: 'Error', detail:text, life: 3000});
    }
   
    const showSuccess = () => {
        toast.current.show({severity:'success', summary: 'Updated', detail:'The request was updated', life: 3000});
    }

    return (
        <div id='single-employee-requests-container-flex'>       
            <Toast ref={toast} />
            <ManagerNavBar/>
            <div id='single-employee-requests-list-container'>
                <DataTable value={requests} stripedRows responsiveLayout="scroll" loading={isLoading}>
                    <Column field="numberOfHours" header="Hours requested"></Column>
                    <Column field="reason" header="Reason"></Column>
                    <Column field="createDate" header="Created on"></Column>
                    <Column field="requestDate"  header="Request date"></Column>
                    <Column field="status" header="Status"></Column>
                    <Column field="type" header="Type"></Column>
                    <Column bodyStyle={{ textAlign: 'center' }} body={getButton} header="Accept/Decline "/>
                </DataTable>
            </div>
        </div>
    )
}

export default EmployeesRequests;