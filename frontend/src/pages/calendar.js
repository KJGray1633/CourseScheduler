import { Navbar } from '../components/navbar.js';
import React from 'react';
import { DayPilotCalendar } from '@daypilot/daypilot-react';
import "./Calendar.css"

const Calendar = () => {
    const [events, setEvents] = React.useState([
    const config = {
        viewType: "Week",
        durationBarVisible: false,
    };

    return (
        <div>
            <DayPilotCalendar {...config} />
            <DayPilotCalendar
                events={events}
        </div>
    );
}

useEffect(() => {
    const events = }
    {
        id: 1,
        start: "2023-10-01T10:00:00",
        end: "2023-10-01T12:00:00",
        text: "Event 1"
      },
      {
        id: 2,
        start: "2023-10-02T13:00:00",
        end: "2023-10-02T15:00:00",
        text: "Event 2"
      },
    ]);
    }
})
}
export default Calendar;

export function Calendar() {
  return (
    <div>
      <Navbar />
      <div>
        <h1>Calendar</h1>
      </div>
    </div>
  );
}