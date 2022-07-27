import styled, { css, keyframes } from 'styled-components';

const FadeIn = keyframes`
 from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const TimeListContainer = styled.div`
  width: 250px;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
`;

const TimeBox = styled.div<{ isPossible?: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  margin-bottom: 10px;
  border: 1px solid ${({ theme }) => theme.colors.GREEN_900};
  border-radius: 4px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;

  &:hover {
    border: 2px solid ${({ theme }) => theme.colors.GREEN_900};
  }

  ${(props) =>
    props.isPossible === false &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_200};
      color: ${({ theme }) => theme.colors.GRAY_500};
      cursor: default;
      text-decoration: line-through;
      pointer-events: none;
    `}
`;

const ReserveButtonWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  animation: ${FadeIn} 0.8s;

  div {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 120px;
    height: 50px;
    background-color: rgba(0, 0, 0, 0.6);
    color: ${({ theme }) => theme.colors.WHITE};
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }

  button {
    width: 120px;
    height: 50px;
    background-color: ${({ theme }) => theme.colors.BLUE_600};
    color: ${({ theme }) => theme.colors.WHITE};
    font-size: 17px;
    font-weight: bold;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
`;

export { TimeListContainer, TimeBox, ReserveButtonWrapper };
