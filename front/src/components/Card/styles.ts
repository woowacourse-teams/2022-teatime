import styled, { css, keyframes } from 'styled-components';

const Refresh = keyframes`
 0% { 
    background-position: calc(-100px); 
  } 
  40%,
  100% { 
    background-position: 300px; 
  } 
`;

const CardContainer = styled.div<{ isPossible?: boolean; isPreview?: boolean }>`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 320px;
  padding: 35px 16px;
  border-radius: 20px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  border: 2px solid ${({ theme }) => theme.colors.GRAY_250};
  overflow: hidden;
  cursor: pointer;
  pointer-events: ${({ isPreview }) => (isPreview ? 'none' : 'auto')};
  &:hover {
    transform: scale(1.02);
    transition: ease-in-out 0.2s;
  }
  ${(props) =>
    props.isPossible &&
    css`
      ::before {
        content: '';
        position: absolute;
        top: 15px;
        right: 15px;
        width: 14px;
        height: 14px;
        border-radius: 50%;
        background-color: ${({ theme }) => theme.colors.GREEN_400};
      }
    `}
  @media screen and (${({ theme }) => theme.devices.tablet}) {
    height: 250px;
    padding: 20px 10px;
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    height: 200px;
    padding: 20px 10px;
  }
`;

const IconWrapper = styled.div`
  position: absolute;
  top: 15px;
  left: 15px;
  img {
    margin-right: 8px;
    :hover {
      transform: scale(1.2);
      opacity: 0.7;
      transition: ease-in-out 0.1s;
    }
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    display: flex;
    flex-direction: column;
    img {
      width: 18px;
      height: 18px;
      margin-bottom: 6px;
    }
  }
`;

const CardWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  span {
    font-size: 20px;
    font-weight: 600;
  }
  p {
    text-align: center;
    font-size: 15px;
    margin: 10px 0;
    word-break: break-all;
  }
  @media screen and (${({ theme }) => theme.devices.tablet}) {
    span {
      font-size: 16px;
    }
    p {
      font-size: 12px;
    }
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    p {
      display: none;
    }
  }
`;

const ImageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  margin-bottom: 10px;
  img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }
  @media screen and (${({ theme }) => theme.devices.tablet}) {
    img {
      width: 70px;
      height: 70px;
    }
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    img {
      width: 50px;
      height: 50px;
    }
  }
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  button {
    width: 80%;
    height: 32px;
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
  }
  @media screen and (${({ theme }) => theme.devices.tablet}) {
    button {
      font-size: 14px;
    }
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    button {
      height: 25px;
      border-radius: 4px;
    }
  }
`;

const SkeletonContainer = styled(CardContainer)`
  background-image: linear-gradient(90deg, #e0e0e0 0px, #ededed 30px, #e0e0e0 60px);
  background-color: ${({ theme }) => theme.colors.GRAY_150};
  border: 2px solid ${({ theme }) => theme.colors.GRAY_250};
  animation: ${Refresh} 2s infinite ease-out;
  &:hover {
    transform: scale(1);
  }
`;

const SkeletonInner = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  span {
    background-color: ${({ theme }) => theme.colors.GRAY_300};
    width: 80px;
    height: 60px;
    border-radius: 5px;
  }
  p {
    background-color: ${({ theme }) => theme.colors.GRAY_300};
    width: 100%;
    height: 50px;
    margin: 20px 0;
    border-radius: 5px;
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    span {
      height: 40px;
    }
    p {
      display: none;
    }
  }
`;

const SkeletonImageWrapper = styled(ImageWrapper)`
  div {
    width: 100px;
    height: 100px;
    background-color: ${({ theme }) => theme.colors.GRAY_300};
    border-radius: 50px;
  }
  @media screen and (${({ theme }) => theme.devices.tablet}) {
    div {
      width: 70px;
      height: 70px;
    }
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    div {
      width: 50px;
      height: 50px;
    }
  }
`;

const SkeletonButtonWrapper = styled(ButtonWrapper)`
  div {
    background-color: ${({ theme }) => theme.colors.GRAY_300};
    height: 32px;
    width: 80%;
    height: 32px;
    border-radius: 8px;
  }
  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    div {
      height: 25px;
      border-radius: 4px;
    }
  }
`;

export {
  CardContainer,
  IconWrapper,
  CardWrapper,
  ImageWrapper,
  ButtonWrapper,
  SkeletonContainer,
  SkeletonInner,
  SkeletonImageWrapper,
  SkeletonButtonWrapper,
};
