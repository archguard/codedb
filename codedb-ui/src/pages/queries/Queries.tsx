import React from 'react'
import { tableFromArrays } from 'apache-arrow/table'

export interface QueriesProps {}

function Queries(props: QueriesProps) {
  const LENGTH = 2000

  const rainAmounts = Float32Array.from({ length: LENGTH }, () => Number((Math.random() * 20).toFixed(1)))

  const rainDates = Array.from({ length: LENGTH }, (_, i) => new Date(Date.now() - 1000 * 60 * 60 * 24 * i))

  const rainfall = tableFromArrays({
    precipitation: rainAmounts,
    date: rainDates
  })

  console.table(rainfall)

  return (
    <div>
      <h1>Queries</h1>
    </div>
  )
}

export default Queries
