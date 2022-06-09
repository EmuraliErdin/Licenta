import userStore from './UserStore'

const padTo2Digits = (num) => {
    return num.toString().padStart(2, '0');
}

const formatDate = (date) => {
    return [
      date.getFullYear(),
      padTo2Digits(date.getMonth() + 1),
      padTo2Digits(date.getDate()),
    ].join('-');
  }


  const getEmployeeFromSession = async () => {
    const employeeId= window.sessionStorage.getItem('employeeId')
    const response = await fetch(`/api/employees/${employeeId}`)
    let employee = await response.json()
    userStore.employee = employee
  }

  export{
      formatDate, getEmployeeFromSession
  }