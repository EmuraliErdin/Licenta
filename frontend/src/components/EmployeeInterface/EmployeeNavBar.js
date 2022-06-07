import {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom'
import { GrLogout } from 'react-icons/gr';
import { Button } from 'primereact/button';
import { Menubar } from 'primereact/menubar';
import '../../styles/EmployeeInterface/EmployeeDashBoard.css'
import userStore from '../../UserStore'

function EmployeeNavBar() {

   const navigate = useNavigate()
   let newTab = {}
   const [menuItems, setMenuItems] = useState([
        {
           label:'Home',
           icon:'pi pi-fw pi-home',
           command: () => navigate('/employee-dashboard')
        },  
        {
           label:'Profile',
           icon:'pi pi-fw pi-user',
           command: () => navigate('/profile')
        },
        {
           label:'Make a request',
           icon:'pi pi-fw pi-calendar',
           items:[
              {
                 label:'I worked overtime',
                 icon:'pi pi-fw pi-calendar-plus',
                 command: () => navigate('/employee-overtime-work-request')
              },
              {
                 label:'I want to leave early',
                 icon:'pi pi-fw pi-calendar-minus',
                 command: () => navigate('/employee-early-work-request')
              }
           ]
        },
        {
           label:'My requests',
           icon:'pi pi-fw pi-folder',
           command: () => navigate('/employee-my-requests')
        }
     ])

     useEffect(()=>{
         getAccesses()
     },[])

     const getAccesses = async () =>{
         try{
               const responseAccess = await fetch(`/api/employees/${userStore.employee.id}/roles`)
               const access = await responseAccess.json()
               if(responseAccess.status==200 && (new Date().getTime() < new Date(access.endDate).getTime())){
                  setMenuItems([
                    {
                     label:'Home',
                     icon:'pi pi-fw pi-home',
                     command: () => navigate('/employee-dashboard')
                    },  
                    {
                       label:'Profile',
                       icon:'pi pi-fw pi-user',
                       command: () => navigate('/profile')
                    },
                    {
                       label:'Make a request',
                       icon:'pi pi-fw pi-calendar',
                       items:[
                          {
                             label:'I worked overtime',
                             icon:'pi pi-fw pi-calendar-plus',
                             command: () => navigate('/employee-overtime-work-request')
                          },
                          {
                             label:'I want to leave early',
                             icon:'pi pi-fw pi-calendar-minus',
                             command: () => navigate('/employee-early-work-request')
                          }]
                     },
                     {
                        label:'My requests',
                        icon:'pi pi-fw pi-folder',
                        command: () => navigate('/employee-my-requests')
                     },
                     {
                        label:'Employees requests',
                        icon:'pi pi-fw pi-calendar',
                        command: () => navigate('/employees-requests')
                     },
                     {
                        label:'Report a problem',
                        icon:'pi pi-fw pi-bolt',
                        command: () => navigate('/report-a-problem')
                     }
                  ])
               }
         } catch(err){
            return err;
         }

     }
     const getEmployeeName = () =>{
      return userStore.employee.firstName +" "+  userStore.employee.lastName
     }

     const btnLogOff = <Button className="p-button-text" label={getEmployeeName()} type="text" icon="FiLogOut" onClick={() => navigate('/')}>&nbsp;<GrLogout/></Button>;

    return (
        <>
            <Menubar id='employee-menu' model={menuItems} end={btnLogOff}/>
        </>
    )

}

export default EmployeeNavBar;