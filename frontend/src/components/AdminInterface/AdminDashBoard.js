import { useState, useEffect, useRef } from 'react';
import '../../styles/AdminInterface/AdminDashBoard.css'
import userStore from "../../UserStore";
import {useNavigate} from 'react-router-dom'

import { Knob } from 'primereact/knob';
import {getEmployeeFromSession} from '../../functions'

function AdminDashBoard() {


    return (
        <div id="admin-dashboard-container-flex">
          {/* <ManagerNavBar id='manager-dashboard-menu'/> */}
            <div className="fadein animation-duration-1000" id="admin-dashboard-container-grid">
                
                <div id='admin-header-dashboard'>
                  <h1>Homepage</h1>
                  <h3>A place where you can track your working hours</h3>
                </div>

            </div>
        </div>
    )

}

export default AdminDashBoard;