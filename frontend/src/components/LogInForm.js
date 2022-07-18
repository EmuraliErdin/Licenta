import {useState} from "react";
import {useNavigate} from 'react-router-dom'
import React, { useRef } from 'react';
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Password } from 'primereact/password';
import { Dialog } from 'primereact/dialog';
import { Toast } from 'primereact/toast';
import '../styles/LogInForm.css'

import userStore from "../UserStore";

function LogInForm() {
    const [password,setPassowrd] = useState("");
    const [email,setEmail] = useState("");
    const [isLoading,setIsLoading]=useState();
    const [forgotPasswordVisible, setForgotPasswordVisible] = useState(false)
    const [forgotPasswordEmail, setForgotPasswordEmail] = useState('')
    const toast = useRef(null);
    const navigate = useNavigate();

    const submitData  = async () => {
        if(password==='' || email===''){
            showWarning();
            return;
        }

        if(password == 'admin' && email == 'admin') {
            userStore.employee = {id:"1"}
            userStore.isLogged=true;
            navigate('/admin-dashboard')
            return;
        }

        setIsLoading(true);
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email,
                password
            }),
        });

        if(response.status===200){
            
            let person = await response.json();
            userStore.employee=person
            console.log(person);
            userStore.isLogged=true
            
            if(person.isManager===false){
                navigate('/employee-dashboard');
            }
            else if(person.isManager===true){
                navigate('/manager-dashboard')
            }
            
        }
        else if(response.status===404){
            showError();
        }

        setIsLoading(false);
        
    }

    const showError = () => {
        toast.current.show({severity: 'error', summary: '400 Not Found', detail: 'User and password do not match', life: 3000});
    }

    const showWarning = () =>{
        toast.current.show({severity: 'warn', summary: 'Textbox warning', detail: 'Please input your username and password in the required fields', life: 3000});
    }

    const renderFooter = () => {
        return (
            <div>
                <Button label="Cancel" icon="pi pi-times" onClick={() => setForgotPasswordVisible(false) } className="p-button-text" />
                <Button label="Send" icon="pi pi-arrow-up-right" onClick={() => resetPassword()} autoFocus />
            </div>
        );
    }

    const showToast = (severity, summary, detail) => {
        toast.current.show({severity: severity, summary: summary, detail: detail, life: 3000});
    }

    const resetPassword = async () => {
        
        if(forgotPasswordEmail==='' || !forgotPasswordEmail.includes('@')){
            showToast('warn', 'Input warning', 'Please input a valid email address')
            return;
        }

        const responsePassword = await fetch('/api/passwordForgot', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email:forgotPasswordEmail
            })});

        if(responsePassword.ok){
            showToast('success','Sent','If the email is registred, we will send an email containing a password. Please change the password as soon as you can from the profile menu.')
        }else if(!responsePassword.ok){
            showToast('error','Not found','The email was not found');
        }
        setForgotPasswordEmail('')
        setForgotPasswordVisible(false)
    }

    return (
        <div id="login-container-grid">

            <Dialog header="Please input your email in the textbox" visible={forgotPasswordVisible} style={{ width: '50vw' }} footer={renderFooter()} onHide={() => setForgotPasswordVisible(false)}>
                <span className="p-input-icon-left" id="input-forgot-password">
                    <InputText  value={forgotPasswordEmail} onChange={(e) => setForgotPasswordEmail(e.target.value)} placeholder="Email" />
                </span>
            </Dialog>

        <div  className="login-container fadein animation-duration-1000">
                <Toast ref={toast} position='top-right' />
                <InputText id = "login-email" placeholder = "Account name" value = {email} onChange = {(e) => setEmail(e.target.value)} /> 
                <Password id = "login-pass" placeholder = "Password" value = {password} onChange = {(e) => setPassowrd(e.target.value)} toggleMask feedback={false}/> 
                <Button className='p-button-text animation-iteration-infinite' id="login-create-account" onClick={()=>navigate('/create-account')} label="Sign up" />
                <Button className='p-button-text' id="login-forgot-password" onClick={()=>setForgotPasswordVisible(true)} label="I forgot my password" />
                <Button id="login-btn-submit" label="Authenticate"  loading={isLoading}  onClick={submitData}  />
            </div>
        </div>
    )

}

export default LogInForm;