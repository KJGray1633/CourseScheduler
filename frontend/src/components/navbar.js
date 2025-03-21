import {Link} from "react-router";
import '../App.css';

export function Navbar() {
  return (
    <div>
      <Link to="/">Home</Link>
      <Link to="/search">Search Courses</Link>
      <Link to="/calendar">Calendar</Link>
    </div>
  );
}