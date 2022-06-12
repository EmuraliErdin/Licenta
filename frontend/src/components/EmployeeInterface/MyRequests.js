import userStore from '../../UserStore'
import EmployeeNavBar from './EmployeeNavBar'
import { useState, useRef, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Toast } from 'primereact/toast';
import '../../styles/EmployeeInterface/MyRequests.css'

function MyRequests() {
    const [requests, setRequests] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [date, setDate] = useState(new Date())
    const [type, setType] = useState('')
    const toast = useRef(null);
    const typeSelectItems = [
        {label: 'Overtime', value: 'ADD_HOURS'},
        {label: 'Early leave', value: 'SUBTRACT_HOURS'},
    ];

    useEffect(()=>{
        getRequests()
    },[])

    const getRequests = async () => {
        const responseRequests = await fetch(`/api/employees/${userStore.employee.id}/requests`)
        const requestsList = await responseRequests.json();
        for(let i=0;i<requestsList.length;i++){
            if(requestsList[i].type=='ADD_HOURS'){
                requestsList[i].type = 'Overtime'
            } 
            else{
                requestsList[i].type = 'Leaving early'
            }
            if(requestsList[i].status == 'PENDING'){
                requestsList[i].status = 'Pending'
            }
            else if(requestsList[i].status == 'ACCEPTED'){
                requestsList[i].status = 'Accepted'
            }
            else if(requestsList[i].status == 'REFUSED'){
                requestsList[i].status = 'Refused'
            }
        }
        setRequests(requestsList)
        setTimeout(() => {
            setIsLoading(false)
        }, 500);
    }

    const onRowEditComplete = (e) => {
        if(e.data.status!='Pending'){
            showErrorStatus()
            return;
        }
        updateRequest(e.data, e.newData)
    }

    const updateRequest = async (oldValue, newValue)=> {
        
        let reqDate = oldValue.requestDate
        console.log(newValue.requestDate);
        if(newValue.reqDate!=undefined){
             formatDate(newValue.requestDate)
        }
        
        let status = oldValue.status.toUpperCase()
        let obj ={
            id:oldValue.id,
            requestDate:reqDate,
            createDate:newValue.createDate,
            reason:newValue.reason,
            type:newValue.type,
            status:status,
            numberOfHours:newValue.numberOfHours
        }
        console.log(obj);
        try {
            const response = await fetch(`/api/requests/${oldValue.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id:oldValue.id,
                    requestDate:reqDate,
                    createDate:newValue.createDate,
                    reason:newValue.reason,
                    type:newValue.type,
                    status:status,
                    numberOfHours:newValue.numberOfHours

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

    const padTo2Digits = (num) => {
        return num.toString().padStart(2, '0');
    }

    const formatDate = (date) => {
        return [
          date.getFullYear(),
          padTo2Digits(date.getMonth() + 1),
          padTo2Digits(date.getDate()),
        ].join('-');
      }

      const getButton = (e) =>{
          if(e.status == 'Pending'){
            return <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" aria-label="Cancel" onClick={()=> deleteRequest(e)} />
          }
          else{
            return <Button icon="pi pi-trash" disabled={true} className="p-button-rounded p-button-danger" aria-label="Cancel" />
          } 
    }

    const deleteRequest = async (e) =>{
        if(e.status != 'Pending'){
            showError("The request could not be deleted. If the status is one of 'accepted' or 'refused' you cannot delete it")
            return;
        }
        const response = await fetch(`/api/requests/${e.id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }})
        if(response.status == 204){
            showSuccess()
            getRequests()
        }
    }

    const textEditor = (options) => {
        return <InputText type="text" value={options.value} onChange={(e) => options.editorCallback(e.target.value)} />
    }
    const calendarEditor = (options) => {
        return <Calendar value={options.value} dateFormat="yy-mm-dd" onChange={(e) => options.editorCallback(e.target.value)}></Calendar>
    }

    const typeEditor = (options) => {
        return <Dropdown value={options.value} options={typeSelectItems} onChange={(e) => options.editorCallback(e.target.value)}/>
    }

    const showError = (text) => {
        toast.current.show({severity:'error', summary: 'Error', detail:text, life: 3000});
    }
    const showErrorStatus = () => {
        toast.current.show({severity:'error', summary: 'Error', detail:'Status of the request must be pending in order to change other fields', life: 3000});
    }
   
    const showSuccess = () => {
        toast.current.show({severity:'success', summary: 'Updated', detail:'The request was updated', life: 3000});
    }

    return (
        <div id='my-requests-container-flex'>
            
            <Toast ref={toast} />
            <EmployeeNavBar/>
            <div id='my-requests-list-container'>
                <DataTable value={requests} editMode="row" onRowEditComplete={onRowEditComplete} stripedRows responsiveLayout="scroll" loading={isLoading}>
                    <Column field="numberOfHours" editor={(options) => textEditor(options)} header="Hours requested"></Column>
                    <Column field="reason" editor={(options) => textEditor(options)} header="Reason"></Column>
                    <Column field="createDate" header="Created on"></Column>
                    <Column field="requestDate" editor={(options) => calendarEditor(options)} header="Request date"></Column>
                    <Column field="status" header="Status"></Column>
                    <Column field="type" editor={(options) => typeEditor(options)} header="Type"></Column>
                    <Column rowEditor bodyStyle={{ textAlign: 'center' }}  header="Edit"/>
                    <Column bodyStyle={{ textAlign: 'center' }} body={getButton} header="Remove "/>
                </DataTable>
            </div>
        </div>
    )
}

export default MyRequests;