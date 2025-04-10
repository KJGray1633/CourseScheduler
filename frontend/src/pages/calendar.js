import { Navbar } from '../components/navbar.js';
import React, { useState, useEffect } from 'react';
import { format, startOfWeek, addDays } from 'date-fns';
import '../Calendar.css'

export function Calendar() {
  const [courses, setCourses] = useState([]);

  const [currentDate, setCurrentDate] = useState(new Date());

  const RenderCalendar = () => {
    const [currentDate, setCurrentDate] = useState(new Date());

    const startOfTheWeek = startOfWeek(currentDate, { weekStartsOn: 1 }); // start week on Monday

    const days = [];
    for (let i = 0; i < 7; i++) {
      const day = addDays(startOfTheWeek, i);
      days.push(day);
    }

    return (
      <div>
        <h2>{format(startOfTheWeek, 'MMMM yyyy')}</h2>
        {/*
        <div>
          <button onClick={() => setCurrentDate(addDays(currentDate, -7))}>Previous Week</button>
          <button onClick={() => setCurrentDate(addDays(currentDate, 7))}>Next Week</button>
        </div>
        */}
        <div className="calendar-days-container">
          {days.map((day) => (
            <div key={day.toString()} className="calendar-day">
              <h3>{format(day, 'EEE')}</h3>
              <p>{format(day, 'd')}</p>
            </div>
          ))}
        </div>
      </div>
    );
  };

  useEffect(() => {
    // Fetch schedule data from the backend
    fetch("http://localhost:7000/schedule")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch schedule");
        }
        return response.json();
      })
      .then((data) => {
        setCourses(data); // Update state with fetched courses
      })
      .catch((error) => {
        console.error("Error fetching schedule:", error);
      });
  }, []);

  return (
    <div>
      <Navbar />
      <div>
        <h1>Calendar</h1>
        <RenderCalendar /> {/* Include the RenderCalendar component here */}
        <ul>
          {courses.map((course, index) => (
            <li key={index}>
              <strong>{course.name}</strong> - {course.department} ({course.courseCode})
              <strong>{course.startTime}</strong> - {course.endTime}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}