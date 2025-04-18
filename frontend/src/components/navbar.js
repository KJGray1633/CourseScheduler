import { NavLink } from "react-router";
import '../App.css';
import '../styles/navbar.css';

export function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-links">
        <NavLink
          className={({ isActive }) => isActive ? "link-styles active-link" : "link-styles"}
          to="/"
        >
          Home
        </NavLink>
        <NavLink
          className={({ isActive }) => isActive ? "link-styles active-link" : "link-styles"}
          to="/search"
        >
          Search Courses
        </NavLink>
        <NavLink
          className={({ isActive }) => isActive ? "link-styles active-link" : "link-styles"}
          to="/calendar"
        >
          Calendar
        </NavLink>
      </div>
    </nav>
  );
}