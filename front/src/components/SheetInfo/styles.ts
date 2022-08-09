import styled from 'styled-components';

const InfoContainer = styled.div`
  width: 30%;
  margin-right: 50px;
  border-right: 1px solid ${({ theme }) => theme.colors.GRAY_200};
  font-weight: bold;

  > img {
    width: 50px;
    height: 50px;
    border-radius: 8px;
    margin-bottom: 6px;
  }

  > p {
    font-size: 20px;
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
    color: ${({ theme }) => theme.colors.GRAY_800};
  }
`;

const SheetContainer = styled.div`
  width: 100%;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;

  button {
    margin: 0 20px;
    padding: 10px 20px;
    border-radius: 20px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
  }
`;

const FirstButton = styled.button`
  background-color: ${({ theme }) => theme.colors.WHITE};
  border: 1px solid ${({ theme }) => theme.colors.BLUE_600};
  color: ${({ theme }) => theme.colors.BLUE_600};
`;

const SecondButton = styled.button`
  background-color: ${({ theme }) => theme.colors.BLUE_600};
  color: ${({ theme }) => theme.colors.WHITE};
  border: none;
`;

export { InfoContainer, DateWrapper, SheetContainer, ButtonContainer, FirstButton, SecondButton };
