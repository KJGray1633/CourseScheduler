import '../App.css';
import '../styles/table.css'; // Add styles for the table

import React, { memo } from 'react';

const TableRow = memo(({ item, isCourseInSchedule, handleAddCourse, handleDropCourse }) => {
  // Function to capitalize each part of the professor's name
  const capitalizeName = (name) => {
    return name
      .split(' ')
      .map(part => part.charAt(0).toUpperCase() + part.slice(1).toLowerCase())
      .join(' ');
  };

  const capitalizeCourseName = (name) => {
    return name
      .split(' ')
      .map(word => {
        if (['i', 'ii', 'iii', 'iv', 'v'].includes(word.toLowerCase())) {
          return word.toUpperCase(); // Convert Roman numerals to uppercase
        }
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase(); // Capitalize other words
      })
      .join(' ');
  };

  return (
    <tr>
      <td>{`${item.subject.toUpperCase()} ${item.courseCode}`}</td>
      <td>{capitalizeCourseName(item.name)}</td>
      <td>{item.cid}</td>
      <td>
        {item.times.length > 0
          ? `${item.times.map(time => time.day[0]).join('/')} ${item.times[0].startTime} - ${item.times[0].endTime}`
          : 'TBA'}
      </td>
      <td>{item.location.toUpperCase()}</td>
      <td>
        {item.professor.length > 0
        ? item.professor.map(capitalizeName).join(' / ')
        : 'TBA'}
      </td>
      <td>
        {isCourseInSchedule(item) ? (
          <button className="dropButton" onClick={() => handleDropCourse(item)}>Drop</button>
        ) : (
          <button className="addButton" onClick={() => handleAddCourse(item)}>Add</button>
        )}
      </td>
    </tr>

  )
});

export const Table = memo(({ tableData, isCourseInSchedule, handleAddCourse, handleDropCourse }) => (
  <table>
    <thead>
      <tr>
        <th>Course Code</th>
        <th>Course Name</th>
        <th>Reference Number</th>
        <th>Times</th>
        <th>Location</th>
        <th>Professor</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      {Array.isArray(tableData) && tableData.length > 0 ? (
        tableData.map((item, index) => (
          <TableRow
            key={index}
            item={item}
            isCourseInSchedule={isCourseInSchedule}
            handleAddCourse={handleAddCourse}
            handleDropCourse={handleDropCourse}
          />
        ))
      ) : (
        <tr>
          <td colSpan="4">No data available</td>
        </tr>
      )}
    </tbody>
  </table>
));