import {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom'
import { GrLogout } from 'react-icons/gr';
import { Button } from 'primereact/button';
import { Menubar } from 'primereact/menubar';
import '../../styles/EmployeeInterface/EmployeeDashBoard.css'
import userStore from '../../UserStore'
import {getEmployeeFromSession} from '../../functions'

function AdminNavBar() {

   const navigate = useNavigate()

   const [menuItems, setMenuItems] = useState([
        {
           label:'Issue List',
           icon:'pi pi-fw pi-exclamation-triangle',
           command: () => navigate('/admin-dashboard')
        },  
        {
           label:'Department',
           icon:'pi pi-fw pi-home',
           items:[
            {
               label:'Create a new department',
               command: () => navigate('/create-department')
            },
            {
                label:'Rename a department',
                command: () => navigate('/rename-department')
             }]
        },
        {
         label:'Prizes',
         icon:'pi pi-fw pi-star-fill',
         command: () => navigate('/create-prize')
         }
     ])

     const getEmployeeName = () =>{
        return "Admin"
     }

     const btnLogOff = <Button className="p-button-text" label={getEmployeeName()} type="text" icon="FiLogOut" onClick={() => navigate('/')}>&nbsp;<GrLogout/></Button>;

    return (
        <>
            <Menubar id='admin-menu' model={menuItems} end={btnLogOff}/>
        </>
    )

}

export default AdminNavBar;