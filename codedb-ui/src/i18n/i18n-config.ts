import i18next from 'i18next'
import { initReactI18next } from 'react-i18next'
import LanguageDetector from 'i18next-browser-languagedetector'

import en from './locales/en.json'
import zhCn from './locales/zh-cn.json'

export const resources = {
  en: {
    translation: en
  },
  zh: {
    translation: zhCn
  }
}

i18next
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    lng: 'zh',
    debug: true,
    resources,
    // fallbackLng: 'en',
    react: {
      useSuspense: false
    },
    detection: {
      order: ['querystring', 'navigator', 'localStorage'],
      lookupQuerystring: 'lang'
    },
    interpolation: {
      escapeValue: false
    }
  })
