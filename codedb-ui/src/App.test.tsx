import React from 'react';
import ReactThreeTestRenderer from '@react-three/test-renderer'
import { Box } from "./components/threejs/Box";

test('mesh to have two children', async () => {
  const renderer = await ReactThreeTestRenderer.create(<Box />)

  // const mesh = renderer.scene.children[0].allChildren
  // expect(mesh.length).toBe(2)
})
