import React from 'react';
import App from './App';
import ReactThreeTestRenderer from '@react-three/test-renderer'

test('mesh to have two children', async () => {
  const renderer = await ReactThreeTestRenderer.create(<App/>)

  // const mesh = renderer.scene.children[0].allChildren
  // expect(mesh.length).toBe(2)
})
