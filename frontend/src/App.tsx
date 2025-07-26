import { Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import CarList from './pages/CarList'
import CarForm from './pages/CarForm'
import { Toaster } from 'react-hot-toast'

function App() {
  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <main className="container mx-auto px-4 py-8">
        <Routes>
          <Route path="/" element={<CarList />} />
          <Route path="/add" element={<CarForm />} />
          <Route path="/edit/:id" element={<CarForm />} />
        </Routes>
      </main>
      <Toaster position="top-right" />
    </div>
  )
}

export default App 