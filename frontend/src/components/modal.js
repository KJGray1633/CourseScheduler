import React from 'react';
import '../styles/modal.css'; // Add styles for the modal

export function Modal({ message, onClose }) {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <p>{message}</p>
        <button className="close-modal" onClick={onClose}>&times;</button>
      </div>
    </div>
  );
}