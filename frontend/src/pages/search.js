import { Navbar } from '../components/navbar.js';
import { Table, fetchData } from '../components/table.js';

function searchCourses() {
  console.log('Searching for courses...');
  const query = document.getElementById('query').value;
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
    fetchData("search");
  })
}

export function Search() {
  return (
    <div>
      <Navbar />
      <div>
        <h1>Search</h1>
        <input type="text" id="query" placeholder="Search for courses..." />
        <button id="searchButton" onClick={searchCourses}>Search</button>
        <Table path={'search'}/>
      </div>
    </div>
  );
}