import type { SnackbarInfo as SnackbarProps } from '@context/SnackbarProvider';
import * as S from './styles';

const Snackbar = ({ message, type = 'success' }: SnackbarProps) => {
  return <S.SnackBarWrapper type={type}>{message}</S.SnackBarWrapper>;
};

export default Snackbar;
