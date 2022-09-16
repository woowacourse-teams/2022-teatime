import styled from 'styled-components';

const Table = styled.table`
  width: 55%;
  border-collapse: collapse;
  margin: 50px auto;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
`;

const TheadRow = styled.tr`
  display: grid;
  grid-template-columns: 1fr 2fr repeat(3, 1fr);
  padding: 14px 0;
  background-color: ${({ theme }) => theme.colors.GRAY_200};
  color: ${({ theme }) => theme.colors.GRAY_600};
`;

export { Table, TheadRow };
