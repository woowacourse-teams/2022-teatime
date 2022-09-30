import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 50px auto;
`;

const CardListContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 250px);
  gap: 40px;

  @media screen and (${({ theme }) => theme.devices.laptopM}) {
    width: 100%;
    justify-content: center;
    grid-template-columns: repeat(auto-fill, 250px);
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    grid-template-columns: repeat(2, 220px);
  }

  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    grid-template-columns: repeat(2, 150px);
  }
`;

export { CardListContainer, Layout };
