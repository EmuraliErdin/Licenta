import { Route, Routes } from 'react-router-dom'
import "primereact/resources/themes/lara-light-indigo/theme.css"; 
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import 'primeflex/primeflex.css';
import Meetings from './Meetings';
import Participants from './Participants';
import './App.css'


function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Meetings/>}/>
        <Route path="/:id/participants" element={<Participants/>}/>
      </Routes>
    </div>
  );
}

export default App;
