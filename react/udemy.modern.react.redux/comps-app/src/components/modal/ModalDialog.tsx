import {type ReactNode, useEffect, useRef} from 'react';
import {createPortal} from 'react-dom';

type ModalDialogProps = {
  title: string,
  children: ReactNode,
  actionBar: ReactNode,
  onClose: () => void
}

function ModalDialog({title, children, actionBar, onClose}: ModalDialogProps) {
  const modalRef = useRef<HTMLDivElement>(null);

  // Disable scrolling while modal is active
  useEffect(() => {
    document.body.classList.add('overflow-hidden');
    return () => document.body.classList.remove('overflow-hidden');
  }, []);

  return createPortal(
    <div className="fixed inset-0 flex items-center justify-center">
      <div className="absolute inset-0 bg-gray-300 opacity-80" onClick={onClose}/>

      <div ref={modalRef}
           className="relative border border-gray-300 rounded-t-md shadow-md shadow-gray-300 bg-white w-1/3">
        <div className="text-center rounded-t-md text-2xl font-bold text-white py-2 bg-blue-900">
          {title}
        </div>
        <div className="py-4 px-8">
          {children}
        </div>
        <div className="flex justify-end py-2 px-4">
          {actionBar}
        </div>
      </div>
    </div>,
    document.querySelector('#modal-container')!
  );
}

export default ModalDialog;
export {type ModalDialogProps};