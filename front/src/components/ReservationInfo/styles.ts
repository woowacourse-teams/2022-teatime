import styled from 'styled-components';

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

export { CoachImg, DateWrapper };
