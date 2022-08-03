import * as S from './styles';

import { ROUTES } from '@constants/index';

import LogoIcon from '@assets/logo.svg';

const Header = () => {
  return (
    <S.HeaderContainer>
      <S.LogoLink to={ROUTES.HOME}>
        <img src={LogoIcon} />
        <h1>티타임</h1>
      </S.LogoLink>
    </S.HeaderContainer>
  );
};

export default Header;
