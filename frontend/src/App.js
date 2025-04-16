import './App.css';
import { Routes, Route } from 'react-router';
import { Home } from './pages/home.js';
import { Search } from './pages/search.js';
import { ScheduleProvider } from './components/scheduleContext.js';
import CalendarApp from './pages/calendar.js';

function App() {
  return (
    <ScheduleProvider>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/search" element={<Search />} />
        <Route path="/calendar" element={<CalendarApp />} />
      </Routes>
    </ScheduleProvider>
  );
}

export default App;
