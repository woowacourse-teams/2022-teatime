import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 1080px;
  margin: 50px auto;
`;

const BoardListContainer = styled.div`
  height: calc(100vh - 150px);
  display: flex;
  justify-content: space-between;
`;

export { Layout, BoardListContainer };
