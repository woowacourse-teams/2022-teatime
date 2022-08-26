import { useContext, useRef } from 'react';
import { useNavigate, Link } from 'react-router-dom';

import Dropdown from '@components/Dropdown';
import useOutsideClick from '@hooks/useOutsideClick';
import { UserStateContext, UserDispatchContext } from '@context/UserProvider';
import { ROUTES } from '@constants/index';
import * as S from './styles';

import LogoIcon from '@assets/logo.svg';
import Conditional from '../Conditional/index';

const Header = () => {
  // const { userData } = useContext(UserStateContext);
  const userData = {
    name: '아키',
    image: 'https://avatars.githubusercontent.com/u/23068523?v=4',
    role: 'CREW',
  };
  const dispatch = useContext(UserDispatchContext);
  const navigate = useNavigate();
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
        <S.LogoImage src={LogoIcon} />
        <h1>티타임</h1>
      </S.LogoLink>
      {userData && (
        <S.ProfileContainer>
          <S.ProfileWrapper ref={profileRef} onClick={toggleDropdown}>
            <span>{userData.name}</span>
            <img src={userData.image} alt="프로필 이미지" />
          </S.ProfileWrapper>
          <Conditional condition={userData.role === 'CREW'}>
            <Dropdown isActive={isActive}>
              <Link to={ROUTES.CREW_HISTORY}>
                <li>히스토리</li>
              </Link>
              <li onClick={handleLogout}>로그아웃</li>
            </Dropdown>
          </Conditional>
          <Conditional condition={userData.role === 'COACH'}>
            <Dropdown isActive={isActive}>
              <li onClick={handleLogout}>로그아웃</li>
            </Dropdown>
          </Conditional>
        </S.ProfileContainer>
      )}
    </S.HeaderContainer>
  );
};

export default Header;
