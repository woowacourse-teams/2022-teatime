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

  tr > :nth-child(1) {
    span {
      padding: 4px 10px;
      font-size: 16px;
      border-radius: 4px;
      background-color: ${({ theme }) => theme.colors.ORANGE_100};
      color: ${({ theme }) => theme.colors.ORANGE_600};
    }
  }

  tr :nth-child(2) {
    div {
      display: flex;
      justify-content: center;
      align-items: center;
    }

    img {
      width: 65px;
      height: 65px;
      margin-right: 18px;
      border-radius: 50%;
      border: 3px solid white;
      box-shadow: 0 0 16px rgb(215, 215, 215);
    }
  }

  tr :nth-child(5) {
    img {
      width: 24px;
      height: 24px;
      margin-left: 14px;
      cursor: pointer;
      :hover {
        transform: scale(1.1);
      }
    }
  }
`;

export { Table, Thead, Tbody };
