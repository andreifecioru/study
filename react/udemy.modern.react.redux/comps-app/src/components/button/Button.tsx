import type {ButtonHTMLAttributes} from 'react';
import {type Exclusive} from '@/utils/type-utils.ts';
import classNames from 'classnames';
import {twMerge} from 'tailwind-merge';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> &
  Partial<{
    rounded?: boolean,
    outline?: boolean,
  }> &
  Exclusive<["primary", "secondary", "success", "warning", "danger"], boolean>

function Button({
                  children,
                  primary,
                  secondary,
                  success,
                  warning,
                  danger,
                  outline,
                  rounded,
                  ...rest
                }: ButtonProps) {
  const className = twMerge(classNames(
    // external styling comes 1st
    rest.className,
    // common styling
    'flex', 'items-center',
    'm-1', 'px-3', 'py-1.5', 'border',
    // conditional styling
    {
      'bg-blue-500 text-white border-blue-600': primary,
      'bg-gray-900 text-white border-gray-900': secondary,
      'bg-green-500 text-white border-green-500': success,
      'bg-yellow-400 text-white border-yellow-500': warning,
      'bg-red-500 text-white border-red-500': danger,
      'rounded-full': rounded,
      'bg-white': outline,
      'text-blue-500': primary && outline,
      'text-gray-900': secondary && outline,
      'text-green-500': success && outline,
      'text-yellow-400': warning && outline,
      'text-red-500': danger && outline,
    })
  );

  return (
    // make sure that ...rest comes 1st, so we can override the styles
    <button {...rest} className={className}>{children}</button>
  );
}

export default Button;