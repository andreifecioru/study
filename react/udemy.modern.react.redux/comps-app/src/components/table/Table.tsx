type TableEntry = {
  name: string,
  color: string,
  score: number
}

type TableProps = {
  data: TableEntry[]
}

function Table({data}: TableProps) {
  return(
    <div>
      Table here...
    </div>
  );
}

export default Table;
export {type TableEntry};