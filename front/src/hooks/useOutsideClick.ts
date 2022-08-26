import { Dispatch, SetStateAction, useEffect, useState } from 'react';

type UseOutsideClickType = (
  profile: React.RefObject<HTMLDivElement>,
  el: React.RefObject<HTMLUListElement>,
  initialState: boolean
) => [boolean, Dispatch<SetStateAction<boolean>>];

const useOutsideClick: UseOutsideClickType = (profile, el, initialState) => {
  const [isActive, setIsActive] = useState(initialState);

  useEffect(() => {
    const pageClickEvent = (e: MouseEvent) => {
      if (profile.current !== null && profile.current.contains(e.target as HTMLDivElement)) {
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
  }, [isActive, el]);

  return [isActive, setIsActive];
};

export default useOutsideClick;
