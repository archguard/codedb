import React, { forwardRef, useCallback, useEffect, useImperativeHandle, useLayoutEffect, useRef } from "react";
import { EChartOption, ECharts, init } from "echarts";

interface EchartProps {
  width: number;

  height: number;
  /**
   * the echart option
   */
  option: EChartOption;

  ref: React.Ref<{ instance: () => ECharts | undefined }>;
}

function Echart(props: EchartProps) {
  const { width, height, option } = props;

  const elemRef = useRef<HTMLDivElement>(null);
  const echartRef = useRef<ECharts>();

  useEffect(() => {
    if (!elemRef.current) {
      return
    }

    if (!echartRef.current) {
      echartRef.current = init(elemRef.current!);
    }

    echartRef.current.setOption(props.option, true);
  });

  useImperativeHandle(props.ref, () => ({
    instance() {
      return echartRef.current;
    }
  }));

  const handleResize = useCallback((width: number, height: number) => {
    if (echartRef.current) {
      echartRef.current.resize({ width, height });
    }
  }, []);

  useLayoutEffect(() => {
    handleResize(width, height)
  }, [width, height, handleResize]);

  return (
    <div className={ "echart" } ref={ elemRef } style={ { width, height } }/>
  )
}

export default forwardRef(Echart)
