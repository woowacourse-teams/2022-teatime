import { Link } from 'react-router-dom';
import styled, { keyframes } from 'styled-components';

const FadeIn = keyframes`
  from {
    transform: translateX(10%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
`;

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

const Form = styled.form`
  animation: ${FadeIn} 0.8s;

  input {
    width: 170px;
    height: 34px;
    text-align: center;
    border: 1px solid ${({ theme }) => theme.colors.GRAY_300};
    border-radius: 20px;
    font-size: 16px;

    ::placeholder {
      color: ${({ theme }) => theme.colors.GRAY_500};
      font-size: 14px;
    }
  }

  button {
    width: 40px;
    height: 30px;
    margin-left: 6px;
    background-color: ${({ theme }) => theme.colors.GRAY_200};
    border-radius: 6px;
    border: none;
    cursor: pointer;

    :hover {
      opacity: 0.7;
    }
  }
`;

export { HeaderContainer, LogoLink, LogoImage, ProfileContainer, ProfileWrapper, Form };
