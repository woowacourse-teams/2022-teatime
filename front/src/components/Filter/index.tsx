import type { PropsWithRequiredChildren } from '@typings/utils';
import * as S from './styles';

interface FilterProps {
  onFilter: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}
const Filter = ({ children, onFilter }: PropsWithRequiredChildren<FilterProps>) => {
  return <S.Select onChange={onFilter}>{children}</S.Select>;
};

export default Filter;
