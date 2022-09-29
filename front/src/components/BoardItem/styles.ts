import styled, { css } from 'styled-components';

const BoardItemContainer = styled.div<{ color: string; draggedColor: string; isDragging: boolean }>`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 160px;
  margin: 10px 0px;
  padding: 15px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-left: 10px solid ${(props) => props.color};
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

  div {
    margin-bottom: 5px;
  }

  img {
    width: 18px;
    height: 18px;
    vertical-align: top;
    margin-right: 5px;
  }

  span {
    color: ${({ theme }) => theme.colors.BLUE_700};
    font-weight: 600;
    font-size: 18px;
    letter-spacing: 1px;
  }
`;

const CloseIconWrapper = styled.div`
  display: flex;

  img {
    width: 15px;
    height: 15px;
    cursor: pointer;
  }
`;

const ProfileImage = styled.img`
  width: 25px;
  height: 25px;
  border-radius: 15px;
  margin-right: 10px;
`;

const BottomSection = styled.div`
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;

  div {
    display: flex;
    align-items: center;
    width: calc(100% - 100px);
    padding-right: 5px;
    /* cursor: pointer; */

    /* &:hover {
      opacity: 0.5;
      transition: ease-in-out 0.2s;
    } */

    span {
      overflow-x: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
`;

const MenuButton = styled.button<{ color: string; isButtonDisabled?: boolean }>`
  width: 100px;
  height: 25px;
  border: none;
  border-radius: 15px;
  color: ${({ theme }) => theme.colors.WHITE};
  background-color: ${(props) => props.color};
  font-weight: bold;
  cursor: pointer;

  &:hover {
    opacity: 0.5;
    transition: ease-in-out 0.2s;
  }

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
  CloseIconWrapper,
  ProfileImage,
  BottomSection,
  MenuButton,
};
