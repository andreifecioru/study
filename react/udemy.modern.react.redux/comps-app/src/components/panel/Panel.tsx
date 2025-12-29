import {type HTMLAttributes} from 'react';
import classNames from 'classnames';
import {twMerge} from 'tailwind-merge';

type PanelProps = HTMLAttributes<HTMLDivElement>

function Panel({
  children,
  ...rest
  }: PanelProps) {
  const classes = twMerge(
    classNames(
      'border border-gray-300 rounded p-3 shadow-md shadow-gray-300 bg-white w-full',
      rest.className,
    )
  );

  return (
    <div {...rest} className={classes}>
      {children}
    </div>
  );
}

export default Panel;