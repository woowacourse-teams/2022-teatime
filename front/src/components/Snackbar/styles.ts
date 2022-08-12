import styled, { keyframes } from 'styled-components';

const FadeIn = keyframes`
 from {
    transform: translateY(0);
    opacity: 0;
  }
  to {
    transform: translateY(-50px);
    opacity: 1;
  }
`;

const FadeOut = keyframes`
  from {
    transform: translateY(-50px);
    opacity: 1;
  }
  to {
    transform: translateY(0);
    opacity: 0;
  }
`;

const SnackBarWrapper = styled.div<{ type?: string }>`
  min-width: 250px;
  margin-left: -125px;
  background-color: ${({ theme }) => theme.colors.BLUE_900};
  color: ${({ theme }) => theme.colors.WHITE};
  text-align: center;
  border-radius: 5px;
  padding: 16px;
  position: fixed;
  z-index: 1;
  left: 50%;
  bottom: 0;
  animation: ${FadeIn} 0.5s, ${FadeOut} 0.5s 2.5s;
  animation-fill-mode: forwards;
`;

export { SnackBarWrapper };
