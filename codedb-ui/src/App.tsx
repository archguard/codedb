import React from 'react';
import './App.css';
import { Route, Routes } from "react-router-dom";
import DefaultLayout from "./layout/DefaultLayout";
import { NoMatch } from "./pages/NoMatch";
import { Dashboard } from "./pages/Dashboard";


function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={ <DefaultLayout/> }>
          <Route path="/" element={ <Dashboard/> }/>
          <Route path="*" element={ <NoMatch/> }/>
        </Route>
      </Routes>
    </div>
  );
}

export default App
