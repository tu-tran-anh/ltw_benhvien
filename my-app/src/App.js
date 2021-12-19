import './index.css';
import { Home } from "./pages";
import {Header,DoctorTable,NurseTable,PatientTable,DiseasesTable,MedicineTable,ExamTable,DiseasesMonth} from "./compoments/index"
import { Link, Switch, BrowserRouter as Router, Route, Redirect } from "react-router-dom"


function App() {
  return (
    <Router>
      <div>
        <Header></Header>
        <Switch>
          <Route exact path="/">
            <Home></Home>
          </Route>
          <Route exact path="/login">
            <div>LOGIN PAGE</div>
          </Route>
          <Route exact path="/doctor">
            <DoctorTable></DoctorTable>
          </Route>
          <Route exact path="/nurse">
            <NurseTable></NurseTable>
          </Route>
          <Route exact path="/patient">
            <PatientTable></PatientTable>
          </Route>
          <Route exact path="/diseases">
            <DiseasesTable></DiseasesTable>
          </Route>
          <Route exact path="/medicine">
            <MedicineTable></MedicineTable>
          </Route>
          <Route exact path="/examination">
            <ExamTable></ExamTable>
          </Route>
          <Route exact path="/diseasesmonth">
            <DiseasesMonth></DiseasesMonth>
          </Route>
          <Route path="*">
            <h1>Not Found 404</h1>
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
