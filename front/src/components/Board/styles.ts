import styled from 'styled-components';

const BoardContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 330px;
  height: 100%;
  padding: 15px;
  background-color: ${({ theme }) => theme.colors.GRAY_100};
  border-radius: 10px;
`;

const TitleContainer = styled.div<{ color: string }>`
  display: flex;
  align-items: center;
  width: 100%;
  border-bottom: 3px solid ${(props) => props.color};
  padding-bottom: 15px;
  margin-bottom: 15px;

  div {
    width: 8px;
    height: 8px;
    background-color: ${(props) => props.color};
    border-radius: 5px;
    margin-right: 10px;
  }

  span {
    font-weight: bold;
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

export { BoardContainer, TitleContainer, ScrollContainer };
