import React, { useState, useEffect } from 'react';
import { Navbar } from '../components/navbar.js';

export function Search() {
  const [filters, setFilters] = useState({
    days: [], // List of days
    department: null,
    courseCode: 0,
    name: null,
    prof: [], // List of professors
    startTime: null,
    endTime: null,
  });
  const [checkboxes, setCheckboxes] = useState({
    MWF: false,
    TR: false,
  });
  const [tableData, setTableData] = useState([]);
  const [schedule, setSchedule] = useState([]);

  useEffect(() => {
    console.log('Fetching data from: search');
    fetch('http://localhost:7000/search')
      .then(response => response.json())
      .then(data => {
        console.log('Data fetched:', data);
        setTableData(data);
      })
      .catch(error => {
        console.error('Error fetching search results:', error);
      });
    fetch('http://localhost:7000/schedule')
      .then(response => response.json())
      .then(data => {
        console.log('Schedule fetched:', data);
        setSchedule(data);
      })
      .catch(error => {
        console.error('Error fetching schedule:', error);
      });
  }, []);

  function isCourseInSchedule(course) {
    return schedule.some(scheduled => scheduled.courseCode === course.courseCode);
  }

  function handleAddCourse(course) {
    setSchedule(prevSchedule => [...prevSchedule, course]);
    const javaCourse = {
      cid: course.cid,
      name: course.name,
      courseCode: course.courseCode,
      description: course.description,
      professor: course.professor,
      times: course.times,
    }
    console.log(course);
    console.log(javaCourse);
    fetch('http://localhost:7000/schedule', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(javaCourse)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Course added:', data);
      })
      .catch(error => {
        console.error('Error adding course:', error);
      });
  }

  function handleDropCourse(course) {
    setSchedule(prevSchedule => prevSchedule.filter(scheduled => scheduled.courseCode !== course.courseCode));
  }

  function fetchSearchData() {
    console.log('Fetching data from: search');
    fetch('http://localhost:7000/search')
      .then(response => response.json())
      .then(data => {
        console.log('Data fetched:', data);
        setTableData(data);
      })
      .catch(error => {
        console.error('Error fetching search results:', error);
      });
  }

  function fetchFilterData() {
    console.log('Fetching data from: filter');
    fetch('http://localhost:7000/filter')
      .then(response => response.json())
      .then(data => {
        console.log('Data fetched:', data);
        setTableData(data);
      })
      .catch(error => {
        console.error('Error fetching filter results:', error);
      });
  }

  async function updateQuery(query) {
    console.log('Searching for courses...');
    console.log('Query:', query);

    try {
      const response = await fetch("http://localhost:7000/search", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: query // Ensure query is sent as JSON
      });

      const data = await response.json();
      console.log('Search results:', data);
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  }
  
  async function updateFilters(filters) {
    console.log('Filtering courses...');
    console.log('Filters:', filters);
    
    try {
      const response = await fetch("http://localhost:7000/filter", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(filters)
      });
      const data = await response.json();
      console.log('Search results:', data);
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  }

  async function handleQueryChange(event) {
    const newQuery = event.target.value;

    // Wait for updateQuery to complete
    await updateQuery(newQuery);

    // Perform another fetch request after updateQuery completes
    console.log('Making another fetch request...');
    fetchSearchData(); // Example of another fetch function
  }

  async function handleCheckboxChange(event) {
    const { name, checked } = event.target;
    console.log('Checkbox changed:', name, checked);

    // Correctly update the checkboxes state
    setCheckboxes(prevCheckboxes => ({
      ...prevCheckboxes,
      [name]: checked
    }));

    setFilters(prevFilters => {
      let newDays = [...prevFilters.days];
      if (name === 'MWF') {
        if (checked) {
          newDays.push('MONDAY', 'WEDNESDAY', 'FRIDAY');
        } else {
          newDays = newDays.filter(day => day !== 'MONDAY' && day !== 'WEDNESDAY' && day !== 'FRIDAY');
        }
      } else if (name === 'TR') {
        if (checked) {
          newDays.push('TUESDAY', 'THURSDAY');
        } else {
          newDays = newDays.filter(day => day !== 'TUESDAY' && day !== 'THURSDAY');
        }
      }
      const updatedFilters = {
        ...prevFilters,
        days: newDays
      };
      console.log('Filters after change (inside setFilters):', updatedFilters);
      return updatedFilters;
    });

    // Use a useEffect to react to changes in filters or pass the updated filters directly
    await updateFilters({
      ...filters,
      days: checked
        ? [...filters.days, ...(name === 'MWF' ? ['MONDAY', 'WEDNESDAY', 'FRIDAY'] : ['TUESDAY', 'THURSDAY'])]
        : filters.days.filter(day => !['MONDAY', 'WEDNESDAY', 'FRIDAY', 'TUESDAY', 'THURSDAY'].includes(day)),
    });
    fetchFilterData();
  }

  async function handleInputChange(event) {
    const { name, value } = event.target;
    console.log('Input changed:', name, value);

    setFilters(prevFilters => ({
      ...prevFilters,
      [name]: value
    }));

    // Use a useEffect to react to changes in filters or pass the updated filters directly
    await updateFilters({
      ...filters,
      [name]: value
    });
    fetchFilterData();
  }

  async function handleTimeChange(event) {
    const time = event.target.value + ":00";
    console.log('Time changed:', event.target.id, time);

    if (time !== filters[event.target.id]) {
      setFilters((prevFilters) => {
        const updatedFilters = { ...prevFilters, [event.target.id]: time };
        if (JSON.stringify(updatedFilters) !== JSON.stringify(prevFilters)) {
          return updatedFilters;
        }
        return prevFilters;
      });

      // Wait for the filters to update and then call updateFilters
      await updateFilters({
        ...filters,
        [event.target.id]: time
      });

      // Fetch the filtered data after updating the filters
      fetchFilterData();
    }
  }

  async function handleProfInput(event) {
    const input = event.target.value.trim(); // Get the input value and trim whitespace
    if (input) {
      const profArray = input.split('/').map(prof => prof.trim()).filter(prof => prof); // Convert to array and clean up

      setFilters(prevFilters => ({
        ...prevFilters,
        prof: [...profArray] // Add the new professors to the array
      }));

      // Perform an asynchronous operation, e.g., updating filters on the server
      await updateFilters({
        ...filters,
        prof: [...profArray]
      });

      // Optionally fetch updated data
      fetchFilterData();
    }
  }

  function clearFilters() {
    console.log('Clearing filters');
    setFilters({
      days: [],
      department: null,
      courseCode: 0,
      name: null,
      prof: [],
      startTime: null,
      endTime: null
    });
    setCheckboxes({
      MWF: false,
      TR: false,
    });
    fetchSearchData();
  }

  return (
    <div>
      <Navbar />
      <div>
        <h1>Search</h1>
        <input
          type="text"
          id="query"
          placeholder="Search for courses..."
          onChange={handleQueryChange}
        />
        <div>
          <label htmlFor="MWF">
            <input
              type="checkbox"
              id="MWF"
              name="MWF"
              checked={checkboxes.MWF}
              onChange={handleCheckboxChange}
            />
            M/W/F
          </label>
          <label htmlFor="TR">
            <input
              type="checkbox"
              id="TR"
              name="TR"
              checked={checkboxes.TR}
              onChange={handleCheckboxChange}
            />
            T/R
          </label>
          <label htmlFor="department">
            Department:
            <input
              type="text"
              id="department"
              name="department"
              value={filters.department || ""} // Use empty string if filters.department is null
              onChange={handleInputChange}
            />
          </label>
          <label htmlFor="courseCode">
            Course Code:
            <input
              type="number"
              id="courseCode"
              name="courseCode"
              value={filters.courseCode || 0} // Use 0 if filters.courseCode is null
              onChange={handleInputChange}
            />
          </label>
          <label htmlFor="name">
            Course Name:
            <input
              type="text"
              id="name"
              name="name"
              value={filters.name || ""} // Use empty string if filters.name is null
              onChange={handleInputChange}
            />
          </label>
          <label htmlFor="prof">
            Professor:
            <input
              type="text"
              id="prof"
              name="prof"
              value={filters.prof}
              onChange={handleProfInput}
            />
          </label>
          <label htmlFor="startTime">
            Start Time:
            <input
              type="time"
              id="startTime"
              name="startTime"
              value={filters.startTime || ""} // Use empty string if filters.startTime is null
              onChange={handleTimeChange}
            />
          </label>
          <label htmlFor="endTime">
            End Time:
            <input
              type="time"
              id="endTime"
              name="endTime"
              value={filters.endTime || ""} // Use empty string if filters.endTime is null
              onChange={handleTimeChange}
            />
          </label>
          <button onClick={clearFilters}>Clear Filters</button>
        </div>
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
              {tableData.map((item, index) => (
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