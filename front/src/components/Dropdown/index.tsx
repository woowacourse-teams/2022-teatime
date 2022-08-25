import { useContext, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import useOutsideClick from '@hooks/useOutsideClick';
import { ROUTES } from '@constants/index';
import { UserDispatchContext } from '@context/UserProvider';
import * as S from './styles';

interface DropdownProps {
  name: string;
  image: string;
}

const Dropdown = ({ name, image }: DropdownProps) => {
  const dispatch = useContext(UserDispatchContext);
  const navigate = useNavigate();
  const dropdownRef = useRef(null);
  const profileRef = useRef(null);
  const [isActive, setIsActive] = useOutsideClick(profileRef, dropdownRef, false);

  const toggleDropdown = () => {
    setIsActive(!isActive);
  };

  // const handleLogout = () => {
  //   dispatch({ type: 'DELETE_USER' });
  //   navigate(ROUTES.HOME);
  // };

  return (
    <S.ProfileContainer>
      <S.ProfileWrapper ref={profileRef} onClick={toggleDropdown}>
        <span>{name}</span>
        <img src={image} alt="프로필 이미지" />
      </S.ProfileWrapper>
      <S.ContentList ref={dropdownRef} isActive={isActive}>
        <ul>
          <Link to={ROUTES.CREW_HISTORY}>
            <li>히스토리</li>
          </Link>
          <li>로그아웃</li>
        </ul>
      </S.ContentList>
    </S.ProfileContainer>
  );
};

export default Dropdown;
