import LogoIcon from '@assets/logo.png';
import { ROUTES } from '@constants/index';
import * as S from './styles';

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
