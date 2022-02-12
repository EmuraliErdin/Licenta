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

function Meetings() {
  const [description, setDescription] = useState("");
  const [url, setUrl] = useState("");
  const [date, setDate] = useState("");
  const [meetings,setMeetings] = useState([])
  const navigateTo = useNavigate()

  const addMeeting = async () => {
    try {
      const response = await fetch(`${SERVER}/meetings`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          description,
          url,
          date,
        }),
      });
      getMeetings()
    } catch (e) {
      throw e;
    }
  };

  const getMeetings = async()=>{
    const response = await fetch(`${SERVER}/meetings`);
    let data = await response.json();
    setMeetings(data)
  }

  const textEditor = (options) => {
    return <InputText type="text" value={options.value} onChange={(e) => options.editorCallback(e.target.value)} />;
}

const dateEditor = (options) => {
    return <Calendar value={options.value} onChange={(e) => options.editorCallback(e.target.value)}/>
  }

const onRowEditComplete = (e) => {

    updateMeeting(e.data, e.newData)
}

const updateMeeting = async(meeting, newMeeting) =>{

    try{
        const response = await fetch(`${SERVER}/meetings/${meeting.id}`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(newMeeting),
          });
          getMeetings();
        
    }
    catch(e)
    {
        throw e
    }

}

const deleteM = (ing) => {
    return (
        <Button onClick={() => deleteMeeting(ing)} icon="pi pi-times" className="p-button-rounded p-button-danger p-button-text" tooltip="Delete" />

    )
}

const deleteMeeting = async (meeting)=>{
    
    try {
        const response = await fetch(`${SERVER}/meetings/${meeting.id}`, {
            method: "DELETE"
        })
        if (!response.ok) {
            throw response
        }
        getMeetings();
    } catch (err) {
        console.warn(err)
    }
}

const seeChild = (data) =>
{
    const id = data.data.id;
    navigateTo(`/${id}/participants`)
}

useEffect(() => {getMeetings()}, [])

  return (
    <div>

        <div>
            <DataTable value={meetings} scrollable scrollHeight="flex" onRowEditComplete={onRowEditComplete} editMode="row" rowHover="true" onRowClick={e => seeChild(e)} >
            <Column field="id" header="Id" headerStyle={{ width: '15%', minWidth: '8rem' }}></Column>
            <Column field="description" header="Description" headerStyle={{ width: '15%', minWidth: '8rem' }} editor={(options) => textEditor(options)}></Column>
            <Column field="url" header="URL" headerStyle={{ width: '15%', minWidth: '8rem' }} editor={(options) => textEditor(options)}></Column>
            <Column field="date" header="Date" headerStyle={{ width: '15%', minWidth: '8rem' }} editor={(options) => dateEditor(options)}></Column>
            <Column body={deleteM} headerStyle={{ width: '15%', minWidth: '8rem' }}></Column>
            <Column rowEditor headerStyle={{ width: '15%', minWidth: '8rem' }} bodyStyle={{ textAlign: 'center' }} />

            </DataTable>
        </div>



        <div id = 'inputuri'>
            <span className="p-float-label">
                <InputText id="description" value={description} onChange={(e) => setDescription(e.target.value)}/>
                <label htmlFor="description">Description</label>
            </span>

            <span className="p-float-label">
                <InputText id="url" value={url} onChange={(e) => setUrl(e.target.value)}/>
                <label htmlFor="url">URL</label>
            </span>

            <span className="p-float-label mt-4">
                <Calendar id="date" value={date} onChange={(e) => setDate(e.value)}></Calendar>
                <label htmlFor="date">Date</label>
                </span>

                <Button label="Add" onClick={() => addMeeting ()}className="p-button-success m-3 mt-1"/>
            </div>

    </div>
  );
}

export default Meetings;
