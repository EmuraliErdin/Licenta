import '../styles/ReportAProblem.css'
import userStore from '../UserStore'
import { InputTextarea } from 'primereact/inputtextarea';
import { useState, useRef } from 'react';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { Dropdown } from 'primereact/dropdown';
import {formatDate} from '../functions'
import ManagerNavBar from './ManagerInterface/ManagerNavBar';
import EmployeeNavBar from './EmployeeInterface/EmployeeNavBar'

function ReportAProblem() {

    const toast = useRef(null)
    const [urgencyLevel, setUrgencyLevel] = useState('Low')
    const [reason, setReason] = useState('')

    
const urgencyLevelList = [
    {label: 'Urgent', value: 'Urgent'},
    {label: 'High', value: 'High'},
    {label: 'Medium', value: 'Medium'},
    {label: 'Low', value: 'Low'},
];

    const getNavBar = () => {
        if(userStore.employee.isManager==false){
            return <EmployeeNavBar id='employee-profile-menu'/>
        }
        else {
            return <ManagerNavBar id='manager-profile-menu'/>
        }
    }

    const sendIssue = async () =>{
        if(verifyData()){
            const response = await fetch('/api/issues', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    reason:reason,
                    priorityLevel:urgencyLevel,
                    status:'PENDING',
                    createDate: formatDate(new Date())
                }),
            });

            if(response.ok){
                showToast('success','Added','The issue has been saved!')
            }
        }
        else{
            showToast('warn','textfield error','Something went wrong. Please verify your input')
        }
    }

    const showToast = (severity, summary, detail) => {
        toast.current.show({severity: severity, summary: summary, detail: detail});
    }

    const verifyData = () =>{
        if(reason.trim().length>5){
            return true;
        }
        return false;
    }
    
    return (
        <div id='report-a-problem-container-flex'>
            <Toast ref={toast} />
            {getNavBar()}
            <div id='report-a-problem-container-grid'>
                
                <InputTextarea id='report-a-problem-reason' rows={5} cols={50} value={reason} onChange={(e) => setReason(e.target.value)} 
                    autoResize placeholder='Please input your reason'/>
                <h3 id='report-a-problem-urgency-header'>Please specify the urgency level</h3>
                <Dropdown id='report-a-problem-urgency-level'value={urgencyLevel} options={urgencyLevelList} onChange={(e) => setUrgencyLevel(e.value)} />
                <Button id='report-a-problem-btn-send' label="Send" className="p-button-outlined" onClick={() => sendIssue()}/>
            </div>
        </div>
    )
}

export default ReportAProblem;