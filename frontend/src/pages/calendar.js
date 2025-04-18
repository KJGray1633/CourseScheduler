import React, { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Navbar } from '../components/navbar.js';
import '../Calendar.css';

const localizer = momentLocalizer(moment);

// Static reference week starting on a Sunday
const staticWeekStart = moment('2025-04-13'); // This is a Sunday
console.log('Static week start:', staticWeekStart.format());


// Map server day string to offset from Sunday
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

    const CustomDateHeader = ({ date }) => {
      return <span>{moment(date).format('dddd')}</span>; // "Sun", "Mon", "Tue", etc.
    };


  const [events, setEvents] = useState([
    {
      id: 'test-event',
      title: 'Test Event',
      start: new Date(2025, 3, 14, 10, 0), // Monday 10 AM
      end: new Date(2025, 3, 14, 12, 0),
    },
  ]);


  useEffect(() => {
    fetch('http://localhost:7000/schedule')
      .then((response) => {
        if (!response.ok) throw new Error('Failed to fetch schedule');
        return response.json();
      })
      .then((data) => {
        console.log('Fetched schedule data:', data); // ✅ Log data

        const mappedEvents = data.courses.flatMap((course) =>
          course.times.map((time) => {
            const dayOffset = dayMap[time.day.toUpperCase()];
            const eventDate = staticWeekStart.clone().add(dayOffset, 'days');

            const start = moment(`${eventDate.format('YYYY-MM-DD')}T${time.startTime}`);
            const end = moment(`${eventDate.format('YYYY-MM-DD')}T${time.endTime}`);

            return {
              id: `${course.courseCode}-${time.day}`,
              title: `${course.name} (${course.courseCode})`,
              start: start.toDate(),
              end: end.toDate(),
            };
          })
        );

        console.log('Mapped events:', mappedEvents); // ✅ Log events
        setEvents(mappedEvents);
      })
      .catch((error) => console.error('Error fetching schedule:', error));
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
          views={['week']}
          defaultDate={staticWeekStart.toDate()} // Static start of the calendar
          style={{ height: '100%' }}
          min={new Date(2025, 3, 13, 7, 0, 0)} // 7 AM
          max={new Date(2025, 3, 13, 23, 0, 0)} // 11 PM
          step={30}
          timeslots={2}
          toolbar={false}
          components={{
              header: CustomDateHeader,
            }}
        />
      </div>
    </>
  );
}

export default WeekViewCalendar;
