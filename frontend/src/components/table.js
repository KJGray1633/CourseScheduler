import '../App.css';

export function fetchData(path) {
  console.log(`Fetching data for path: ${path}`);
  fetch('http://localhost:7000/' + path)
    .then(response => response.json())
    .then(data => {
      console.log('Data fetched:', data);
      const content = document.getElementById('content');
      // Clear the table body before appending new rows
      content.innerHTML = '';
      const daysConvert = {
        'MONDAY': 'M',
        'TUESDAY': 'T',
        'WEDNESDAY': 'W',
        'THURSDAY': 'R',
        'FRIDAY': 'F'
      };
      data.forEach(item => {
        //console.log(item);
        const tr = document.createElement('tr');
        let td = document.createElement('td');
        td.textContent = `${item.subject.toUpperCase()} ${item.courseCode}`;
        tr.appendChild(td);
        td = document.createElement('td');
        if (item.times.length > 0) {
          let days = [];
          item.times.forEach(time => {
            const day = daysConvert[time.day];
            days.push(day);
          });
          td.textContent = `${days.join('/')} ${item.times[0].startTime} - ${item.times[0].endTime}`;
        } else {
          td.textContent = 'TBA';
        }
        tr.appendChild(td);
        td = document.createElement('td');
        td.textContent = item.location.toUpperCase();
        tr.appendChild(td);
        td = document.createElement('td');
        if (item.professor.length >= 1 && item.professor[0] !== '') {
          let professors = [];
          item.professor.forEach(prof => {
            let parts = prof.split(', ');
            parts[0] = parts[0].charAt(0).toUpperCase() + parts[0].slice(1);
            let last = parts[1].split(' ');
            last[0] = last[0].charAt(0).toUpperCase() + last[0].slice(1);
            if (last.length > 1) {
              last[1] = last[1].charAt(0).toUpperCase() + last[1].slice(1);
              parts[1] = last.join(' ');
            } else {
              parts[1] = last[0];
            }
            professors.push(parts.join(', '));
          });
          td.textContent = professors.join(', ');
        } else {
          td.textContent = 'TBA';
        }
        tr.appendChild(td);
        content.appendChild(tr);
      });
    })
    .catch(error => (console.error("Error fetching data:", error)));
}

export function Table({ path }) {
  fetchData(path);
  return (
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
        <tbody id="content">
          <tr></tr>
        </tbody>
      </table>
    </div>
  );
}