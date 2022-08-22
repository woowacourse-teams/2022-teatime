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
  overflow-x: hidden;
`;

const MainSection = styled.section`
  padding-top: 200px;
  width: 100vw;
  overflow-x: hidden;

  &::before {
    content: '';
    position: absolute;
    overflow-x: hidden;
    width: 100vw;
    height: 100vh;
    left: 50%;
    background-color: ${({ theme }) => theme.colors.GRAY_800};
    border-radius: 50%;
    transform-origin: bottom;
    transform: translateX(-60%) scale(4);
    bottom: 100px;
  }
`;

const Description = styled.div`
  padding-top: 150px;
  display: flex;
  flex-direction: column;
  padding-left: 20%;

  p {
    color: ${({ theme }) => theme.colors.WHITE};
    z-index: 1;
    font-size: 25px;
    padding-top: 15px;
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
    color: #008dff;
    animation: ${Flow} 4s ease-in-out infinite;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  z-index: 1;
  position: absolute;
  bottom: 20%;
  left: 20%;
  gap: 20px;
  margin-top: 200px;

  button {
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1;
    width: 250px;
    height: 70px;
    border: none;
    border-radius: 12px;
    font-size: 20px;
    font-weight: 500;
    font-family: 'BMJUA';
    cursor: pointer;

    &:hover {
      transition: 0.2s;
    }
  }

  img {
    width: 30px;
    height: 30px;
    margin-right: 15px;
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
