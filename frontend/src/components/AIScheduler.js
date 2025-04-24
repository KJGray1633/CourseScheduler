import { useState, useEffect, useContext } from "react";
import { Table } from "../components/table.js";
import { ScheduleContext } from '../components/scheduleContext.js';

export function AIScheduler() {
  const [courses, setCourses] = useState([]);
  const { schedule, handleAddCourse, handleDropCourse, isCourseInSchedule } = useContext(ScheduleContext);

  const major = "computerScience";
  const desiredCredits = 15;

  useEffect(() => {
    // Fetch recommended courses from the server
    fetch(`http://localhost:7000/recommendSchedule?major=${major}&desiredCredits=${desiredCredits}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch recommended courses");
        }
        return response.json();
      })
      .then((data) => {
        setCourses(data);
      })
      .catch((error) => {
        console.error("Error fetching recommended courses:", error);
      });
  }, [major, desiredCredits]);

  return (
    <div>
      <form className="aischeduler-form">
        <h1>AI Scheduler</h1>
        <Table
          tableData={courses}
          isCourseInSchedule={isCourseInSchedule}
          handleAddCourse={handleAddCourse}
          handleDropCourse={handleDropCourse}
        />
      </form>
    </div>
  );
}
