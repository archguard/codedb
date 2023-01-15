import React from 'react'
import { Route, Routes } from 'react-router-dom'
import { ConfigProvider } from 'antd'
import { Helmet } from 'react-helmet'

import './App.css'
import DefaultLayout from './layout/DefaultLayout'
import { NoMatch } from './pages/NoMatch'
import { Dashboard } from './pages/dashboard/Dashboard'
import { color } from './theme/color'
import 'antd/dist/reset.css'
import Alerts from './pages/alerts/Alerts'
import Queries from './pages/queries/Queries'
import { useTranslation } from 'react-i18next'
import './i18n/i18n-config'

function App() {
  const { t } = useTranslation()

  return (
    <ConfigProvider theme={{ token: color }}>
      <div className='App'>
        <Helmet>
          <meta charSet='utf-8' />
          <title>{t('title')}</title>
        </Helmet>
        <Routes>
          <Route path='/' element={<DefaultLayout />}>
            <Route path='/' element={<Dashboard />} />
            <Route path='/alerts' element={<Alerts />} />
            <Route path='/queries' element={<Queries />} />
            <Route path='*' element={<NoMatch />} />
          </Route>
        </Routes>
      </div>
    </ConfigProvider>
  )
}

export default App
