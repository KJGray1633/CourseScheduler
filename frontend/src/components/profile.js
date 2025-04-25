import '../styles/profile.css';
import { useState, useEffect } from "react";

export function Profile() {
  const [year, setYear] = useState('');
  const [major, setMajor] = useState('');
  const [courseHistory, setCourseHistory] = useState('');

  // Fetch year from the server
  const fetchYear = () => {
    fetch('http://localhost:7000/year')
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch year');
        }
        return response.json();
      })
      .then(data => {
        setYear(data.year); // Access the 'year' attribute from the JSON
      })
      .catch(error => {
        console.error('Error fetching year:', error);
      });
  };

  // Fetch major from the server
  const fetchMajor = () => {
    fetch('http://localhost:7000/major')
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch major');
        }
        return response.json();
      })
      .then(data => {
        setMajor(data.major); // Access the 'major' attribute from the JSON
      })
      .catch(error => {
        console.error('Error fetching major:', error);
      });
  };

  // Fetch course history from the server
  const fetchCourseHistory = () => {
    fetch('http://localhost:7000/courseHistory')
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch course history');
        }
        return response.json();
      })
      .then(data => {
        setCourseHistory(data.courseHistory); // Access the 'courseHistory' attribute
      })
      .catch(error => {
        console.error('Error fetching course history:', error);
      });
  };

  // Use useEffect to fetch data when the component is mounted
  useEffect(() => {
    fetchYear();
    fetchMajor();
    fetchCourseHistory();
  }, []);

  // Function to handle saving the year
  const handleSaveYear = (year) => {
    console.log(year);
    fetch('http://localhost:7000/year', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ year })
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Year saved:', data);
      })
      .catch(error => {
        console.error('Error saving year:', error);
      });
  };

  // Function to handle saving major
  const handleSaveMajor = (major) => {
    console.log(major);
    fetch('http://localhost:7000/major', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ major })
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Major saved:', data);
      })
      .catch(error => {
        console.error('Error saving major:', error);
      });
  };

  // Function to handle saving course history
  const handleSaveCourseHistory = (courseHistory) => {
    fetch('http://localhost:7000/courseHistory', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ courseHistory })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log('Course history saved:', data);
    })
    .catch(error => {
      console.error('Error saving course history:', error);
    })
  };

  return (
    <div>
      <form className="profile-form">
        <h1>Profile</h1>
        <label>
          Year:
          <input type="text" name="year" placeholder="Enter your year" value={year}
           onChange={(event) => setYear(event.target.value)}
           onBlur={() => handleSaveYear(year)} // Trigger on leaving the input
          />
        </label>
        <label>
          Major:
          <input type="text" name="major" placeholder="Enter your major" value={major}
            onChange={(event) => setMajor(event.target.value)}
            onBlur={() => handleSaveMajor(major)} // Trigger on leaving the input
          />
        </label>
        <label>
          Course History:
          <textarea
            name="courseHistory"
            placeholder="Enter your course history"
            value={courseHistory}
            onChange={(event) => setCourseHistory(event.target.value)}
            onBlur={() => handleSaveCourseHistory(courseHistory)} // Trigger on leaving the input
          ></textarea>
        </label>
      </form>
    </div>
  );
}
