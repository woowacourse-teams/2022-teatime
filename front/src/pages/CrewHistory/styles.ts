import styled from 'styled-components';

const Table = styled.table`
  width: 63%;
  border-collapse: collapse;
  margin: 50px auto;

  caption {
    text-align: start;
    padding: 8px;
    font-size: 20px;
    font-weight: bold;
  }
`;

const Thead = styled.thead`
  text-align: center;
  background-color: ${({ theme }) => theme.colors.GRAY_200};

  td {
    padding: 14px 0;
    font-size: 18px;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.GRAY_600};
  }
`;

const Tbody = styled.tbody`
  tr {
    border-bottom: 1px solid ${({ theme }) => theme.colors.GRAY_300};
  }

  td {
    padding: 18px 0;
    text-align: center;
    color: ${({ theme }) => theme.colors.BLUE_700};
    font-size: 18px;
    font-weight: bold;
  }
`;

export { Table, Thead, Tbody };
