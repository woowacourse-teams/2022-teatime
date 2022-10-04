import styled from 'styled-components';

const Container = styled.div`
  width: 55%;
  margin: 0 auto;
  margin-bottom: 20px;

  @media screen and (${({ theme }) => theme.devices.laptop}) {
    width: 70%;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 95%;
  }
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  text-align: center;
  font-size: 18px;
  font-weight: bold;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    font-size: 15px;
  }
`;

const TheadRow = styled.tr`
  display: grid;
  grid-template-columns: 1fr 2fr repeat(3, 1fr);
  padding: 14px 0;
  background-color: ${({ theme }) => theme.colors.GRAY_200};
  color: ${({ theme }) => theme.colors.GRAY_600};

  @media screen and (${({ theme }) => theme.devices.mobileM}) {
    font-size: 12px;
  }
`;

export { Container, Table, TheadRow };
