import React from 'react'
import Echart from '../Echart'
import { RadarOptions, transformProps } from './transform-props'

interface RadarProps {
  option: RadarOptions
}

export default function Radar(props: RadarProps) {
  return <Echart height={480} width={480} option={transformProps(props.option)} />
}
