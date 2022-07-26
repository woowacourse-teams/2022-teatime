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

const TimeBox = styled.div<{ isPossible?: boolean; selected: boolean }>`
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

  ${(props) =>
    props.isPossible === false &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_200};
      color: ${({ theme }) => theme.colors.GRAY_500};
      cursor: default;
      text-decoration: line-through;
      pointer-events: none;
    `}

  ${(props) =>
    props.selected &&
    css`
      background-color: ${({ theme }) => theme.colors.GREEN_900};
      color: ${({ theme }) => theme.colors.WHITE};
    `}

  &:hover {
    border: 2px solid ${({ theme }) => theme.colors.GREEN_900};
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
  background-color: ${({ theme }) => theme.colors.BLUE_600};
  color: ${({ theme }) => theme.colors.WHITE};
`;

export { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton };
