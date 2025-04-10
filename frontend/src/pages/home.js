import { Navbar } from '../components/navbar.js';
import { useContext } from 'react';
import { ScheduleContext } from '../components/scheduleContext.js';

export function Home() {
  const { schedule, handleAddCourse, handleDropCourse, isCourseInSchedule } = useContext(ScheduleContext);

  return (
    <div>
      <Navbar />
      <div>
        <h1>Home</h1>
        <div>
          <table>
            <thead>
              <tr>
                <th>Course Code</th>
                <th>Times</th>
                <th>Location</th>
                <th>Professor</th>
              </tr>
            </thead>
            <tbody>
              {schedule.map((item, index) => (
                <tr key={index}>
                  <td>{`${item.subject.toUpperCase()} ${item.courseCode}`}</td>
                  <td>
                    {item.times.length > 0
                    ? `${item.times.map(time => time.day[0]).join('/')} ${item.times[0].startTime} - ${item.times[0].endTime}`
                    : 'TBA'}
                  </td>
                  <td>{item.location.toUpperCase()}</td>
                  <td>
                    {item.professor.length > 0
                    ? item.professor.join(', ')
                    : 'TBA'}
                  </td>
                  <td>
                    {!isCourseInSchedule(item) && (
                      <button onClick={() => handleAddCourse(item)}>Add</button>
                    )}
                    {isCourseInSchedule(item) && (
                      <button onClick={() => handleDropCourse(item)}>Drop</button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}