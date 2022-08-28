import { useState } from 'react';

const useToggle = () => {
  const [isOpen, setIsOpen] = useState(false);

  const openElement = () => {
    setIsOpen(true);
  };

  const closeElement = () => {
    setIsOpen(false);
  };

  return { isOpen, openElement, closeElement };
};
export default useToggle;
