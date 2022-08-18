import { Link } from 'react-router-dom';
import styled from 'styled-components';

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 50px;
  padding: 0 10%;
  box-shadow: 0px 3px 4px ${({ theme }) => theme.colors.GRAY_300};

  h1 {
    font-size: 18px;
  }
`;

const LogoImage = styled.img`
  width: 30px;
  margin-right: 8px;
`;

const LogoLink = styled(Link)`
  display: flex;
  color: ${({ theme }) => theme.colors.BLACK};
  align-items: center;
  flex-direction: row;
  cursor: pointer;
`;

const ProfileContainer = styled.div`
  position: relative;
`;

const ProfileImage = styled.img`
  width: 35px;
  height: 35px;
  border-radius: 20px;
`;

export { HeaderContainer, LogoLink, LogoImage, ProfileContainer, ProfileImage };
