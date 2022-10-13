import styled, { keyframes } from 'styled-components';

const Motion = keyframes`
	0% {
    margin-top: 0px;
  }

	100% {
    margin-top: 20px;
  } 
`;

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

  @media screen and (${({ theme }) => theme.devices.mobileM}) {
    grid-template-columns: repeat(2, 130px);
    gap: 20px;
  }
`;

const ImageWrapper = styled.div`
  display: flex;
  justify-content: space-evenly;

  img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
  }

  span {
    font-size: 30px;
    animation: ${Motion} 0.8s linear 0s infinite alternate;
  }
`;

export { CardListContainer, Layout, ImageWrapper };
