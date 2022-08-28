import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  justify-content: center;
  width: 1000px;
  height: calc(100vh - 150px);
  padding: 30px 50px;
  margin: 50px auto;
  box-shadow: 2px 4px 8px rgba(0, 0, 0, 0.25);

  @media all and (${({ theme }) => theme.devices.tablet}) {
    width: 100%;
    height: 100%;
    padding: 20px 10px;
    box-shadow: none;
  }
`;

export { Container };
