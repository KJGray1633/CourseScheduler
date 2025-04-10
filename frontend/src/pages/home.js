import { Navbar } from '../components/navbar.js';
import { useContext } from 'react';
import { ScheduleContext } from '../components/scheduleContext.js';
import { Table } from '../components/table.js';

export function Home() {
  const { schedule, handleAddCourse, handleDropCourse, isCourseInSchedule } = useContext(ScheduleContext);

  return (
    <div>
      <Navbar />
      <div>
        <h1>Home</h1>
        <Table
          tableData={schedule}
          isCourseInSchedule={isCourseInSchedule}
          handleAddCourse={handleAddCourse}
          handleDropCourse={handleDropCourse}
        />
      </div>
    </div>
  );
}