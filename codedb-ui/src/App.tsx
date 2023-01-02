import React, { useRef, useState } from 'react';
import './App.css';
import { Canvas, useFrame } from '@react-three/fiber'
import { Flex } from '@react-three/flex';

function Box(props: any) {
  // This reference gives us direct access to the THREE.Mesh object
  const ref = useRef()
  // Hold state for hovered and clicked events
  const [hovered, hover] = useState(false)
  const [clicked, click] = useState(false)
  // @ts-ignore
  useFrame((state, delta) => (ref.current.rotation.x += delta))
  // Return the view, these are regular Threejs elements expressed in JSX
  return (
    <mesh
      {...props}
      ref={ref}
      scale={clicked ? 1.5 : 1}
      onClick={(event) => click(!clicked)}
      onPointerOver={(event) => hover(true)}
      onPointerOut={(event) => hover(false)}>
      <boxGeometry args={[1, 1, 1]}/>
      <meshStandardMaterial color={hovered ? 'hotpink' : 'orange'}/>
    </mesh>
  )
}

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
