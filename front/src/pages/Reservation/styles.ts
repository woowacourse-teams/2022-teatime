import styled from 'styled-components';

const CoachImage = styled.img`
  position: absolute;
  top: -20px;
  left: -20px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 3px solid white;
  box-shadow: 0 0 16px rgb(210, 210, 210);

  @media screen and (${({ theme }) => theme.devices.laptop}) {
    left: 0px;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    top: 18px;
    width: 50px;
    height: 50px;
  }
`;

export { CoachImage };
