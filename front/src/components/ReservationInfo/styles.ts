import styled from 'styled-components';

const Container = styled.div`
  display: flex;
`;

const Name = styled.p`
  font-size: 20px;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    font-size: 16px;
  }
`;

const Image = styled.img`
  width: 50px;
  height: 50px;
  margin-bottom: 6px;
  border-radius: 50%;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 30px;
    height: 30px;
    margin-bottom: 0;
  }
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

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    margin-top: 0;

    img {
      width: 14px;
      height: 14px;
    }

    span {
      font-size: 14px;
    }
  }
`;

export { Container, Name, Image, DateWrapper };
