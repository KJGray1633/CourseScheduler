import { useState } from "react";
import { NavLink } from "react-router";
import '../App.css';
import '../styles/navbar.css';
import { Profile } from '../components/profile.js';

export function Navbar() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  function handleProfileClick() {
    setIsModalOpen(true);
  }

  function closeModal() {
    setIsModalOpen(false);
  }

  return (
    <nav className="navbar">
      <button className="profile-button" onClick={handleProfileClick}>
        Profile
      </button>
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
      {isModalOpen && (
              <div className="modal">
                <div className="modal-content profile-modal">
                  <button className="close-button" onClick={closeModal}>
                    &times;
                  </button>
                  <Profile />
                </div>
              </div>
            )}
    </nav>
  );
}