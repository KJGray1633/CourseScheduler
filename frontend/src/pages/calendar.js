import React, { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Navbar } from '../components/navbar.js';
import '../Calendar.css';

const localizer = momentLocalizer(moment);

function WeekViewCalendar() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Fetch schedule data
    fetch('http://localhost:7000/schedule')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch schedule');
        }
        return response.json();
      })
      .then((data) => {
        // Map schedule data to calendar events
        const mappedEvents = data.courses.flatMap((course) =>
          course.times.map((time) => ({
            id: `${course.courseCode}-${time.day}`,
            title: `${course.name} (${course.courseCode})`,
            start: new Date(`2023-01-01T${time.startTime}`),
            end: new Date(`2023-01-01T${time.endTime}`),
          }))
        );
        setEvents(mappedEvents);
      })
      .catch((error) => {
        console.error('Error fetching schedule:', error);
      });
  }, []);

  return (
    <>
      <Navbar />
      <div className="calendar-container">
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          defaultView="week"
          style={{ height: '100%' }}
          min={new Date(2023, 0, 1, 7, 0, 0)}
          max={new Date(2023, 0, 1, 23, 0, 0)}
          step={30}
          timeslots={2}

        />
      </div>
    </>
  );
}

export default WeekViewCalendar;