import styled, { keyframes } from 'styled-components';

const FadeIn = keyframes`
 from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const SnackBarWrapper = styled.div<{ type?: string }>`
  position: fixed;
  min-width: 300px;
  background-color: ${({ theme }) => theme.colors.BLUE_900};
  color: ${({ theme }) => theme.colors.WHITE};
  text-align: center;
  border-radius: 5px;
  padding: 16px;
  z-index: 1;
  left: 50%;
  transform: translate(-50%);
  bottom: 30px;
  animation: ${FadeIn} 0.7s;
`;

export { SnackBarWrapper };
