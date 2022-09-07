import styled, { css } from 'styled-components';

const BoardContainer = styled.div<{ isDraggingOver: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 330px;
  height: 100%;
  padding: 15px;
  background-color: ${({ theme }) => theme.colors.GRAY_100};
  border-radius: 10px;

  ${(props) =>
    props.isDraggingOver &&
    css`
      background-color: ${({ theme }) => theme.colors.GRAY_150};
    `}
`;

const TitleCircle = styled.div``;

const TitleContainer = styled.div<{ color: string }>`
  display: flex;
  align-items: center;
  width: 100%;
  height: 50px;
  border-bottom: 3px solid ${(props) => props.color};
  padding-bottom: 15px;
  margin-bottom: 15px;

  ${TitleCircle} {
    width: 8px;
    height: 8px;
    background-color: ${(props) => props.color};
    border-radius: 5px;
    margin-right: 10px;
  }

  span {
    font-weight: bold;
  }

  div {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-left: 15px;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    font-weight: bold;
    background-color: ${({ theme }) => theme.colors.GRAY_200};
    color: ${({ theme }) => theme.colors.GRAY_600};
  }
`;

const ScrollContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 0 10px;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
`;

export { BoardContainer, TitleContainer, TitleCircle, ScrollContainer };
