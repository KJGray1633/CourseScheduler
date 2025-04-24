import { Navbar } from '../components/navbar.js';
import { useContext, useState } from 'react';
import { ScheduleContext } from '../components/scheduleContext.js';
import { Table } from '../components/table.js';
import { AIScheduler } from '../components/AIScheduler.js';
import '../styles/home.css';

export function Home() {
  const { schedule, handleAddCourse, handleDropCourse, isCourseInSchedule } = useContext(ScheduleContext);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const toggleModal = () => {
    setIsModalOpen(!isModalOpen);
  };

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
      <button className="ai-scheduler" onClick={toggleModal}>AI Scheduler</button>
      {isModalOpen && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-modal" onClick={toggleModal}>Close</button>
            <AIScheduler />
          </div>
        </div>
      )}    </div>
  );
}