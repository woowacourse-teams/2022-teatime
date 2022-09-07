import styled from 'styled-components';

const Name = styled.p`
  font-size: 20px;
`;

const Image = styled.img`
  width: 50px;
  height: 50px;
  margin-bottom: 6px;
  border-radius: 50%;
  border: 2px solid white;
  box-shadow: 3px 2px 3px rgb(190, 190, 190);
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

export { Name, Image, DateWrapper };
