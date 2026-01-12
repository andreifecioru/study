import type {ReactNode} from 'react';

type SortOrder = 'asc' | 'desc' | 'none';

type TableColumn<T> = {
  header?: ReactNode
  label: string,
  render: (rowData: T) => ReactNode,
  sortBy?: (rowData: T) => string | number,
}

type TableProps<T extends { id: number }> = {
  rows: T[],
  columns: TableColumn<T>[]
}

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

function Table<T extends { id: number }>({rows, columns}: TableProps<T>) {
  const renderTableHeader = () => {
    return (
      <thead>
      <tr className="border-b-2">
        {columns.map(col =>
          <th key={col.label} className="px-2">{col.header ??  col.label}</th>
        )}
      </tr>
      </thead>
    );
  }

  const renderTableBody = () => {
    const sortedRows = [...rows];
    if (columns[0].sortBy) {
      sortedRows.sort((row1, row2) => {
        return compare(row1, row2, columns[0].sortBy!, 'asc');
      });
    }

    return (
      <tbody>
      {sortedRows.map(row =>
        <tr key={row.id} className="border-b">
          {columns.map(col =>
            <td key={col.label} className="p-3 text-center">
              {col.render(row)}
            </td>
          )}
        </tr>
      )}
      </tbody>
    );
  }

  return (
    <div>
      <table className="table-auto">
        {renderTableHeader()}
        {renderTableBody()}
      </table>
    </div>
  );
}

export default Table;
export {type TableColumn, type TableProps, type SortOrder};