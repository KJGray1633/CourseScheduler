import React, { useState, useEffect, useRef } from 'react';
import { Navbar } from '../components/navbar.js';
import { Table, fetchData } from '../components/table.js';

let searchTimeout;
let filterTimeout;

function searchCourses(query) {
  console.log('Searching for courses...');
  fetch("http://localhost:7000/search", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: query
  })
  .then(response => response.json())
  .then(data => {
    console.log('Search results:', data);
    fetchData("search");
  });
}

function filterCourses(filters) {
  console.log('Filtering courses...');
  console.log('Filters:', filters);
  fetch("http://localhost:7000/filter", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(filters)
  })
  .then(response => response.json())
  .then(data => {
    console.log('Filtered results:', data);
    fetchData("filter");
  });
}

export function Search() {
  const [query, setQuery] = useState('');
  const [filters, setFilters] = useState({
    days: [],
    department: null,
    courseCode: 0,
    name: null,
    prof: [],
    startTime: null,
    endTime: null
  });

  const searchTimeoutRef = useRef(null);
  const latestQueryRef = useRef('');

  useEffect(() => {
    latestQueryRef.current = query;

    clearTimeout(searchTimeoutRef.current);

    searchTimeoutRef.current = setTimeout(() => {
      if (query === latestQueryRef.current) {
        searchCourses(query);
      }
    }, 500);

    return () => clearTimeout(searchTimeoutRef.current);
  }, [query]);

  const handleQueryChange = (event) => {
    setQuery(event.target.value);
  };

  /*useEffect(() => {
    console.log("Query changed:", query);
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
      searchCourses(query);
    }, 500);
  }, [query]);

  /*useEffect(() => {
    console.log("Filters changed:", filters);
    clearTimeout(filterTimeout);
    filterTimeout = setTimeout(() => {
      filterCourses(filters);
    }, 500);
  }, [filters]);*/

  /*const handleQueryChange = (event) => {
    const { value } = event.target;
    if (value !== query) {
      setQuery(value);
      clearTimeout(searchTimeout);
      searchTimeout = setTimeout(() => {
        searchCourses(value);
      }, 500);
    }
  };*/

  const handleCheckboxChange = (event) => {
    const { name, checked } = event.target;
    setFilters((prevFilters) => {
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
      return {
        ...prevFilters,
        days: newDays
      };
    });
  };

  const handleTimeChange = (event) => {
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
    }
  };

  const handleInputChange = (event) => {
    const { id, value } = event.target;
    if (value !== filters[id]) {
      setFilters((prevFilters) => ({
        ...prevFilters,
        [id]: value
      }));
    }
  };

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
        <label htmlFor="MWF">
          <input
            type="checkbox"
            id="MWF"
            name="MWF"
            checked={filters.MWF}
            onChange={handleCheckboxChange}
          />
          M/W/F
        </label>
        <label htmlFor="TR">
          <input
            type="checkbox"
            id="TR"
            name="TR"
            checked={filters.TR}
            onChange={handleCheckboxChange}
          />
          T/R
        </label>
        <label htmlFor="department">
          Department:
          <input
            type="text"
            id="department"
            placeholder="Department"
            onChange={handleInputChange}
          />
        </label>
        <label htmlFor="courseCode">
          Course Code:
          <input
            type="number"
            id="courseCode"
            placeholder="Course Code"
            onChange={handleInputChange}
          />
        </label>
        <label htmlFor="name">
          Course Name:
          <input
            type="text"
            id="name"
            placeholder="Course Name"
            onChange={handleInputChange}
          />
        </label>
        <label htmlFor="prof">
          Professor:
          <input
            type="text"
            id="prof"
            placeholder="Professor"
            onChange={handleInputChange}
          />
        </label>
        <label htmlFor="startTime">
          Start Time:
          <input
            type="time"
            id="startTime"
            onChange={handleTimeChange}
          />
        </label>
        <label htmlFor="endTime">
          End Time:
          <input
            type="time"
            id="endTime"
            onChange={handleTimeChange}
          />
        </label>
        <Table path={'search'}/>
      </div>
    </div>
  );
}