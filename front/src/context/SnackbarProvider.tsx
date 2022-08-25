import { createContext, useEffect, useState } from 'react';
import Snackbar from '@components/Snackbar';
import Portal from '../Portal';

type SnackbarType = 'success' | 'error';

export interface SnackbarInfo {
  message: string;
  type?: SnackbarType;
}

type SnackbarContextType = ({ message, type }: SnackbarInfo) => void;

export const SnackbarContext = createContext<SnackbarContextType>(() => null);

const SnackbarProvider = ({ children }: { children: React.ReactNode }) => {
  const [snackbarInfo, setSnackbarInfo] = useState<SnackbarInfo>({
    message: '',
    type: 'success',
  });

  const showSnackbar = ({ message, type }: SnackbarInfo) => {
    setSnackbarInfo({ message, type });
  };

  useEffect(() => {
    const snackbarTimer = setTimeout(() => {
      setSnackbarInfo({ message: '', type: 'success' });
    }, 2000);

    return () => clearTimeout(snackbarTimer);
  }, [snackbarInfo.message]);

  return (
    <SnackbarContext.Provider value={showSnackbar}>
      {children}
      {snackbarInfo.message && (
        <Portal id="snackbar">
          <Snackbar key={Date.now()} message={snackbarInfo.message} type={snackbarInfo.type} />
        </Portal>
      )}
    </SnackbarContext.Provider>
  );
};

export default SnackbarProvider;
