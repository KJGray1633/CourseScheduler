import { useCalendarApp, ScheduleXCalendar } from '@schedule-x/react';
import {
  createViewWeek,
} from '@schedule-x/calendar';
import { createEventsServicePlugin } from '@schedule-x/events-service';
import { useState, useEffect } from 'react';
import '@schedule-x/theme-default/dist/index.css';

import { Navbar } from '../components/navbar.js';
import '../Calendar.css';
import CustomTimeGridEvent from '../components/CustomTimeGridEvent.js';
import CustomWeekGridHour from '../components/CustomWeekGridHour.js';

const customComponents = {
  timeGridEvent: CustomTimeGridEvent,
//  weekGridHour: CustomWeekGridHour,
};

function CalendarApp() {
  const eventsService = useState(() => createEventsServicePlugin())[0];
  const [events, setEvents] = useState([]);

  const calendar = useCalendarApp({
    defaultView: 'week',
    views: [
      createViewWeek({
        id: 'week',
      }),
    ],
    events,
    plugins: [eventsService],
  });

  useEffect(() => {
    fetch('http://localhost:7000/schedule')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch course schedule');
        }
        return response.json();
      })
      .then((data) => {
        const mappedEvents = data.map((course) => ({
          id: course.courseCode,
          title: course.courseCode,
          start: `${course.startTime}`,
          end: `${course.endTime}`,
        }));
        setEvents(mappedEvents);
      })
      .catch((error) => {
        console.error('Error fetching course schedule:', error);
      });
  }, []);

  return (
    <>
      <Navbar />
      <div className="calendar-container">
        <ScheduleXCalendar
          calendarApp={calendar}
          customComponents={customComponents}
        />
      </div>
    </>
  );
}

export default CalendarApp;