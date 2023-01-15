// @ts-ignore
import i18next from 'i18next'
import { initReactI18next } from 'react-i18next'
import translation from './en/translation.json'

export const resources = {
  en: {
    translation
  }
}

i18next.use(initReactI18next).init({
  lng: 'en',
  debug: true,
  resources
})
