import styled, { keyframes } from 'styled-components';

const Flow = keyframes`
    0%,
  100% {
    clip-path: polygon(
      0% 45%,
      16% 44%,
      33% 50%,
      54% 60%,
      70% 61%,
      84% 59%,
      100% 52%,
      100% 100%,
      0% 100%
    );
  }

  50% {
    clip-path: polygon(
      0% 60%,
      15% 65%,
      34% 66%,
      51% 62%,
      67% 50%,
      84% 45%,
      100% 46%,
      100% 100%,
      0% 100%
    );
  }
`;

const Container = styled.div`
  height: calc(100vh - 50px);
  width: 100%;
  background-color: ${({ theme }) => theme.colors.BLUE_300};

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    position: fixed;
  }
`;

const MainSection = styled.section`
  padding-top: 100px;
  width: 100vw;

  &::before {
    content: '';
    position: absolute;
    width: 100vw;
    height: 100vh;
    left: 50%;
    background-color: ${({ theme }) => theme.colors.GRAY_800};
    border-radius: 50%;
    transform-origin: bottom;
    transform: translateX(-60%) scale(4);
    bottom: 100px;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    padding-top: 0;
    padding: 10%;
  }
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  padding-top: 120px;
  padding-left: 20%;

  p {
    color: ${({ theme }) => theme.colors.WHITE};
    z-index: 1;
    font-size: 25px;
    padding-top: 15px;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    padding-left: 0;

    p {
      font-size: 20px;
    }
  }
`;

const OverlapContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  padding-top: 80px;

  h2 {
    position: absolute;
    left: 20%;
    color: ${({ theme }) => theme.colors.WHITE};
    font-size: 80px;
  }

  h2:nth-child(1) {
    color: ${({ theme }) => theme.colors.WHITE};
  }

  h2:nth-child(2) {
    color: ${({ theme }) => theme.colors.BLUE_600};
    animation: ${Flow} 4s ease-in-out infinite;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    padding-top: 50px;

    h2 {
      left: 10%;
      font-size: 40px;
    }
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  z-index: 1;
  padding: 0 20%;
  margin-top: 10%;
  gap: 20px;

  button {
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1;
    width: 50%;
    height: 70px;
    border: none;
    border-radius: 12px;
    font-size: 20px;
    font-weight: 500;
    cursor: pointer;
    box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2), inset 1px 1px 1px rgba(0, 0, 0, 0.1);

    &:hover {
      transition: 0.2s;
    }
  }

  img {
    width: 30px;
    height: 30px;
    margin-right: 15px;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    padding: 0;
    margin-top: 100px;

    button {
      width: 100%;
      font-size: 16px;
    }

    img {
      width: 20px;
      height: 20px;
      margin-right: 5px;
    }
  }
`;

const SlackButton = styled.button`
  color: ${({ theme }) => theme.colors.BLACK};
  background-color: ${({ theme }) => theme.colors.WHITE};

  &:hover {
    background-color: ${({ theme }) => theme.colors.PURPLE_700};
    color: ${({ theme }) => theme.colors.WHITE};
  }
`;

const TeamIntroButton = styled.button`
  color: ${({ theme }) => theme.colors.BLACK};
  background-color: ${({ theme }) => theme.colors.ORANGE_200};

  &:hover {
    background-color: ${({ theme }) => theme.colors.ORANGE_600};
    color: ${({ theme }) => theme.colors.WHITE};
  }
`;

export {
  Container,
  MainSection,
  Description,
  OverlapContent,
  ButtonContainer,
  SlackButton,
  TeamIntroButton,
};
