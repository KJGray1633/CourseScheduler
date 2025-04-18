import React, { useState, useEffect, useRef } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Navbar } from '../components/navbar.js';
import '../Calendar.css';

const localizer = momentLocalizer(moment);

const staticWeekStart = moment('2025-04-13');
console.log('Static week start:', staticWeekStart.format());

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
  console.log('Rendering WeekViewCalendar component');

  const CustomDateHeader = ({ date }) => {
    console.log('Rendering CustomDateHeader for date:', date);
    return <span>{moment(date).format('dddd')}</span>;
  };

  const [events, setEvents] = useState([
    {
      id: '1',
      title: 'Test Event',
      start: new Date(2025, 3, 14, 10, 0),
      end: new Date(2025, 3, 14, 12, 0),
    },
    {
      id: '2',
      title: 'Another Event',
      start: new Date(2025, 3, 15, 9, 0),
      end: new Date(2025, 3, 15, 11, 0),
    },
  ]);

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
    console.log('Fetching schedule data from API...');
    fetch('http://localhost:7000/schedule')
      .then((response) => {
        console.log('Received response from API:', response);
        if (!response.ok) throw new Error('Failed to fetch schedule');
        return response.json();
      })
      .then((data) => {
         console.log('Fetched schedule data:', data);

         const mappedEvents = data.flatMap((course) => {
           console.log('Mapping event for course:', course.name, 'and times:', course.times);
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

         console.log('Mapped events:', mappedEvents);
         setEvents(mappedEvents);
       })

      .catch((error) => console.error('Error fetching schedule:', error));
  }, []);

  return (
    <>
      <Navbar />
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
          components={{
            header: CustomDateHeader,
          }}
        />
      </div>
    </>
  );
}

export default WeekViewCalendar;
