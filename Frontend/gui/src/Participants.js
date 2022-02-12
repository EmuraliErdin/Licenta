import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { InputText } from "primereact/inputtext";
import { Calendar } from 'primereact/calendar';
import { Button } from "primereact/button";
import { useEffect, useState } from "react";
import { useRef } from "react";
import './App.css'
const SERVER = "http://localhost:8080/api";

function Participants() {

    const [name, setName] = useState("");
    const [participants, setParticipants] = useState([]);
    const { id } = useParams()
    

    const textEditor = (options) => {
        return <InputText type="text" value={options.value} onChange={(e) => options.editorCallback(e.target.value)} />;
    }

    const getParticipants = async()=>{
        const response = await fetch(`${SERVER}/meetings/${id}/participants`);
        let data = await response.json();
        setParticipants(data)
    }


    const onRowEditComplete = (e) => {

        updateParticipant(e.data, e.newData)
    }

    const updateParticipant = async(participant, newParticipant) =>{

        try{
            const response = await fetch(`${SERVER}/meetings/${id}/participants/${participant.id}`, {
                method: "PUT",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify(newParticipant),
              });
              getParticipants();
            
        }
        catch(e)
        {
            throw e
        }
    
    }

    const deleteP = (ing) => {
        return (
            <Button onClick={() => deleteParticipant(ing)} icon="pi pi-times" className="p-button-rounded p-button-danger p-button-text" tooltip="Delete" />
    
        )
    }
    
    const deleteParticipant = async (participant)=>{
        
        try {
            const response = await fetch(`${SERVER}/meetings/${id}/participants/${participant.id}`, {
                method: "DELETE"
            })
            if (!response.ok) {
                throw response
            }
            getParticipants();
        } catch (err) {
            console.warn(err)
        }
    }
    
    const addParticipant = async () => {
        try {
          const response = await fetch(`${SERVER}/meetings/${id}/participants`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name
            }),
          });
          getParticipants()
        } catch (e) {
          throw e;
        }
      };

    useEffect(() => {getParticipants()}, [])
    return (
        
    <div>

        <div>
            <DataTable value={participants} scrollable scrollHeight="flex" onRowEditComplete={onRowEditComplete} editMode="row" >
            <Column field="id" header="Id" headerStyle={{ width: '15%', minWidth: '8rem' }}></Column>
            <Column field="name" header="Name" headerStyle={{ width: '15%', minWidth: '8rem' }} editor={(options) => textEditor(options)}></Column>
            <Column body={deleteP} headerStyle={{ width: '15%', minWidth: '8rem' }}></Column>
            <Column rowEditor headerStyle={{ width: '15%', minWidth: '8rem' }} bodyStyle={{ textAlign: 'center' }} />

            </DataTable>
        </div>


        <div id = 'inputuri'>
            <span className="p-float-label">
                <InputText id="name" value={name} onChange={(e) => setName(e.target.value)}/>
                <label htmlFor="name">Name</label>
            </span>

            <Button label="Add" onClick={() => addParticipant()}className="p-button-success m-3 mt-1"/>
        </div>
    </div>
    );
  }
  
  export default Participants;
  