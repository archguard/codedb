import React, { forwardRef, useCallback, useEffect, useImperativeHandle, useLayoutEffect, useRef } from 'react'
import { EChartOption, ECharts, init } from 'echarts'

interface EchartProps {
  width: number

  height: number
  /**
   * the echart option
   */
  option: EChartOption

  ref: React.Ref<{ instance: () => ECharts | undefined }>
}

function Echart(props: EchartProps) {
  const { width, height } = props

  const elemRef = useRef<HTMLDivElement>(null)
  const echartRef = useRef<ECharts>()

  useEffect(() => {
    if (!elemRef.current) {
      return
    }

    if (!echartRef.current) {
      echartRef.current = init(elemRef.current!)
    }

    echartRef.current.setOption(props.option, true)
  })

  useImperativeHandle(props.ref, () => ({
    instance() {
      return echartRef.current
    }
  }))

  const onResize = useCallback((width: number, height: number) => {
    if (echartRef.current) {
      echartRef.current.resize({ width, height })
    }
  }, [])

  useLayoutEffect(() => {
    onResize(width, height)
  }, [width, height, onResize])

  return <div className={'echart'} ref={elemRef} style={{ width, height }} />
}

export default forwardRef(Echart)
