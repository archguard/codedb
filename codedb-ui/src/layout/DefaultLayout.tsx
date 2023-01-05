import { Breadcrumb, Layout, Menu } from "antd";
import React, { useState } from "react";
import { NavLink, Outlet } from "react-router-dom";

const { Header, Content, Footer, Sider } = Layout;

export default function DefaultLayout() {
  const [collapsed, setCollapsed] = useState(false);

  return (
    <Layout style={ { minHeight: '100vh' } }>
      <Sider collapsible collapsed={ collapsed } onCollapse={ (value) => setCollapsed(value) }>
        <Menu theme="light" mode="inline">
          <Menu.Item key="1">
            <NavLink to="/">Home</NavLink>
          </Menu.Item>
          <Menu.Item key="2">
            <NavLink to="/dashboard">My Dashboard</NavLink>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout>
        <Header>
          <Breadcrumb></Breadcrumb>
        </Header>
        <Content>
          <Outlet/>
        </Content>

        <Footer style={ { textAlign: 'center' } }>CodeDB ©2023 Created by ArchGuard</Footer>
      </Layout>
    </Layout>
  );
}
