import { Button, Layout, Menu, MenuProps } from "antd";
import React, { useState } from "react";
import { Link, NavLink, Outlet } from "react-router-dom";
import { DownOutlined } from "@ant-design/icons";
import styled from "@emotion/styled";

const { Header, Content, Footer } = Layout;

export default function DefaultLayout() {
  const [current, setCurrent] = useState('dashboard');
  const items: MenuProps['items'] = [

    {
      label: <NavLink to="/">My Dashboard</NavLink>,
      key: 'dashboard',
    },
    {
      label: <NavLink to="/queries">Queries</NavLink>,
      key: 'queries',
    },
    {
      label: <NavLink to="/alerts">Alerts</NavLink>,
      key: 'alerts',
    },
    {
      label: <Button type="primary">Create <DownOutlined/></Button>,
      key: 'SubMenu',
      children: [
        {
          label: 'Create Alert',
          key: 'setting:1',
        },
        {
          label: 'Create Query',
          key: 'setting:2',
        }
      ],
    },
  ];


  const onClick: MenuProps['onClick'] = (e) => {
    setCurrent(e.key);
  };

  return (
    <FullLayout style={ { minHeight: '100vh' } }>
      <Layout>
        <StyledHeader>
          <Menu onClick={ onClick } selectedKeys={ [current] } mode="horizontal" items={ items }/>
        </StyledHeader>
        <Content>
          <Outlet/>
        </Content>

        <Footer style={ { textAlign: 'center' } }>CodeDB ©2023 Created by ArchGuard</Footer>
      </Layout>
    </FullLayout>
  );
}

const FullLayout = styled(Layout)`
  min-height: 100vh;
`
const StyledHeader = styled(Header)`
  padding-inline: 0px !important;
`
