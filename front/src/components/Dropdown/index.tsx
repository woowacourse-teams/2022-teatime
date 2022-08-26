import { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { ROUTES } from '@constants/index';
import { UserDispatchContext } from '@context/UserProvider';
import * as S from './styles';

interface DropdownProps {
  dropdownRef: React.RefObject<HTMLUListElement>;
  isActive: boolean;
  role?: string;
}

const Dropdown = ({ dropdownRef, isActive, role }: DropdownProps) => {
  const dispatch = useContext(UserDispatchContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch({ type: 'DELETE_USER' });
    navigate(ROUTES.HOME);
  };

  return (
    <S.ContentList ref={dropdownRef} isActive={isActive}>
      {role === 'CREW' && (
        <Link to={ROUTES.CREW_HISTORY}>
          <li>히스토리</li>
        </Link>
      )}
      <li onClick={handleLogout}>로그아웃</li>
    </S.ContentList>
  );
};

export default Dropdown;
