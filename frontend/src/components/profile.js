import '../styles/profile.css';

export function Profile() {
  return (
    <div>
      <form className="profile-form">
        <label>
          Year:
          <input type="text" name="year" placeholder="Enter your year" />
        </label>
        <label>
          Major:
          <input type="text" name="major" placeholder="Enter your major" />
        </label>
        <label>
          Course History:
          <textarea name="courseHistory" placeholder="Enter your course history"></textarea>
        </label>
      </form>
    </div>
  );
}