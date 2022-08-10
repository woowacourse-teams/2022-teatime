import styled from 'styled-components';

const InfoContainer = styled.div`
  position: relative;
  width: 30%;
  margin-right: 50px;
  border-right: 1px solid ${({ theme }) => theme.colors.GRAY_200};
  font-weight: bold;

  > p {
    font-size: 20px;
  }
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
    :hover {
      opacity: 0.7;
    }
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

export { InfoContainer, SheetContainer, ButtonContainer, FirstButton, SecondButton, ArrowIcon };
