import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 60px;
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

const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 4px 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  border-radius: 90px;
  transition: box-shadow 0.2s ease;
  cursor: pointer;

  :hover {
    box-shadow: 0 1px 8px rgba(0, 0, 0, 0.3);
  }

  span {
    font-weight: bold;
    font-size: 14px;
    margin: 0 10px;
  }

  img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
  }
`;

const ContentList = styled.div<{ isActive: boolean }>`
  position: absolute;
  z-index: 100;
  width: 150px;
  top: 48px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  box-shadow: 0 0 10px rgb(190, 190, 190);
  border-radius: 8px;
  opacity: 0;
  overflow: hidden;
  visibility: hidden;

  ${(props) =>
    props.isActive &&
    css`
      opacity: 1;
      visibility: visible;
    `}

  li {
    padding: 8px;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.GRAY_600};
    text-align: center;
    list-style: none;
    cursor: pointer;

    :hover {
      background-color: ${({ theme }) => theme.colors.GRAY_300};
    }
  }
`;

export { HeaderContainer, LogoLink, LogoImage, ProfileContainer, ProfileWrapper, ContentList };
