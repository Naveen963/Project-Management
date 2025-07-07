import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import SignIn from './pages/SignIn'

const router = createBrowserRouter([
  {
    path: "/",
    element: <SignIn />
  }
])
function App() {

  return <RouterProvider router={router} />

}

export default App
