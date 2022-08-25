import { useContext } from 'react';

import { UserStateContext } from '@context/UserProvider';
import Dropdown from '@components/Dropdown';
import { ROUTES } from '@constants/index';

import LogoIcon from '@assets/logo.svg';
import * as S from './styles';

const Header = () => {
  const { userData } = useContext(UserStateContext);

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} />
        <h1>티타임</h1>
      </S.LogoLink>
      {userData && <Dropdown name={userData.name} image={userData.image} />}
    </S.HeaderContainer>
  );
};

export default Header;
