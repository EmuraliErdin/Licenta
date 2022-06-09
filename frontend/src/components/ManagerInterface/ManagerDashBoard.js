import { useState, useEffect, useRef } from 'react';
import '../../styles/ManagerInterface/ManagerDashBoard.css'
import userStore from "../../UserStore";
import {useNavigate} from 'react-router-dom'
import ManagerNavBar from './ManagerNavBar';
import { Knob } from 'primereact/knob';
import {getEmployeeFromSession} from '../../functions'

function ManagerDashBoard() {
    const [level, setLevel] = useState(1)
    const [progress, setProgress] = useState(0)
    const [numberOfPendingRequests, setNumberOfPendingRequests] = useState(0)
    const navigate = useNavigate()

    useEffect(() => {
      getExperience()
      getNumberOfPendingRequests()


    },[]);

    const getNumberOfPendingRequests = async () =>{
      try{
        const responseNumber = await fetch(`/api/numberOfRequestsPerDepartment/${userStore.employee.departmentId}`)
        const number = await responseNumber.json();
        setNumberOfPendingRequests(number.numberOfRequests)
      } catch (err) {
        console.warn(err);
    }
        

    }

    const getExperience = async () => {
      setLevel(userStore.employee.level+1)
      setProgress(userStore.employee.experience)
    }

    return (
        <div id="manager-dashboard-container-flex">
          <ManagerNavBar id='manager-dashboard-menu'/>
            <div id="manager-dashboard-container-grid">
                
                <div id='manager-header-dashboard'>
                  <h1>Homepage</h1>
                  <h3>A place where you can track your working hours</h3>
                </div>

                <div id='manager-experience-section'>
                  <Knob valueColor={"#ff6600"} rangeColor={"lightgrey"} value={Math.trunc(progress/(level))} />
                  <div id='manager-experience-text'>
                    <h3>Level: {level}</h3>
                    <h3>Experience until next level: {(level*100-progress)}</h3>
                  </div>
                </div>

                <div id='manager-requests-section'>
                  <div className='btn-manager'>
                    <h3>Salut</h3>
                    <h4 id='access-this-page'>Access this page</h4>
                  </div>
                  <div className='btn-manager' onClick={() => navigate('/employees-requests')} >
                    <h4>You have {""+numberOfPendingRequests} pending requests</h4>
                    <h4 id='access-this-page'>Access this page</h4>
                  </div>
                  <div className='btn-manager' onClick={() => navigate('/report-a-problem')}>
                    <h4>Saw a problem? Report it!</h4>
                    <h4 id='access-this-page'>Access this page</h4>
                  </div>
                </div>
            </div>
        </div>
    )

}

export default ManagerDashBoard;