import { getStorage } from '@utils/localStorage';
import { LOCAL_DB, ROUTES } from '@constants/index';
import { UserInfo } from '@typings/domain';

import LogoIcon from '@assets/logo.svg';
import * as S from './styles';

const Header = () => {
  const userInfo: UserInfo = getStorage(LOCAL_DB.USER);

  return (
    <S.HeaderContainer>
      <S.LogoLink to={userInfo ? `/${userInfo.role.toLowerCase()}` : ROUTES.HOME}>
        <S.LogoImage src={LogoIcon} />
        <h1>티타임</h1>
      </S.LogoLink>
      {userInfo && <S.ProfileImage src={userInfo.image} alt="프로필 이미지" />}
    </S.HeaderContainer>
  );
};

export default Header;
