import { useState, useEffect } from 'react';
import '../styles/Inventory.css'
import EmployeeNavBar from './EmployeeInterface/EmployeeNavBar'
import ManagerNavBar from './ManagerInterface/ManagerNavBar'
import userStore from "../UserStore";

function Inventory() {

    const [prizes, setPrizes] = useState([])
    const [prizesCounter, setPrizesCounter] = useState(false)
    
    const getNavBar = () => {
        if(userStore.employee.isManager==false){
            return <EmployeeNavBar id='employee-profile-menu'/>
        }
        else {
            return <ManagerNavBar id='manager-profile-menu'/>
        }
    }

    const getPrizes = async () =>{
        const responsePrizes = await fetch(`/api/employees/${userStore.employee.id}/items`)
        let prizesOwned = await responsePrizes.json()
        if(prizesOwned.length==0){
            setPrizesCounter(true)
        }
        setPrizes(prizesOwned)
    }

    useEffect(()=>{
        getPrizes()
    }
    ,[])

    const divList = prizes.map((item)=>{
        return <div className='prize-item'>
                    <h3>{item.name}</h3>
                    <h3>Description: {item.description}</h3>
                    <h3>Level: {item.necessaryLevel}</h3>
               </div>
    })
    return (
        <div id="inventory-container-flex">
            {getNavBar()}
            <div id="inventory-container-grid">
                {/* <h2 id='no-prizes-message'>You don't own any prizes yet</h2> */}
                
                {(prizesCounter)? (<h2 id='no-prizes-message'>You don't own any prizes yet</h2>) : (divList)}
            </div>
        </div>
    )

}

export default Inventory;