import { Button, Layout, Menu, MenuProps } from "antd";
import React, { useState } from "react";
import { NavLink, Outlet } from "react-router-dom";
import { DownOutlined } from "@ant-design/icons";

const { Header, Content, Footer } = Layout;

export default function DefaultLayout() {
  const [current, setCurrent] = useState('dashboard');
  const items: MenuProps['items'] = [

    {
      label: <NavLink to="/dashboard">My Dashboard</NavLink>,
      key: 'dashboard',
    },
    {
      label: <NavLink to="/">Queries</NavLink>,
      key: 'queries',
      disabled: true
    },
    {
      label: <NavLink to="/">Alerts</NavLink>,
      key: 'alerts',
      disabled: true
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
    <Layout style={ { minHeight: '100vh' } }>
      <Layout>
        <Header className="header">
          <a className="logo">
            <img src={ require('../assets/images/logo.svg').default } alt="logo"/>
          </a>

          <Menu onClick={ onClick } selectedKeys={ [current] } mode="horizontal" items={ items }/>
        </Header>
        <Content>
          <Outlet/>
        </Content>

        <Footer style={ { textAlign: 'center' } }>CodeDB ©2023 Created by ArchGuard</Footer>
      </Layout>
    </Layout>
  );
}
