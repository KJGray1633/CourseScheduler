import './App.css';
import { Routes, Route } from 'react-router';
import { Home } from './pages/home.js';
import { Search } from './pages/search.js';
import { Calendar } from './pages/calendar.js';
import { ScheduleProvider } from './components/scheduleContext.js';

function App() {
  return (
    <ScheduleProvider>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/search" element={<Search />} />
        <Route path="/calendar" element={<Calendar />} />
      </Routes>
    </ScheduleProvider>
  );
}

export default App;
