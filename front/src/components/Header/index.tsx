import { useContext, useState } from 'react';

import { UserStateContext } from '@context/UserProvider';
import Dropdown from '@components/Dropdown';
import { ROUTES } from '@constants/index';

import LogoIcon from '@assets/logo.svg';
import * as S from './styles';

const Header = () => {
  const { userData } = useContext(UserStateContext);
  const [isOpen, setIsOpen] = useState(false);

  const toggleDropdown = () => {
    setIsOpen(false);
  };

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} />
        <h1>티타임</h1>
      </S.LogoLink>
      {userData && (
        <S.ProfileContainer>
          <S.ProfileImage
            src={userData.image}
            alt="프로필 이미지"
            onClick={() => setIsOpen(!isOpen)}
          />
          {isOpen && <Dropdown onClick={toggleDropdown} />}
        </S.ProfileContainer>
      )}
    </S.HeaderContainer>
  );
};

export default Header;
