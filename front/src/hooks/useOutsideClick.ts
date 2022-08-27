import { Dispatch, SetStateAction, useEffect, useState } from 'react';

type UseOutsideClickType = (
  element: React.RefObject<HTMLDivElement>,
  initialState: boolean
) => [boolean, Dispatch<SetStateAction<boolean>>];

const useOutsideClick: UseOutsideClickType = (element, initialState) => {
  const [isActive, setIsActive] = useState(initialState);

  useEffect(() => {
    const pageClickEvent = (e: MouseEvent) => {
      if (element.current !== null && element.current.contains(e.target as HTMLDivElement)) {
        return;
      }
      setIsActive(!isActive);
    };

    if (isActive) {
      window.addEventListener('click', pageClickEvent);
    }

    return () => {
      window.removeEventListener('click', pageClickEvent);
    };
  }, [isActive]);

  return [isActive, setIsActive];
};

export default useOutsideClick;
