import { Dispatch, SetStateAction, useEffect, useState } from 'react';

type UseOutsideClickType = (
  profile: React.RefObject<HTMLDivElement>,
  el: React.RefObject<HTMLDivElement>,
  initialState: boolean
) => [boolean, Dispatch<SetStateAction<boolean>>];

const useOutsideClick: UseOutsideClickType = (profile, el, initialState) => {
  const [isActive, setIsActive] = useState(initialState);

  useEffect(() => {
    const pageClickEvent = (e: MouseEvent) => {
      if (profile.current.contains(e.target)) {
        return;
      }
      if (el.current !== null && !el.current.contains(e.target as HTMLDivElement)) {
        setIsActive(!isActive);
      }
    };
    if (isActive) {
      window.addEventListener('click', pageClickEvent);
    }

    return () => {
      window.removeEventListener('click', pageClickEvent);
    };
  }, [isActive, el]);

  return [isActive, setIsActive];
};

export default useOutsideClick;
