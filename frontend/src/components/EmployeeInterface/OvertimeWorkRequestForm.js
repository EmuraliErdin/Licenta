import {useState, useRef} from "react";
import {useNavigate} from 'react-router-dom'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Calendar } from 'primereact/calendar';
import '/node_modules/primeflex/primeflex.css'
import EmployeeNavBar from "./EmployeeNavBar";
import { formatDate } from "../../functions";
import { Toast } from 'primereact/toast';
import '../../styles/EmployeeInterface/OvertimeWorkRequestForm.css'
import userStore from "../../UserStore";

function OvertimeWorkRequestForm() {
    const [reason,setReason] = useState('')
    const [numberOfHours,setNumberOfHours] = useState('')
    const [date,setDate] = useState(new Date())
    const toast = useRef(null)

    const submitData = async ()=>{
        if(verifyData()==true){
            const currentDate = new Date();
            let dateString = date.toISOString().substring(0, currentDate.toISOString().length-1);
            const responseRequest = await fetch('/api/requests', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    reason,
                    numberOfHours:parseInt(numberOfHours),
                    createDate : dateString,
                    requestDate : formatDate(date),
                    employeeId: userStore.employee.id,
                    status:"PENDING",
                    type:"ADD_HOURS"
                }),
            });

            if(responseRequest.status==201){
                showToast("The request was sent successfully!","201 created","success")
            }
        }


    }

    const verifyData = () => {
        try{
            let number = parseInt(numberOfHours);
            if(number ==0){
                showToast("Number of hours field should only contain numbers","Textfield error","error");
                return;
            }
        }catch(err){
            showToast("Number of hours field should only contain numbers","Textfield error","error");
            return;
        }

        if(reason.length<5) {
            showToast("Reason should contain something","Textfield error","error");
            return;
        }
        return true;
    }

    const showToast = (text,summary,severity) => {
        toast.current.show({severity:severity, summary: summary, detail:text, life: 3000});
    }
    
    return (
        <div id="employee-request-container-flex">
            <Toast ref={toast} />
            <EmployeeNavBar id='employee-request-menu'/>
            <div id="employee-request-container-grid">
                
                <span id="overtimework-reason" className="p-float-label p-input-icon-right">
                    <i className="pi pi-pencil" />
                    <InputText className='input' id="overtimework-reason-input" value={reason} onChange={(e) => setReason(e.target.value)} />
                    <label htmlFor="overtimework-reason-input">Reason</label>
                </span>

                <span id="overtimework-no-hours" className="p-float-label p-input-icon-right">
                    <i className="pi pi-clock" />
                    <InputText className='input' id="overtimework-no-hours-input" value={numberOfHours} onChange={(e) => setNumberOfHours(e.target.value)} />
                    <label htmlFor="overtimework-no-hours-input">Number of hours requested</label>
                </span>

                <div id='overtime-work-day'>
                        <label htmlFor="overtime-work-calendar">Calendar</label>
                    <Calendar dateFormat="yy-mm-dd" showIcon id="overtime-work-calendar" className='input' value={date} onChange={(e) => setDate(e.value)  }></Calendar>
                </div>

                <div id='overtime-work-btn-submit'>
                    <Button label="Create request" className="p-button-raised p-button-rounded" onClick={submitData}/>
                </div>


            </div>
      </div>
    )

}

export default OvertimeWorkRequestForm;