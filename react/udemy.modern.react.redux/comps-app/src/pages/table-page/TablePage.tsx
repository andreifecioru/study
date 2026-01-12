import Table, {type SortOrder, type TableColumn} from '@/components/table/Table.tsx';
import TableHeader from '@/components/table/TableHeader.tsx';

type Fruit = {
  id: number,
  name: string,
  color: string,
  score: number,
}

const TABLE_ROWS: Fruit[] = [
  {name: 'Orange', color: 'bg-orange-500', score: 5},
  {name: 'Apple', color: 'bg-red-300', score: 3},
  {name: 'Banana', color: 'bg-yellow-500', score: 1},
  {name: 'Lime', color: 'bg-green-500', score: 4},
].map((entry, idx) => ({id: idx, ...entry}));


function compare<T>(l: T, r: T, sortBy: (_: T) => string | number, sortOrder: SortOrder): number {
  const leftVal = sortBy(l);
  const rightVal = sortBy(r);

  const orderMultiplier = sortOrder === 'asc' ? 1 : -1;

  if (typeof leftVal === 'number' && typeof rightVal === 'number') {
    return (leftVal - rightVal) * orderMultiplier;
  } else if (typeof leftVal === 'string' && typeof rightVal === 'string') {
    return leftVal.localeCompare(rightVal) * orderMultiplier;
  } else {
    throw new Error(`Unable to compare type: ${typeof leftVal} / ${typeof rightVal}`);
  }
}

function TablePage() {
  const TABLE_COLS: TableColumn<Fruit>[] = [
    {
      label: "Fruit",
      render: (rowData) => rowData.name,
      sortBy: (rowData) => rowData.name
    },

    {
      label: "Color",
      render: (rowData) => <div className={`size-4 border ${rowData.color}`} />
    },

    {
      header: <TableHeader onClick={(sortOrder) =>
        console.log(`Sorting score column: ${sortOrder}`)
      }>
        Score
      </TableHeader>,
      label: "Score",
      render: (rowData) => rowData.score,
      sortBy: (rowData) => rowData.score
    }
  ];

  return (
    <div className="w-1/2 mx-auto mt-32">
      <Table rows={TABLE_ROWS} columns={TABLE_COLS}/>
    </div>
  )
}

export default TablePage;