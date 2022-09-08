import { useContext, useRef } from 'react';
import { useNavigate, Link } from 'react-router-dom';

import Dropdown from '@components/Dropdown';
import Conditional from '@components/Conditional';
import useOutsideClick from '@hooks/useOutsideClick';
import { UserStateContext, UserDispatchContext } from '@context/UserProvider';
import { ROUTES } from '@constants/index';
import * as S from './styles';

import LogoIcon from '@assets/logo.svg';

const Header = () => {
  const navigate = useNavigate();
  const { userData } = useContext(UserStateContext);
  const dispatch = useContext(UserDispatchContext);
  const profileRef = useRef(null);
  const [isActive, setIsActive] = useOutsideClick(profileRef, false);

  const handleLogout = () => {
    dispatch({ type: 'DELETE_USER' });
    navigate(ROUTES.HOME);
  };

  const toggleDropdown = () => {
    setIsActive(!isActive);
  };

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} alt="티타임 로고" />
        <h1>티타임</h1>
      </S.LogoLink>
      {userData && (
        <S.ProfileContainer>
          <S.ProfileWrapper ref={profileRef} onClick={toggleDropdown}>
            <span>{userData.name}</span>
            <img src={userData.image} alt="프로필 이미지" />
          </S.ProfileWrapper>
          <Dropdown isActive={isActive}>
            <Conditional condition={userData.role === 'COACH'}>
              <Link to={ROUTES.COACH_HISTORY}>
                <li>히스토리</li>
              </Link>
              <Link to={ROUTES.SCHEDULE}>
                <li>스케쥴 관리</li>
              </Link>
              <li onClick={handleLogout}>로그아웃</li>
            </Conditional>

            <Conditional condition={userData.role === 'CREW'}>
              <Link to={ROUTES.CREW_HISTORY}>
                <li>히스토리</li>
              </Link>
              <li onClick={handleLogout}>로그아웃</li>
            </Conditional>
          </Dropdown>
        </S.ProfileContainer>
      )}
    </S.HeaderContainer>
  );
};

export default Header;
