import React from 'react';
import './App.css';
import { Route, Routes } from "react-router-dom";
import DefaultLayout from "./layout/DefaultLayout";
import { NoMatch } from "./pages/NoMatch";
import { Dashboard } from "./pages/Dashboard";
import Home from "./pages/Home";
import { ConfigProvider } from "antd";
import { color } from "./theme/color";


function App() {
  return (
    <ConfigProvider
      theme={ {
        token: color,
      } }
    >
      <div className="App">
        <Routes>
          <Route path="/" element={ <DefaultLayout/> }>
            <Route path="/" element={ <Home/> }/>
            <Route path="/dashboard" element={ <Dashboard/> }/>
            <Route path="*" element={ <NoMatch/> }/>
          </Route>
        </Routes>
      </div>
    </ConfigProvider>
  );
}

export default App
