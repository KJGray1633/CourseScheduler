import { createContext, useState } from 'react';

export const ScheduleContext = createContext();

export function ScheduleProvider({ children }) {
  const [schedule, setSchedule] = useState([]);
  const [query, setQuery] = useState('');

  function isCourseInSchedule(course) {
    return schedule.some(scheduled => scheduled.cid === course.cid);
  }

  function handleAddCourse(course) {
    setSchedule(prevSchedule => [...prevSchedule, course]);
    console.log(course);
    fetch('http://localhost:7000/schedule', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(course)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Course added:', data);
      })
      .catch(error => {
        console.error('Error adding course:', error);
      });
  }

  function handleDropCourse(course) {
    setSchedule(prevSchedule => prevSchedule.filter(scheduled => scheduled.cid !== course.cid));
    console.log(course);
    fetch('http://localhost:7000/schedule', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(course)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Course dropped:', data);
      })
      .catch(error => {
        console.error('Error dropping course:', error);
      });
  }

  return (
    <ScheduleContext.Provider value={{ schedule, setSchedule, query, setQuery, isCourseInSchedule, handleAddCourse, handleDropCourse }}>
      {children}
    </ScheduleContext.Provider>
  );
}