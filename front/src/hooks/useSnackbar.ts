import { useContext } from 'react';
import { SnackbarContext } from '@context/SnackbarProvider';

const useSnackbar = () => {
  const showSnackbar = useContext(SnackbarContext);

  return showSnackbar;
};

export default useSnackbar;
