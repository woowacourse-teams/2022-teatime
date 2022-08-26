import { useContext, useRef } from 'react';

import Dropdown from '@components/Dropdown';
import useOutsideClick from '@hooks/useOutsideClick';
import { UserStateContext } from '@context/UserProvider';
import { ROUTES } from '@constants/index';
import * as S from './styles';

import LogoIcon from '@assets/logo.svg';

const Header = () => {
  const { userData } = useContext(UserStateContext);
  const dropdownRef = useRef(null);
  const profileRef = useRef(null);
  const [isActive, setIsActive] = useOutsideClick(profileRef, dropdownRef, false);

  const toggleDropdown = () => {
    setIsActive(!isActive);
  };

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} />
        <h1>티타임</h1>
      </S.LogoLink>
      <S.ProfileContainer>
        {userData && (
          <S.ProfileWrapper ref={profileRef} onClick={toggleDropdown}>
            <span>{userData.name}</span>
            <img src={userData.image} alt="프로필 이미지" />
          </S.ProfileWrapper>
        )}
        {isActive && (
          <Dropdown dropdownRef={dropdownRef} isActive={isActive} role={userData?.role} />
        )}
      </S.ProfileContainer>
    </S.HeaderContainer>
  );
};

export default Header;
