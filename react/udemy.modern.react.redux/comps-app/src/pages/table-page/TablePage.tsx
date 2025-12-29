import Table from '@/components/table/Table.tsx';
import {type TableEntry} from '@/components/table/Table.tsx';

const TABLE_DATA: TableEntry[] = [
  {name: 'Orange', color: 'bg-orange-500', score: 5},
  {name: 'Apple', color: 'bg-red-300', score: 3},
  {name: 'Banana', color: 'bg-yellow-500', score: 1},
  {name: 'Lime', color: 'bg-green-500', score: 4},
]

function TablePage() {
  return(
    <div className="w-1/2 mx-auto mt-32">
      <Table data={TABLE_DATA}/>
    </div>
  )
}

export default TablePage;