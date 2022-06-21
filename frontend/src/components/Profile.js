import '../styles/Profile.css'
import userStore from '../UserStore'
import EmployeeNavBar from './EmployeeInterface/EmployeeNavBar'
import { InputText } from 'primereact/inputtext';
import { useState, useRef } from 'react';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import ManagerNavBar from './ManagerInterface/ManagerNavBar';

function Profile() {

    const [firstName, setFirstName] = useState(userStore.employee.firstName)
    const [lastName, setLastName] = useState(userStore.employee.lastName)
    const [fnDisabled, setFnDisabled] = useState(true)
    const [lnDisabled, setLnDisabled] = useState(true)
    const [passDisabled, setPassDisabled] = useState(true)
    const [oldPassword,setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const toast = useRef(null)

    const getNavBar = () => {
        if(userStore.employee.isManager==false){
            return <EmployeeNavBar id='employee-profile-menu'/>
        }
        else {
            return <ManagerNavBar id='manager-profile-menu'/>
        }
    }

    
    const onEdit = (setter) =>{
        setter(false)
    }

    const onConfirmChange = async (setter) =>{
        setter(true)
        userStore.employee.firstName = firstName
        userStore.employee.lastName = lastName
        const employee = userStore.employee

        const responseEmployee = await fetch(`/api/employees/${userStore.employee.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                employee
            }),
        });

        if(responseEmployee.status==200){
            showSuccess()
        }
        else{
            showError()
        }
    }

    const onCancelChangeFn = () => {
        setFnDisabled(true)
        setFirstName(userStore.employee.firstName)
    }

    const onCancelChangeLn = () => {
        setLnDisabled(true)
        setLastName(userStore.employee.lastName)
    }

    const onEditPassword = () =>{
        if(passDisabled == true){
            setPassDisabled(false)
        }
        else{
            setPassDisabled(true)
        }
    }

    const onChangePasswordClick = async () =>{
        if(newPassword.length>=5){
        const responseEmployee = await fetch(`/api/employeeChangePassword/${userStore.employee.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                newPassword: newPassword,
                oldPassword: oldPassword
            }),
        });
        
        if (responseEmployee.status == 200) {
            showSuccess()
        } else {
            showError()
        }
    }

    }

    const showSuccess = () => {
        toast.current.show({severity:'success', summary: 'Profile updated!', detail:'Server has recorded the changes', life: 3000})
    }

    const showError = () => {
        toast.current.show({severity:'error', summary: 'Profile not updated!', detail:'Server responded with an error message', life: 3000})
    }
    
    return (
        <div id='profile-container-flex'>
            <Toast ref={toast} />
            {getNavBar()}
            <div id='profile-container-grid' className='fadein animation-duration-1000'>
            
            <div id='profile-fn-input'>
                <InputText value={firstName} disabled={fnDisabled} onChange={(e) => setFirstName(e.target.value)} placeholder="First name"/>
                {(fnDisabled) ? (<span>
                                    <Button  icon="pi pi-pencil" className="p-button-rounded round scalein animation-duration-500" onClick={(e)=>{onEdit(setFnDisabled)}}/>
                                </span>) : (
                                <span>
                                    <Button  icon="pi pi-check" className="p-button-rounded round scalein animation-duration-500" onClick={(e)=>{onConfirmChange(setFnDisabled, setFirstName)}}/>
                                    <Button  icon="pi pi-times" className="p-button-rounded round scalein animation-duration-500" onClick={(e)=>{onCancelChangeFn()}}/>
                                </span>
                            )}
                
            </div>

            <div id='profile-ln-input'>
                <InputText value={lastName} disabled={lnDisabled} onChange={(e) => setLastName(e.target.value)} placeholder="Last name"/>
                {(lnDisabled) ? (<span>
                                    <Button  icon="pi pi-pencil" className="p-button-rounded round scalein animation-duration-500" onClick={(e)=>{onEdit(setLnDisabled)}}/>
                                </span>) : (
                                <span>
                                    <Button  icon="pi pi-check" className="p-button-rounded round scalein animation-duration-500" onClick={(e)=>{onConfirmChange(setLnDisabled, setLastName)}}/>
                                    <Button  icon="pi pi-times" className="p-button-rounded round scalein animation-duration-500" onClick={(e)=>{onCancelChangeLn()}}/>
                                </span>
                            )}
            </div>

            <div id='profile-password-input'>
                <Button label="Change your password" className="p-button-text text" onClick={(e)=>{onEditPassword()}}/>
                {(passDisabled) ? (<span>
                                </span>) : (
                                <div id='profile-psw-input'>
                                    <InputText className='fadeinleft animation-duration-500' id='old-psw' value={oldPassword}  onChange={(e) => setOldPassword(e.target.value)} placeholder="Old password"/>
                                    <InputText className='fadeinleft animation-duration-500' id='new-psw' value={newPassword}  onChange={(e) => setNewPassword(e.target.value)} placeholder="New password"/>
                                    <Button className="p-button-rounded  round fadeinleft animation-duration-500" onClick={(e)=>{onChangePasswordClick()}} label='Change password'/>
                                </div>
                            )}
            </div>

            </div>
        </div>
    )
}

export default Profile;