import type {SortOrder} from '@/components/table/Table.tsx';
import {type ReactNode, useState} from 'react';
import {GoChevronDown, GoChevronUp, GoDash} from "react-icons/go";

type TableHeaderProps = {
  onClick: (sortOrder: SortOrder) => void,
  children: ReactNode
}

function TableHeader({onClick, children}: TableHeaderProps) {
  const [sortOrder, setSortOrder] = useState<SortOrder>('none');

  const handleClick = () => {
    let newSortOrder = sortOrder;
    switch (sortOrder) {
      case 'none':
        newSortOrder = 'asc';
        break;
      case 'asc':
        newSortOrder = 'desc';
        break;
      case 'desc':
        newSortOrder = 'none';
        break;
    }
    setSortOrder(newSortOrder);
    onClick(newSortOrder);
  }

  const renderSortIcon = () => {
    switch (sortOrder) {
      case 'asc': return <GoChevronUp />
      case 'desc': return <GoChevronDown />
      case 'none': return <GoDash />
    }
  }

  return (
    <div className="flex items-center" onClick={handleClick}>
      <span className="pr-2">{renderSortIcon()}</span>
      {children}
    </div>
  );
}

export default TableHeader;