import logo from './logo.svg';
import './App.css';

function App() {
  function searchCourses() {
    console.log("searching courses");
    return (
      <div className="search-courses">)
        <input type="text" placeholder="Search for courses" />
        <button>Search</button>
      </div>
    );
  }
  return (
    <div className="nav-bar">
      <button>Home</button>
      <button onClick={() => searchCourses()}>Search Courses</button>
      <button>Calendar</button>
    </div>
  );
}

export default App;
