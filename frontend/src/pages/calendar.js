import { Navbar } from '../components/navbar.js';
import React, { useState, useEffect } from 'react';
import { format, startOfWeek, addDays } from 'date-fns';
import '../Calendar.css';

export function Calendar() {
  const [courses, setCourses] = useState([]);

  const fetchTimes = (path) => {
    fetch('http://localhost:7000/' + path)
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch course times');
        }
        return response.json();
      })
      .then(data => {
        const times = data.map(item => ({
          courseCode: item.courseCode,
          startTime: item.times.length > 0 ? item.times[0].startTime : 'TBA',
          endTime: item.times.length > 0 ? item.times[0].endTime : 'TBA',
          days: item.times.map(time => time.day),
        }));
        setCourses(times); // Update state with processed times
      })
      .catch(error => {
        console.error('Error fetching course times:', error);
      });
  };

  useEffect(() => {
    fetchTimes('schedule'); // Fetch course times from the backend
  }, []);

  const RenderCalendar = () => {
    const [currentDate, setCurrentDate] = useState(new Date());
    const startOfTheWeek = startOfWeek(currentDate, { weekStartsOn: 0 });

    const days = [];
    for (let i = 0; i < 7; i++) {
      const day = addDays(startOfTheWeek, i);
      days.push(day);
    }

    // adding time increments
    const timeIncrements = [];
    for (let hour  = 8; hour <= 20; hour++) {
        for (let minute = 0; minute < 60; minute += 35) {
            timeIncrements.push(`${hour}:${minute < 10 ? '0' : ''}${minute}`);
        }
    }

    return (
      <div>
        <h2>{format(startOfTheWeek, 'MMMM yyyy')}</h2>
        <div className="calendar-container">
            <div className="time-column">
                {timeIncrements.map((time, index) => (
                    <div key={index} className="time-slot">
                    {time}
                    </div>
                ))}
            </div>
          {days.map((day) => (
            <div key={day.toString()} className="calendar-day">
              <h3>{format(day, 'EEE')}</h3>
              <p>{format(day, 'd')}</p>
              {courses
                .filter(course => course.days.includes(format(day, 'EEEE').toUpperCase()))
                .map((course, index) => (
                  <div key={index} className="calendar_default_event_inner">
                    {course.courseCode} - {course.startTime} to {course.endTime}
                  </div>
                ))}
            </div>
          ))}
        </div>
      </div>
    );
  };

  return (
    <div>
      <Navbar />
      <div>
        <h1>Calendar</h1>
        <RenderCalendar />
      </div>
    </div>
  );
}