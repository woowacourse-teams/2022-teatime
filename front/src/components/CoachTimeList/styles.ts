import styled, { css } from 'styled-components';

const TimeListContainer = styled.div`
  width: 250px;
  height: 370px;
  overflow: scroll;
  margin-bottom: 20px;
  ::-webkit-scrollbar {
    display: none;
  }
`;

const TimeBox = styled.div<{ disabled?: boolean; selected: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  margin-bottom: 10px;
  border: 1px solid #404338;
  border-radius: 4px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;

  ${(props) =>
    props.disabled === false &&
    css`
      background-color: #e2e1e1;
      color: #a0a0a0;
      cursor: default;
      text-decoration: line-through;
      pointer-events: none;
    `}

  ${(props) =>
    props.selected &&
    css`
      background-color: #404338;
      color: #fff;
    `}

  &:hover {
    border: 2px solid #404338;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;

  button {
    width: 120px;
    height: 50px;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }
`;

const CheckButton = styled.button``;

const ConfirmButton = styled.button`
  background-color: #0169ff;
  color: #fff;
`;

export { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton };
