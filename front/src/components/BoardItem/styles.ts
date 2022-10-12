import styled, { css } from 'styled-components';

const BoardItemContainer = styled.div<{ color: string; draggedColor: string; isDragging: boolean }>`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 170px;
  margin: 10px 0px;
  padding: 15px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-left: 6px solid ${(props) => props.color};
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
  cursor: grab;

  &:active {
    cursor: grabbing;
  }

  ${(props) =>
    props.isDragging &&
    css`
      background-color: ${props.draggedColor};
      border: 2px dashed ${props.color};
      opacity: 0.5;
    `}
`;

const TopSection = styled.div`
  display: flex;
  justify-content: space-between;
`;

const DateContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: calc(100% - 35px);

  div {
    margin-bottom: 6px;
    overflow-x: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  img {
    vertical-align: top;
    margin-right: 5px;
  }

  span {
    color: ${({ theme }) => theme.colors.BLUE_700};
    font-weight: 600;
    font-size: 15px;
    letter-spacing: 1px;
  }
`;

const ProfileImage = styled.img`
  width: 35px;
  height: 35px;
  border-radius: 50%;
`;

const ButtonWrapper = styled.div`
  flex: 1;
  display: flex;
  justify-content: space-evenly;
  align-items: flex-end;

  button {
    width: 90px;
    height: 25px;
    border-radius: 6px;
    font-weight: bold;
    cursor: pointer;

    &:hover {
      opacity: 0.5;
      transition: ease-in-out 0.2s;
    }
  }
`;

const FirstButton = styled.button<{ color: string }>`
  background-color: ${({ theme }) => theme.colors.WHITE};
  color: ${(props) => props.color};
  border: 1px solid ${(props) => props.color};
`;

const SecondButton = styled.button<{ color: string; isButtonDisabled?: boolean }>`
  background-color: ${(props) => props.color};
  color: ${({ theme }) => theme.colors.WHITE};
  border: none;

  ${(props) =>
    props.isButtonDisabled === true &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_200};
      color: ${({ theme }) => theme.colors.GRAY_500};
      cursor: default;
      pointer-events: none;
    `}
`;

export {
  BoardItemContainer,
  TopSection,
  DateContainer,
  ProfileImage,
  ButtonWrapper,
  FirstButton,
  SecondButton,
};
