import React from 'react';

function CustomToolbar({ label, onNavigate, onView }) {
  return (
    <div className="custom-toolbar">
      <button onClick={() => onNavigate('PREV')}>Previous</button>
      <span>{label}</span>
      <button onClick={() => onNavigate('NEXT')}>Next</button>
      <button onClick={() => onView('day')}>Day</button>
      <button onClick={() => onView('week')}>Week</button>
      <button onClick={() => onView('month')}>Month</button>
    </div>
  );
}

export default CustomToolbar;