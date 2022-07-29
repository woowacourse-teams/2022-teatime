import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 1080px;
  margin: 50px auto;
`;

const Board = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 330px;
  height: calc(100vh - 200px);
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 15px;
`;

export { Layout, Board };
