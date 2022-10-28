import * as S from './styles';

interface FilterProps {
  children: React.ReactNode;
  onFilter: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}
const Filter = ({ children, onFilter }: FilterProps) => {
  return <S.Select onChange={onFilter}>{children}</S.Select>;
};

export default Filter;
