import LogoIcon from '@assets/logo.png';
import { ROUTES } from '@constants/index';
import { HeaderContainer, LogoLink } from './styles';

const Header = () => {
  return (
    <HeaderContainer>
      <LogoLink to={ROUTES.HOME}>
        <img src={LogoIcon} />
        <h1>티타임</h1>
      </LogoLink>
    </HeaderContainer>
  );
};

export default Header;
