import styled from 'styled-components';

const Container = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  max-width: 1000px;
  height: calc(100vh - 150px);
  padding: 30px 50px;
  margin: 50px auto;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.WHITE};

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 100%;
    height: 100%;
    padding: 20px 10px;
    box-shadow: none;
    flex-direction: column;
    margin: 10px auto;
  }
`;

export { Container };
