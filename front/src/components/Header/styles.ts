import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 50px;
  padding: 0 10%;
  box-shadow: 0px 3px 4px ${({ theme }) => theme.colors.GRAY_300};
  background-color: ${({ theme }) => theme.colors.WHITE};

  h1 {
    font-size: 18px;
  }
`;

const LogoImage = styled.img`
  width: 30px;
  height: 30px;
  margin-right: 8px;
`;

const LogoLink = styled(Link)`
  display: flex;
  color: ${({ theme }) => theme.colors.BLACK};
  align-items: center;
  flex-direction: row;
  cursor: pointer;
`;

const MainButton = styled.button`
  padding: 10px 22px;
  background-color: ${({ theme }) => theme.colors.GREEN_700};
  color: ${({ theme }) => theme.colors.WHITE};
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;

  :hover {
    opacity: 0.7;
  }
`;

const ProfileContainer = styled.div`
  display: flex;
  align-items: center;
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

const Input = styled.input`
  width: 100%;
  height: 45px;
  text-align: center;
  border: 1px solid ${({ theme }) => theme.colors.GRAY_300};
  border-radius: 20px;
  font-size: 16px;

  ::placeholder {
    color: ${({ theme }) => theme.colors.GRAY_500};
    font-size: 14px;
  }
`;

const RoleButton = styled.button<{ isRole: boolean }>`
  position: relative;
  display: flex;
  justify-content: ${({ isRole }) => (isRole ? 'left' : 'right')};
  align-items: center;
  width: 76px;
  height: 40px;
  margin-right: 15px;
  border-radius: 20px;
  padding: 0 10px;
  border: none;
  background-color: ${({ isRole, theme }) =>
    isRole ? theme.colors.BLUE_800 : theme.colors.GRAY_500};
  color: ${({ theme }) => theme.colors.WHITE};
  transition: all 0.3s ease-out;
  font-size: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  cursor: pointer;

  div {
    position: absolute;
    left: 6px;
    width: 26px;
    height: 26px;
    border-radius: 50px;
    background-color: ${({ theme }) => theme.colors.GRAY_150};
    transition: all 0.3s ease-out;
    ${({ isRole }) =>
      isRole &&
      css`
        transform: translate(38px, 0);
      `}
  }
`;

export {
  HeaderContainer,
  LogoLink,
  LogoImage,
  MainButton,
  ProfileContainer,
  ProfileWrapper,
  Input,
  RoleButton,
};
