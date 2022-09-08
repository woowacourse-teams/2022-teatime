import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 1080px;
  margin: 50px auto;
`;

const BoardListHeader = styled.div`
  height: 50px;
  display: flex;
  justify-content: flex-end;
  height: 50px;
`;

const AddScheduleButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  width: 120px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  background-color: ${({ theme }) => theme.colors.BLUE_600};
  color: ${({ theme }) => theme.colors.WHITE};
  font-size: 16px;
  font-weight: bold;

  img {
    margin-right: 5px;
  }

  &:hover {
    opacity: 0.8;
    transition: ease-in-out 0.2s;
  }
`;

const BoardListContainer = styled.div`
  height: calc(100vh - 200px);
  display: flex;
  justify-content: space-between;
`;

export { Layout, BoardListHeader, AddScheduleButton, BoardListContainer };
