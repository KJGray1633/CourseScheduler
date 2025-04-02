import { Navbar } from '../components/navbar.js';
import { Table } from '../components/table.js';

export function Home() {
  return (
    <div>
      <Navbar />
      <div>
        <h1>Home</h1>
        <Table path={'schedule'}/>
      </div>
    </div>
  );
}