import { useEffect, useState } from 'react';
import { getStorage, setStorage } from '@utils/localStorage';

const useLocalStorage = <T>(key: string, initialState: T) => {
  const [state, setState] = useState(getStorage(key) || initialState);

  useEffect(() => {
    setStorage(key, state);
  }, [key, state]);

  return { state, setState };
};

export default useLocalStorage;
