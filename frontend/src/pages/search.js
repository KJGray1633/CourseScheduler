import React, { useState, useEffect } from 'react';
import { Navbar } from '../components/navbar.js';
import { Table, fetchData } from '../components/table.js';

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
  })
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
  })
}

export function Search() {
  const [query, setQuery] = useState('');
  const [filters, setFilters] = useState({
    days: [], // List of days
    department: null,
    courseCode: 0,
    name: null,
    prof: [], // List of professors
    startTime: null,
    endTime: null
  });

  useEffect(() => {
    searchCourses(query)
  }, [query]);

  useEffect(() => {
    filterCourses(filters)
  }, [filters]);

  const handleCheckboxChange = (event) => {
    const { name, checked } = event.target;
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
      return {
        ...prevFilters,
        days: newDays
      };
    });
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
          onChange={(e) => setQuery(e.target.value)}
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
        <Table path={'search'}/>
      </div>
    </div>
  );
}