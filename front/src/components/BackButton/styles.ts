import styled from 'styled-components';

const ArrowIcon = styled.img`
  position: absolute;
  left: -10px;
  bottom: 10px;
  width: 50px;
  height: 50px;
  padding: 10px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.BLUE_800};
  cursor: pointer;

  :hover {
    opacity: 0.7;
    transform: scale(1.1);
    transition: ease-in-out 0.2s;
  }
`;

export { ArrowIcon };
