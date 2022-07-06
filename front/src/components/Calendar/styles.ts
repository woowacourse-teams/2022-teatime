import styled from 'styled-components';

const DateContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100px;
  height: 80px;
  margin: -1px;
  padding: 10px;
  border: 2px solid #e2e2e2;
`;

const DateGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  width: 600px;
`;

export { DateContainer, DateGrid };
