import React from 'react';
import { Route, Routes } from "react-router-dom";
import { ConfigProvider } from "antd";

import './App.css';
import DefaultLayout from "./layout/DefaultLayout";
import { NoMatch } from "./pages/NoMatch";
import { Dashboard } from "./pages/dashboard/Dashboard";
import { color } from "./theme/color";
import 'antd/dist/reset.css';
import Alerts from './pages/alerts/Alerts';
import Queries from "./pages/queries/Queries";

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
            <Route path="/" element={ <Dashboard/> }/>
            <Route path="/alerts" element={ <Alerts/> }/>
            <Route path="/queries" element={ <Queries/> }/>
            <Route path="*" element={ <NoMatch/> }/>
          </Route>
        </Routes>
      </div>
    </ConfigProvider>
  );
}

export default App
