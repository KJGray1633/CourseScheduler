import './App.css';
import { Routes, Route } from 'react-router';
import { Home } from './pages/home.js';
import { Search } from './pages/search.js';
import { Calendar } from './pages/calendar.js';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/search" element={<Search />} />
      <Route path="/calendar" element={<Calendar />} />
    </Routes>
  );
}

export default App;
