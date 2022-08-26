import * as S from './styles';

interface DropdownProps {
  isActive: boolean;
  children: React.ReactNode;
}

const Dropdown = ({ isActive, children }: DropdownProps) => {
  return <S.ContentList isActive={isActive}>{children}</S.ContentList>;
};

export default Dropdown;
