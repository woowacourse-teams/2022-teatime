import { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { ROUTES } from '@constants/index';
import { UserDispatchContext } from '@context/UserProvider';
import * as S from './styles';

interface Props {
  onClick: () => void;
}

const Dropdown = ({ onClick }: Props) => {
  const dispatch = useContext(UserDispatchContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch({ type: 'DELETE_USER' });
    navigate(ROUTES.HOME);
  };

  return (
    <S.ContentList onClick={onClick}>
      <Link to={ROUTES.CREW_HISTORY}>
        <li>히스토리</li>
      </Link>
      <li onClick={handleLogout}>로그아웃</li>
    </S.ContentList>
  );
};

export default Dropdown;
