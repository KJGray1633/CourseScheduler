import { Navbar } from '../components/navbar.js';

export function Home() {
  fetch('http://localhost:7000/schedule')
    .then(response => response.json())
    .then(data => {
      const schedule = document.getElementById('schedule');
      data.forEach(item => {
        console.log(item);
        const tr = document.createElement('tr');
        let td = document.createElement('td');
        td.textContent = `${item.subject.toUpperCase()} ${item.courseCode}`;
        tr.appendChild(td);
        td = document.createElement('td');
        const daysConvert = {
          'MONDAY': 'M',
          'TUESDAY': 'T',
          'WEDNESDAY': 'W',
          'THURSDAY': 'R',
          'FRIDAY': 'F'
        };
        let days = [];
        item.times.forEach(time => {
          const day = daysConvert[time.day];
          days.push(day);
        });
        td.textContent = `${days.join('/')} ${item.times[0].startTime} - ${item.times[0].endTime}`;
        tr.appendChild(td);
        td = document.createElement('td');
        td.textContent = item.location.toUpperCase();
        tr.appendChild(td);
        td = document.createElement('td');
        item.professor.forEach(prof => {
          let parts = prof.split(', ');
          console.log(parts);
          parts[0] = parts[0].charAt(0).toUpperCase() + parts[0].slice(1);
          parts[1] = parts[1].charAt(0).toUpperCase() + parts[1].slice(1, parts[1].length - 2) + parts[1].charAt(parts[1].length - 2).toUpperCase() + parts[1].slice(parts[1].length - 1);
          td.textContent = parts.join(', ');
          tr.appendChild(td);
          console.log(parts);
        });
        schedule.appendChild(tr);
      });
    })
    .catch(error => (console.error("Error fetching data:", error)));
  return (
    <div>
      <Navbar />
      <div>
        <h1>Home</h1>
        <table id="schedule">
          <caption>Current Schedule</caption>
          <thead>
            <tr>
              <th>Course Code</th>
              <th>Times</th>
              <th>Location</th>
              <th>Professor</th>
            </tr>
          </thead>
          <tbody>
            <tr></tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}