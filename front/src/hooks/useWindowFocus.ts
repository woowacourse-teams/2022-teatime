import { useState, useEffect } from 'react';

const useWindowFocus = () => {
  const [isWindowFocused, setIsWindowFocused] = useState(true);

  useEffect(() => {
    const handleWindowFocus = () => {
      setIsWindowFocused(true);
    };

    const handleWindowBlur = () => {
      setIsWindowFocused(false);
    };

    window.addEventListener('focus', handleWindowFocus);
    window.addEventListener('blur', handleWindowBlur);

    return () => {
      window.removeEventListener('focus', handleWindowFocus);
      window.removeEventListener('blur', handleWindowBlur);
    };
  });

  return isWindowFocused;
};

export default useWindowFocus;
