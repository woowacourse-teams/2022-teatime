import styled from 'styled-components';

const BoardContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 330px;
  height: 100%;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 10px;
`;

const TitleContainer = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  border-bottom: 3px solid #ffa500;
  padding-bottom: 15px;
  margin-bottom: 15px;

  div {
    width: 8px;
    height: 8px;
    background-color: #ffa500;
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
  overflow: scroll;
  padding: 0 10px;
`;

export { BoardContainer, TitleContainer, ScrollContainer };
