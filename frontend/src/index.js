import React from 'react';
import ReactDOM from 'react-dom/client';
import { Routes, Route, BrowserRouter} from 'react-router-dom'
import 'primereact/resources/themes/lara-light-indigo/theme.css';  //theme
import 'primereact/resources/primereact.min.css';                  //core css
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import LogInForm from './components/LogInForm';
import CreateAccountForm from './components/CreateAccountForm'
import PageNotFound from './components/PageNotFound'
import EmployeeDashBoard from './components/EmployeeInterface/EmployeeDashBoard'
import MyRequests from './components/EmployeeInterface/MyRequests'
import OvertimeWorkRequestForm from './components/EmployeeInterface/OvertimeWorkRequestForm';
import Profile from './components/Profile'
import EmployeesRequests from './components/ManagerInterface/EmployeesRequests'
import ManagerDashBoard from './components/ManagerInterface/ManagerDashBoard'
import DepartmentMembers from './components/ManagerInterface/DepartmentMembers'
import DepartmentStatistics from './components/ManagerInterface/DepartmentStatistics';
import ReportAProblem from './components/ReportAProblem';



const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route  path='/' element={<LogInForm/>}/>
      <Route  path='/create-account' element={<CreateAccountForm/>}/>
      <Route  path='/employee-dashboard' element={<EmployeeDashBoard/>}/>
      <Route  path='/employee-overtime-work-request' element={<OvertimeWorkRequestForm/>}/>
      <Route  path='/employee-early-work-request' element={<OvertimeWorkRequestForm/>}/>
      <Route  path='/employee-my-requests' element={<MyRequests/>}/>
      <Route  path='/employees-requests' element={<EmployeesRequests/>}/>
      <Route  path='/profile' element={<Profile/>}/>
      <Route  path='/manager-dashboard' element={<ManagerDashBoard/>}/>
      <Route  path='/department-members' element={<DepartmentMembers/>}/>
      <Route  path='/department-statitstics' element={<DepartmentStatistics/>}/>
      <Route  path='/report-a-problem' element={<ReportAProblem/>}/>
      <Route  path='*' element={<PageNotFound/>}/>
   </Routes>
  </BrowserRouter>,
);


