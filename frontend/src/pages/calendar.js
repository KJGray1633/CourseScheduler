import { Navbar } from '../components/navbar.js';
import React, { useState, useEffect } from 'react';

export function Calendar() {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    // Fetch schedule data from the backend
    fetch("http://localhost:7000/schedule")
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch schedule");
        }
        return response.json();
      })
      .then(data => {
        setCourses(data); // Update state with fetched courses
      })
      .catch(error => {
        console.error("Error fetching schedule:", error);
      });
  }, []);

  return (
    <div>
      <Navbar />
      <div>
        <h1>Calendar</h1>
        <ul>
          {courses.map((course, index) => (
            <li key={index}>
              <strong>{course.name}</strong> - {course.department} ({course.courseCode})
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}