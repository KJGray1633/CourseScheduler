import React, { useState, useEffect, useContext } from 'react';
import { Navbar } from '../components/navbar.js';
import { ScheduleContext } from '../components/scheduleContext.js';
import { Table } from '../components/table.js';
import { Modal } from '../components/modal.js';
import '../styles/search.css'; // Import your CSS file here

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
  const { setSchedule, query, setQuery, handleDropCourse, isCourseInSchedule } = useContext(ScheduleContext);
  const [flashMessage, setFlashMessage] = useState('');
  const [showModal, setShowModal] = useState(false);

  console.log(query);

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
  }, []);

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
    setQuery(newQuery);
    console.log(query);

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
      // Split the input into an array of professors using a delimiter (e.g., comma or slash)
      const profArray = input.split(/[,/]/).map(prof => prof.trim()).filter(prof => prof);

      setFilters(prevFilters => ({
        ...prevFilters,
        prof: profArray // Update the prof array
      }));

      await updateFilters({
        ...filters,
        prof: profArray
      });
    } else {
      // If input is empty, clear the prof array
      setFilters(prevFilters => ({
        ...prevFilters,
        prof: []
      }));

      await updateFilters({
        ...filters,
        prof: []
      });
    }

    fetchFilterData(); // Optionally fetch updated data
  }

  async function addCourseWithFeedback(course) {
    try {
      console.log('Adding course:', course);
      const response = await fetch('http://localhost:7000/schedule', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(course),
      });

      const data = await response.json(); // Parse the response JSON
      console.log('Server response:', data); // Debugging log

      if (response.ok) {
        setSchedule(prevSchedule => [...prevSchedule, course]);
        //setFlashMessage(`Success: ${data.message || 'Course added successfully'}`); // Fallback message
      } else {
        setFlashMessage(`Error: ${data.error || 'An error occurred'}`); // Fallback error message
        setShowModal(true);
      }
    } catch (err) {
      console.error('Error adding course:', err); // Debugging log
      setFlashMessage('Error: Could not add the course.');
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
      <div className="content-container">
        {showModal && (
          <Modal
            message={flashMessage}
            onClose={() => setShowModal(false)} // Close the modal
          />
        )}
        <div className="search-and-filter-container">
          <div className="query-container">
            <div className="query-input-wrapper">
              <span className="search-icon">🔍</span>
              <input
                type="text"
                id="query"
                value={query || ""} // Display the query or an empty string if null
                onChange={(e) => setQuery(e.target.value)} // Update query state on input change
                onBlur={handleQueryChange}
                className="query-input"
              />
            </div>
          </div>
          <div className="filters-container">
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
              <br />
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
            </div>
            <div>
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
              <br />
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
            </div>
            <div>
              <label htmlFor="name">
                Course Name:
                <input
                  type="text"
                  id="name"
                  name="name"
                  onBlur={handleInputChange}
                />
              </label>
              <br />
              <label htmlFor="courseCode">
                Course Code:
                <input
                  type="number"
                  id="courseCode"
                  name="courseCode"
                  onBlur={handleInputChange}
                />
              </label>
            </div>
            <div>
              <label htmlFor="prof">
                Professor:
                <input
                  type="text"
                  id="prof"
                  name="prof"
                  onBlur={handleProfInput}
                />
              </label>
              <br />
              <label htmlFor="department">
                Department:
                <input
                  type="text"
                  id="department"
                  name="department"
                  onBlur={handleInputChange}
                />
              </label>
            </div>
            <button onClick={clearFilters}>Clear Filters</button>
          </div>
        </div>
        <Table
          tableData={tableData}
          isCourseInSchedule={isCourseInSchedule}
          handleAddCourse={addCourseWithFeedback}
          handleDropCourse={handleDropCourse}
        />
      </div>
    </div>
  );
}