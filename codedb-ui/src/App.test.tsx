import React from 'react';
import ReactThreeTestRenderer from '@react-three/test-renderer'
import { Box } from "./App";

test('mesh to have two children', async () => {
  (global as any).IS_REACT_ACT_ENVIRONMENT = true
  const renderer = await ReactThreeTestRenderer.create(<Box />)

  // const mesh = renderer.scene.children[0].allChildren
  // expect(mesh.length).toBe(2)
})
