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

const TimeBox = styled.div<{ disabled?: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  margin-bottom: 10px;
  border: 1px solid #404338;
  border-radius: 8px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;

  &:hover {
    border: 2px solid #404338;
  }

  ${(props) =>
    props.disabled &&
    css`
      background-color: #e2e1e1;
      color: #a0a0a0;
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
    color: #fff;
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }

  button {
    width: 120px;
    height: 50px;
    background-color: #0169ff;
    color: #fff;
    font-size: 17px;
    font-weight: bold;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
`;

export { TimeListContainer, TimeBox, ReserveButtonWrapper };
