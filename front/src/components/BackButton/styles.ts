import styled from 'styled-components';

const ArrowIcon = styled.img`
  position: absolute;
  left: 20px;
  bottom: 20px;
  width: 45px;
  height: 45px;
  padding: 10px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.BLUE_800};
  cursor: pointer;

  :hover {
    opacity: 0.7;
    transform: scale(1.1);
    transition: ease-in-out 0.2s;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: none;
  }
`;

export { ArrowIcon };
