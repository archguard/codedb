import { Button, Layout, Menu, MenuProps } from 'antd'
import React, { useState } from 'react'
import { NavLink, Outlet } from 'react-router-dom'
import { DownOutlined } from '@ant-design/icons'
import styled from '@emotion/styled'
import { useTranslation } from 'react-i18next'

const { Header, Content, Footer } = Layout

export default function DefaultLayout() {
  const { t } = useTranslation()

  const [current, setCurrent] = useState('dashboard')

  const items: MenuProps['items'] = [
    {
      label: <NavLink to='/'>{t('dashboard')}</NavLink>,
      key: 'dashboard'
    },
    {
      label: <NavLink to='/queries'>{t('queries')}</NavLink>,
      key: 'queries'
    },
    {
      label: <NavLink to='/alerts'>{t('alerts')}</NavLink>,
      key: 'alerts'
    },
    {
      label: <NavLink to='/analysis'>{t('analysis')}</NavLink>,
      key: 'analysis'
    },
    {
      label: (
        <Button type='primary'>
          {t('create')} <DownOutlined />
        </Button>
      ),
      key: 'SubMenu',
      children: [
        {
          label: t('alerts'),
          key: 'create:alerts'
        },
        {
          label: t('queries'),
          key: 'create:queries'
        },
        {
          label: t('analysis'),
          key: 'create:analysis'
        }
      ]
    },
    {
      label: <NavLink to='/fitness'>{t('fitness')}</NavLink>,
      key: 'fitness',
      disabled: true
    }
  ]

  const onClick: MenuProps['onClick'] = (e) => {
    setCurrent(e.key)
  }

  return (
    <FullLayout>
      <Layout>
        <StyledHeader>
          <Menu onClick={onClick} selectedKeys={[current]} mode='horizontal' items={items} />
        </StyledHeader>
        <Content>
          <Outlet />
        </Content>

        <Footer style={{ textAlign: 'center' }}>CodeDB©2023 Created by Thoughtworks & ArchGuard Team.</Footer>
      </Layout>
    </FullLayout>
  )
}

const FullLayout = styled(Layout)`
  min-height: 100vh;
`
const StyledHeader = styled(Header)`
  padding-inline: 0 !important;
`
