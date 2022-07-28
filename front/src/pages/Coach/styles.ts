import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 1000px;
  margin: 50px auto;
`;

const Board = styled.div`
  width: 300px;
  height: calc(100vh - 200px);
  background-color: #f5f5f5;
`;

export { Layout, Board };
