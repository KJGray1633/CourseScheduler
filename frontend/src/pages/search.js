import React, { useState, useEffect } from 'react';
import { Navbar } from '../components/navbar.js';
import { Table, fetchData } from '../components/table.js';



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
  //const [query, setQuery] = useState('');
  const [filters, setFilters] = useState({
    days: [], // List of days
    department: null,
    courseCode: 0,
    name: null,
    prof: [], // List of professors
    startTime: null,
    endTime: null
  });
  const [tableData, setTableData] = useState([]);

  /*useEffect(() => {
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
  }, []);*/

  function searchCourses(query) {
    console.log('Searching for courses...');
    console.log('Query:', query);
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
      //const table = document.getElementById('table');
      // Clear the table body before appending new rows
      //table.innerHTML = '';
      //table.appendChild(<Table path={"search"} />);
      //fetchData("search");
    })
    .catch(error => {
      console.error('Error fetching search results:', error);
    });
    fetchData();
  }

  function fetchData() {
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

  /*useEffect(() => {
    searchCourses(query)
  }, [query]);*/

  /*useEffect(() => {
    filterCourses(filters)
  }, [filters]);*/

  const handleQueryChange = (event) => {
    const newQuery = event.target.value;
    //setQuery(newQuery); // Update the query state
    searchCourses(newQuery); // Pass the updated query to searchCourses
    //fetchData();
    //console.log(tableData);// Fetch data based on the new query
  };

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
      filterCourses(filters)
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
        <Table tableData={tableData} />
      </div>
    </div>
  );
}