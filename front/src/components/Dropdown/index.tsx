import type { PropsWithRequiredChildren } from '@typings/utils';
import * as S from './styles';

interface DropdownProps {
  isActive: boolean;
}

const Dropdown = ({ isActive, children }: PropsWithRequiredChildren<DropdownProps>) => {
  return <S.ContentList isActive={isActive}>{children}</S.ContentList>;
};

export default Dropdown;
