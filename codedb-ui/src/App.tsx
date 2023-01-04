import React from 'react';
import './App.css';
import { Canvas } from '@react-three/fiber'
import { Flex } from '@react-three/flex';
import { Box } from "./components/threejs/Box";

function App() {
  return (
    // follow https://github.com/pmndrs/react-three-fiber/issues/248 setup pollyfill
    // <Canvas resize={{polyfill: ResizeObserver}}>
    <Canvas>
      <ambientLight/>
      <pointLight position={[10, 10, 10]}/>

      <Flex justifyContent="center" alignItems="center">
        <Box position={[-1.2, 0, 0]}/>
        <Box position={[1.2, 0, 0]}/>
        <Box centerAnchor></Box>
      </Flex>
    </Canvas>
  );
}

export default App;
