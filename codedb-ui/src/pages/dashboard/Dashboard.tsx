import { Card, Col, Row } from "antd";
import React from "react";
import Radar from "../../chart/Radar/Radar";

export function Dashboard() {
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
            <Card bordered={ false }>
              <Radar option={ {} }/>
            </Card>
          </Col>
          <Col xs={ 24 } sm={ 24 } md={ 12 } lg={ 12 } xl={ 14 } className="mb-24">
            <Card bordered={ false }>
              LineChart
            </Card>
          </Col>
        </Row>
      </div>
    </>
  )
}
