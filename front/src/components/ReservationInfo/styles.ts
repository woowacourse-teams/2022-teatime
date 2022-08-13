import styled from 'styled-components';

const Name = styled.p`
  font-size: 20px;
`;

const ArrowIcon = styled.img`
  position: absolute;
  left: -1em;
  bottom: 1em;
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

const CoachImg = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 8px;
  margin-bottom: 6px;
  border: 2px solid white;
  box-shadow: 0 0 10px rgb(190, 190, 190);
`;

const DateWrapper = styled.div`
  margin-top: 8px;

  img {
    width: 18px;
    height: 18px;
    margin-right: 5px;
  }

  span {
    vertical-align: top;
    font-size: 18px;
    color: ${({ theme }) => theme.colors.BLUE_700};
  }
`;

export { Name, ArrowIcon, CoachImg, DateWrapper };
