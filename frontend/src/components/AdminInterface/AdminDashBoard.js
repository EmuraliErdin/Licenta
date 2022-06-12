import { useState, useEffect, useRef } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import '../../styles/AdminInterface/AdminDashBoard.css'
import userStore from "../../UserStore";
import AdminNavBar from './AdminNavBar';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

function AdminDashBoard() {
    const [issueList, setIssueList] = useState([])
    const toast = useRef(null);
    const navigate = useNavigate();
    const getIssues = async () => {
        const responseIssues = await fetch(`/api/issues`)
        const issues = await responseIssues.json();
        setIssueList(issues);
    }

    useEffect(()=>{
        console.log(userStore.employee.id==null);
        getIssues();
    },[])

    const getButton = (e) =>{
        if(e.status == 'PENDING'){
            return <Button icon="pi pi-check" className="p-button-rounded p-button-success" aria-label="Cancel" onClick={()=> markRequest(e)} />
          }
          else{
            return <Button icon="pi pi-check" disabled={true} className="p-button-rounded p-button-success" />
          } 
    }

    const markRequest = async (e) => {
        const response = await fetch(`/api/issues/${e.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body:JSON.stringify({
                status:"SOLVED",

            })})

            if(response.ok){
                showToast("success","Updated","Issue has been updated!")
                getIssues()
            } else {
                showToast('error',"An error occurred","Something went wrong in the database.")
            }
    }

    const showToast = (severity, summary, detail) => {
        toast.current.show({severity: severity, summary: summary, detail: detail});
    }

    return (
        userStore.employee.id!=null ? (
            <div>
                <div id="admin-dashboard-container-flex">
                     <Toast ref={toast} position="top-left"></Toast>
                    {<AdminNavBar/>}
                <div className="fadein animation-duration-1000" id="admin-dashboard">
                    <DataTable id="issue-list" value={issueList} responsiveLayout="scroll">
                        <Column field="priorityLevel" header="Priority Level"></Column>
                        <Column field="createDate" header="Creation date"></Column>
                        <Column field="status" header="Status"></Column>
                        <Column field="reason" header="Reason"></Column>
                        <Column bodyStyle={{ textAlign: 'center' }} body={getButton} header="Mark as solved"/>
                    </DataTable>
                </div>
        </div>
            </div>
              ) : (
                <Navigate replace to={{ pathname: '/'}} />
              )

    )

}

export default AdminDashBoard;