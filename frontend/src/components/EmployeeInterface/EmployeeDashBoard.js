
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
      const responseExperience = await fetch(`/api/employees/${userStore.employee.id}/experiences`)
      const experienceList = await responseExperience.json()
      const l=getLevel(experienceList)
      const p=getProgress(experienceList)
      setLevel(l)
      setProgress(p)

      const percentage = progress/(level*100)
    }

    const getLevel = (experienceList) => {
      let level = 1
      let xpSum = 0
      for(let i=0;i<experienceList.length;i++) {
         xpSum += experienceList[i].xp

         if(xpSum>=level*100) {
            xpSum = xpSum-level*100
            level += 1
         }
      }
      return level
    }

    const getProgress = (experienceList) =>{
      let level = 1
      let xpSum = 0
      for(let i=0;i<experienceList.length;i++) {
         xpSum += experienceList[i].xp
         
         if(xpSum>=level*100) {
            xpSum = xpSum-level*100
            level += 1
         }
      }
      return xpSum
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