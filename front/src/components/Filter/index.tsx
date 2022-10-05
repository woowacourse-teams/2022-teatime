import * as S from './styles';

interface FilterProps {
  children: React.ReactNode;
  onFilterStatus: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}
const Filter = ({ children, onFilterStatus }: FilterProps) => {
  return <S.Select onChange={onFilterStatus}>{children}</S.Select>;
};

export default Filter;
