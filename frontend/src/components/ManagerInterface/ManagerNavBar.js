import {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom'
import { GrLogout } from 'react-icons/gr';
import { Button } from 'primereact/button';
import { Menubar } from 'primereact/menubar';
import '../../styles/EmployeeInterface/EmployeeDashBoard.css'
import userStore from '../../UserStore'

function ManagerNavBar() {

   const navigate = useNavigate()
   let newTab = {}
   const [menuItems, setMenuItems] = useState([
        {
           label:'Home',
           icon:'pi pi-fw pi-home',
           command: () => navigate('/manager-dashboard')
        },  
        {
           label:'Profile',
           icon:'pi pi-fw pi-user',
           command: () => navigate('/profile')
        },
        {
         label:'Requests',
         icon:'pi pi-fw pi-users',
         items:[
            {
               label:'Department members',
               icon:'pi pi-fw pi-users',
               command: () => navigate('/department-members')
            },
            {
               label:'Employees requests',
               icon:'pi pi-fw pi-folder',
               command: () => navigate('/employees-requests')
            },
         ]
         },
        {
         label:'Statistics',
         icon:'pi pi-fw pi-chart-bar',
         command: () => navigate('/department-statitstics')
        },
        {
           label:'Report a problem',
           icon:'pi pi-fw pi-bolt',
           command: () => navigate('/report-a-problem')
        }

     ])

     const getEmployeeName = () =>{
      return userStore.employee.firstName +" "+  userStore.employee.lastName
     }

     const btnLogOff = <Button className="p-button-text" label={getEmployeeName()} type="text" icon="FiLogOut" onClick={() => navigate('/')}>&nbsp;<GrLogout/></Button>;

    return (
        <>
            <Menubar id='manager-menu' model={menuItems} end={btnLogOff}/>
        </>
    )

}

export default ManagerNavBar;