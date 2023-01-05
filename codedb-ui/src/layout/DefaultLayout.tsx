import { Breadcrumb, Layout, Menu, theme } from "antd";
import React, { useState } from "react";
import { NavLink, Outlet } from "react-router-dom";

const { Content, Footer, Sider } = Layout;

export default function DefaultLayout() {
  const [collapsed, setCollapsed] = useState(false);
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <Layout style={ { minHeight: '100vh', background: colorBgContainer } }>
      <Sider collapsible collapsed={ collapsed } onCollapse={ (value) => setCollapsed(value) }>
        <Menu>
          <Menu.Item key="1">
            <NavLink to="/">Home</NavLink>
          </Menu.Item>
          <Menu.Item key="2">
            <NavLink to="/dashboard">My Dashboard</NavLink>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout>
        <Breadcrumb></Breadcrumb>
        <Content>
          <Outlet/>
        </Content>

        <Footer style={ { textAlign: 'center' } }>CodeDB ©2023 Created by ArchGuard</Footer>
      </Layout>
    </Layout>
  );
}
