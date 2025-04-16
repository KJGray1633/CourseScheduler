export default function CustomWeekGridHour({ hour }) {
  const hourInt = parseInt(hour, 10);
  if (hourInt < 8 || hourInt > 22) {
    return null;
  }
  return (
    <div
      style={{
        height: '60px', // Match the grid cell height
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center', // Center the text horizontally
        borderBottom: '1px solid #ddd', // Match grid line styles
        boxSizing: 'border-box', // Ensure padding/borders don't affect height
      }}
    >
      {hour}:00
    </div>
  );
}