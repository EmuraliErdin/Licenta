import userStore from '../../UserStore'
import EmployeeNavBar from '../EmployeeInterface/EmployeeNavBar'
import { useState, useRef, useEffect } from 'react';
import '../../styles/ManagerInterface/DepartmentStatistics.css'
import React, { Component } from 'react';
import { Chart } from 'primereact/chart';
import ManagerNavBar from './ManagerNavBar';

function DepartmentStatistics() {

    const toast = useRef(null);

    const [data, setData] = useState('');

    useEffect(()=>{
        getDataForChart()
    },[])

    const getDataForChart = async () =>{
        let currentYear = new Date().getFullYear();
        const responseRequests = await fetch(`/api/getFreeHoursOfYear/${currentYear}/${userStore.employee.departmentId}`)
        let obj = await responseRequests.json()
        
        setData({
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July','August','September','October', 'November', 'December'],
            datasets: [
                {
                    label: 'Ahead of time leave hours',
                    backgroundColor: '#42A5F5',
                    data: obj.earlyLeave
                },
                {
                    label: 'Overtime hours worked',
                    backgroundColor: '#f9ac1b',
                    data: obj.overtime
                }
            ]
        })
    }

    const getNavBar = () => {
        if(userStore.employee.isManager===false){
            return <EmployeeNavBar id='employee-requests-menu'/>
        }
        else {
            return <ManagerNavBar id='manager-requests-menu'/>
        }
    }


    return (
        <div className="fadein animation-duration-1000" id='department-statistics-container-flex'>     
            {getNavBar()}
            <div id='department-statistics-container-grid'>
                <h3 id='chart-header'>This chart shows how many hours the employees work by month</h3>
                <Chart id='chart' type="bar" data={data}  />
            </div>
        </div>
    )
}

export default DepartmentStatistics;