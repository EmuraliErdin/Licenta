import { useState, useEffect } from 'react';
import {Navigate, useNavigate} from 'react-router-dom'
import React, { useRef } from 'react';
import { InputText } from "primereact/inputtext";
import '../styles/CreateAccountForm.css'
import { Button } from "primereact/button";
import { Password } from 'primereact/password';
import { Toast } from 'primereact/toast';
import { SelectButton } from 'primereact/selectbutton';
import { Dropdown } from 'primereact/dropdown';
import { Dialog } from 'primereact/dialog';

import userStore from "../UserStore";

function CreateAccountForm() {
    const [password, setPassowrd] = useState("");
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] =useState("");
    const [lastName, setLastName] =useState("");
    const [selectedDepartment, setSelectedDepartment]=useState(null)
    const [departmentItems, setDepartmentItems]=useState([])
    const [isManager, setIsManager]=useState(false);
    const [code,setCode]=useState("");
    const [dialog, setDialog] = useState(false);
    let departmentItemsBuilder=[];
    const toast = useRef(null);
    const navigate = useNavigate();
    const dialogFuncMap = {
        'dialog': setDialog,
    }

    const isManagerResponseItems=[
        {label: 'Yes', value: true},
        {label: 'No', value: false}
    ]

    const showWarning = () =>{
        toast.current.show({severity: 'warn', summary: 'Fields not filled', detail: 'All the fields are mandatory!', life: 3000});
    }
    const showSuccess = () =>{
        toast.current.show({severity: 'success', summary: 'Account created', detail: 'The account has been created', life: 1500});
    }

    const verifyData = ()=>{
        if(firstName.length <=3 || lastName.length<=3 || (email.length<=3 && email.includes("@")!=true) || password.length<=3 || selectedDepartment==null){
            return false;
        }
        return true;
    }

    
    useEffect(() => {
        getDepartments();
      },[]);

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

    const v = (e)=>{
        setIsManager(e)
    }

    const submitDataFirstPart = async ()=>{
        if(verifyData()==true){
            const response = await fetch('/api/firstPartCreateAccount', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email,
                    password,
                    firstName
                }),
            });

            setDialog(true);

        }
        else{
            showWarning();
        }
    }

    const submitDataSecondPart = async ()=>{
        const response = await fetch('/api/secondPartCreateAccount', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "firstName":firstName,
                "lastName":lastName,
                "email":email,
                "password":password,
                "departmentId":selectedDepartment,
                "isManager":isManager,
                "code":code
            }),
        });
        
        setDialog(false)
        showSuccess();
        setTimeout(()=>{navigate('/');},2000);
        
    }
    const renderFooter = (name) => {
        return (
            <div>
                <Button id='create-acc-dialog-cancel'label="Cancel" icon="pi pi-times" onClick={() => onHide(name)} className="p-button-text" />
                <Button id='create-acc-dialog-ok' label="Ok" icon="pi pi-check" onClick={() => submitDataSecondPart()} autoFocus />
            </div>
        );
    }

    const onHide = (name) => {
        dialogFuncMap[`${name}`](false);
    }

    return (
        <div id="create-acc-container-grid">

            <Dialog id="create-acc-dialog" header="Header" visible={dialog} style={{ width: '50vw' }} footer={renderFooter('dialog')} onHide={() => onHide('dialog')}>
                <h3 id="create-acc-dialog-text">You received a code in your email. Take that code and input it here please!</h3>
                <InputText id = "create-acc-dialog-code" placeholder = "4-digit code" value = {code} onChange = {(e) => setCode(e.target.value)} />
            </Dialog>

            <div className="create-acc-container">
                <Toast ref={toast} position='top-right' />
                <h1 id="create-acc-header-text">Create your account</h1>
                <InputText id = "create-acc-first-name" placeholder = "First Name" value = {firstName} onChange = {(e) => setFirstName(e.target.value)} /> 
                <InputText id = "create-acc-last-name" placeholder = "Last name" value = {lastName} onChange = {(e) => setLastName(e.target.value)} /> 
                <InputText id = "create-acc-email" placeholder = "Email" value = { email} onChange = {(e) => setEmail(e.target.value)} /> 
                <Password id = "create-acc-pass" placeholder = "Password" value = {password} onChange = {(e) => setPassowrd(e.target.value)} toggleMask feedback={false}/>
                <div id="create-acc-input-is-manager">
                    <h3>Are you the manager for the department selected?</h3>
                    <SelectButton id="create-acc-is-manager" value={isManager} options={isManagerResponseItems} onChange={(e) => v(e.value)}></SelectButton>
                </div>
                <Dropdown id="create-acc-dropdown" value={selectedDepartment} options={departmentItems} onChange={(e) => setSelectedDepartment(e.value)} placeholder="Select a Department"/>
                <Button id="create-acc-btn-submit" label="Create account" onClick={submitDataFirstPart} />
            </div>
        </div>
    )

}

export default CreateAccountForm;