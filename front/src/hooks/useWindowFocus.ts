import { useState, useEffect } from 'react';

const useWindowFocus = () => {
  const [isWindowFocused, setIsWindowFocused] = useState(true);

  useEffect(() => {
    const checkWindowFocus = () => {
      setIsWindowFocused(true);
    };

    const checkWindowBlur = () => {
      setIsWindowFocused(false);
    };

    window.addEventListener('focus', checkWindowFocus);
    window.addEventListener('blur', checkWindowBlur);

    return () => {
      window.removeEventListener('focus', checkWindowFocus);
      window.removeEventListener('blur', checkWindowBlur);
    };
  });

  return isWindowFocused;
};

export default useWindowFocus;
