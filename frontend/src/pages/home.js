import { Navbar } from '../components/navbar.js';
import { useContext } from 'react';
import { ScheduleContext } from '../components/scheduleContext.js';
import { Table } from '../components/table.js';
import '../styles/home.css';

export function Home() {
  const { schedule, handleAddCourse, handleDropCourse, isCourseInSchedule } = useContext(ScheduleContext);

  return (
    <div>
      <Navbar />
      <div className="content-container">
        <h1>My Schedule</h1>
        <Table
          tableData={schedule}
          isCourseInSchedule={isCourseInSchedule}
          handleAddCourse={handleAddCourse}
          handleDropCourse={handleDropCourse}
        />
      </div>
      <button className="ai-scheduler">AI Scheduler</button>
    </div>
  );
}