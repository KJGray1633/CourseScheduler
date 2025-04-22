import React, { useState, useEffect, useRef } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Navbar } from '../components/navbar.js';
import '../styles/calendar.css';

const localizer = momentLocalizer(moment);

const staticWeekStart = moment('2025-04-13');
const dayMap = {
  SUNDAY: 0,
  MONDAY: 1,
  TUESDAY: 2,
  WEDNESDAY: 3,
  THURSDAY: 4,
  FRIDAY: 5,
  SATURDAY: 6,
};

function WeekViewCalendar() {
  const [events, setEvents] = useState([]);
  const [calendarHeight, setCalendarHeight] = useState(window.innerHeight);
  const calendarContainerRef = useRef(null);

  useEffect(() => {
    const handleResize = () => {
      if (calendarContainerRef.current) {
        setCalendarHeight(window.innerHeight);
      }
    };

    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  useEffect(() => {
    fetch('http://localhost:7000/schedule')
      .then((response) => {
        if (!response.ok) throw new Error('Failed to fetch schedule');
        return response.json();
      })
      .then((data) => {
        const mappedEvents = data.flatMap((course) => {
          return course.times.map((time) => {
            const dayOffset = dayMap[time.day.toUpperCase()];
            const eventDate = staticWeekStart.clone().add(dayOffset, 'days');

            const start = moment(`${eventDate.format('YYYY-MM-DD')}T${time.startTime}`).toDate();
            const end = moment(`${eventDate.format('YYYY-MM-DD')}T${time.endTime}`).toDate();

            return {
              id: `${course.courseCode}-${time.day}`,
              title: `${course.name} (${course.courseCode})`,
              start: start,
              end: end,
            };
          });
        });
        setEvents(mappedEvents);
      })
      .catch((error) => console.error('Error fetching schedule:', error));
  }, []);

  const exportToOutlook = () => {
    let icsContent = 'BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//YourApp//NONSGML v1.0//EN\n';
    events.forEach((event) => {
      icsContent += `BEGIN:VEVENT\n`;
      icsContent += `UID:${event.id}\n`;
      icsContent += `SUMMARY:${event.title}\n`;
      icsContent += `DTSTART:${moment(event.start).utc().format('YYYYMMDDTHHmmss')}Z\n`;
      icsContent += `DTEND:${moment(event.end).utc().format('YYYYMMDDTHHmmss')}Z\n`;
      icsContent += `END:VEVENT\n`;
    });
    icsContent += 'END:VCALENDAR';

    const blob = new Blob([icsContent], { type: 'text/calendar;charset=utf-8' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'calendar.ics';
    link.click();
  };

  return (
    <>
      <Navbar />
      <button onClick={exportToOutlook} className="export-button">
              Export to Outlook
      </button>
      <div className="calendar-container" ref={calendarContainerRef}>
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          defaultView="week"
          views={['week']}
          defaultDate={staticWeekStart.toDate()}
          style={{ height: calendarHeight }}
          min={new Date(2025, 3, 13, 7, 0, 0)}
          max={new Date(2025, 3, 19, 23, 0, 0)}
          step={30}
          timeslots={2}
          toolbar={false}
        />
      </div>
    </>
  );
}

export default WeekViewCalendar;