import * as S from './styles';

interface DropdownProps {
  dropdownRef: React.RefObject<HTMLUListElement>;
  isActive: boolean;
  children: React.ReactNode;
}

const Dropdown = ({ dropdownRef, isActive, children }: DropdownProps) => {
  return (
    <S.ContentList ref={dropdownRef} isActive={isActive}>
      {children}
    </S.ContentList>
  );
};

export default Dropdown;
