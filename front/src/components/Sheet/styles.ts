import styled, { css } from 'styled-components';

const SheetContainer = styled.div`
  width: 100%;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
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

const ButtonContainer = styled.div<{ isUnalterable: boolean }>`
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

    ${(props) =>
      props.isUnalterable &&
      css`
        background-color: ${({ theme }) => theme.colors.GRAY_200};
        color: ${({ theme }) => theme.colors.GRAY_600};
        border: none;

        :hover {
          opacity: 1;
        }
      `}
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    button {
      padding: 10px;
      font-size: 14px;
    }
  }
`;

export { SheetContainer, ButtonContainer, FirstButton, SecondButton };
