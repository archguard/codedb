import React from 'react';
import './App.css';
import { Card, Col, Row } from "antd";

function App() {
  return (
    <>
      <div>
        <Row gutter={ [24, 0] }>
          {/*for loop in here */ }
          <Col xs={ 24 } sm={ 24 } md={ 12 } lg={ 6 } xl={ 6 } className="mb-24">
            Title
          </Col>
        </Row>
        <Row gutter={ [24, 0] }>
          <Col xs={ 24 } sm={ 24 } md={ 12 } lg={ 12 } xl={ 10 } className="mb-24">
            <Card bordered={ false } className="criclebox h-full">
              {/*<Echart />*/ }
              EChart
            </Card>
          </Col>
          <Col xs={ 24 } sm={ 24 } md={ 12 } lg={ 12 } xl={ 14 } className="mb-24">
            <Card bordered={ false } className="criclebox h-full">
              {/*<LineChart />*/ }
              LineChart
            </Card>
          </Col>
        </Row>
      </div>
    </>
  )
}

export default App
