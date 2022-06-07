import userStore from '../../UserStore'
import EmployeeNavBar from '../EmployeeInterface/EmployeeNavBar'
import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import '../../styles/ManagerInterface/DepartmentMembers.css'
import { Dialog } from 'primereact/dialog';
import {formatDate} from '../../functions'
import ManagerNavBar from './ManagerNavBar';

function EmployeesRequests() {
    const [members, setMembers] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [expandedRows, setExpandedRows] = useState(null);
    const [requests, setRequests] = useState([])
    const [displayDialog, setDisplayDialog] = useState(false)
    let employee = null
    const toast = useRef(null);

    useEffect(()=>{
        getMembers()
        getRequestsOfDepartment()
    },[])

    

    const getNavBar = () => {
        if(userStore.employee.isManager===false){
            return <EmployeeNavBar id='employee-requests-menu'/>
        }
        else {
            return <ManagerNavBar id='manager-requests-menu'/>
        }
    }

    const getRequestsOfDepartment = async () => {
        const responseRequests = await fetch(`/api/departments/${userStore.employee.departmentId}/requests`)
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

    }

    const getMembers = async () => {
        const membersResponse = await fetch(`/api/departments/${userStore.employee.departmentId}/employees`)
        const membersList = await membersResponse.json();
        
        setMembers(membersList)
        setTimeout(() => {
            setIsLoading(false)
        }, 500);
    }

    const showError = (text) => {
        toast.current.show({severity:'error', summary: 'Error', detail:text, life: 3000});
    }
   
    const showSuccess = () => {
        toast.current.show({severity:'success', summary: 'Updated', detail:'The request was updated', life: 3000});
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
                employeeLogId: userStore.employee.id,
            })
        })

        if (response.ok) {
            showSuccess()
        }
        else {
            showError(response.statusText)
        }
        getRequestsOfDepartment()
    } catch (err) {
        console.warn(err);
    }
}

    const rowExpansionTemplate = (data) => {
       
        let requestsOfEmployee = []
        
        for(let i=0;i<requests.length;i++){
            if(requests[i].employeeId == data.id){
                requestsOfEmployee.push(requests[i])
            }
        }
        return (
            <div className="employee-requests-sublist">
                
                <h5>Requests for {data.firstName}  {data.lastName}</h5>
                <DataTable value={requestsOfEmployee} responsiveLayout="scroll">
                    <Column field="numberOfHours" header="Hours requested"></Column>
                    <Column field="reason" header="Reason"></Column>
                    <Column field="createDate" header="Created on"></Column>
                    <Column field="requestDate"  header="Request date"></Column>
                    <Column field="status" header="Status"></Column>
                    <Column field="type" header="Type"></Column>
                    <Column bodyStyle={{ textAlign: 'center' }} body={getButton} header="Accept/Decline "/>
                </DataTable>
            </div>
        );
    }

    const handleClickOnEmployee = (e) =>{
        employee = e
        setDisplayDialog(true)
    }

    const onHide = () => {
        setDisplayDialog(false)
    }

    return (
        <div id='department-members-container-flex'> 
            <Toast ref={toast} />
            <Dialog header="Choose an action" visible={displayDialog} style={{ width: '50vw' }} onHide={() => onHide()}>
                <div id='department-members-dialog-container'>
                    <Button label="See employee log" className="p-button-outlined department-members-dialog-buttons" />
                    <Button label="Give this employee a role" className="p-button-outlined department-members-dialog-buttons" />
                </div>
            </Dialog>
            {getNavBar()}
            <div id='department-members-list-container'>
                <DataTable value={members} onRowClick={(row) => handleClickOnEmployee(row.data)} expandedRows={expandedRows} stripedRows responsiveLayout="scroll" loading={isLoading}
                           onRowToggle={(e) => setExpandedRows(e.data)}
                           rowExpansionTemplate={(data) => rowExpansionTemplate(data)}>

                    <Column expander style={{ width: '3em' }} />
                    <Column field="firstName" header="First name"></Column>
                    <Column field="lastName" header="Last name"></Column>
                    <Column field="email" header="Email"></Column>
                </DataTable>
            </div>
        </div>
    )
}

export default EmployeesRequests;