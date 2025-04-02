import { Navbar } from '../components/navbar.js';
import { Table } from '../components/table.js';

export function Search() {
  return (
    <div>
      <Navbar />
      <div>
        <h1>Search</h1>
        <Table path={'search'}/>
      </div>
    </div>
  );
}