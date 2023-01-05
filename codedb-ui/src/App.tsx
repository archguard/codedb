import React from 'react';
import './App.css';
import { Route, Routes } from "react-router-dom";
import DefaultLayout from "./layout/DefaultLayout";
import { NoMatch } from "./pages/NoMatch";
import { Dashboard } from "./pages/Dashboard";
import Home from "./pages/Home";


function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={ <DefaultLayout/> }>
          <Route path="/" element={ <Home/> }/>
          <Route path="/dashboard" element={ <Dashboard/> }/>
          <Route path="*" element={ <NoMatch/> }/>
        </Route>
      </Routes>
    </div>
  );
}

export default App
