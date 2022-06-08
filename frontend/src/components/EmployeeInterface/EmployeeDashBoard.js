
import { useState, useEffect,useRef } from 'react';
import '../../styles/EmployeeInterface/EmployeeDashBoard.css'
import EmployeeNavBar from './EmployeeNavBar'
import userStore from "../../UserStore";
import { Knob } from 'primereact/knob';

function EmployeeDashBoard() {
    const [level, setLevel] = useState(1)
    const [progress, setProgress] = useState(0)


    useEffect(() => {
      getExperience()
    },[]);

    const getExperience = async()=>{
      setLevel(userStore.employee.level)
      setProgress(userStore.employee.experience)
    }

    return (
        <div id="employee-dashboard-container-flex">
          <EmployeeNavBar id='employee-dashboard-menu'/>
            <div id="employee-dashboard-container-grid">
                
                <div id='employee-header-dashboard'>
                  <h1>Homepage</h1>
                  <h3>A place where you can track your working hours</h3>
                </div>

                <div id='employee-experience-section'>
                  <Knob valueColor={"#ff6600"} rangeColor={"lightgrey"} value={Math.trunc(progress/(level))} />
                  <div id='employee-experience-text'>
                    <h3>Level: {level}</h3>
                    <h3>Experience until next level: {(level*100-progress)}</h3>
                  </div>
                </div>

                <div id='employee-requests-section'>
                  <div class='btn-employee'></div>
                  <div class='btn-employee'></div>
                  <div class='btn-employee'></div>
                </div>
            </div>
        </div>
    )

}

export default EmployeeDashBoard;