import styled from 'styled-components';

const SheetContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const CompleteButtonWrapper = styled.div`
  button {
    width: 100%;
    height: 50px;
    border-radius: 10px;
    border: none;
    background-color: ${({ theme }) => theme.colors.RED_400};
    font-size: 20px;
    color: ${({ theme }) => theme.colors.WHITE};
    cursor: pointer;
    margin-top: 10px;
    bottom: 20px;

    :hover {
      opacity: 0.7;
      transition: ease-in-out 0.2s;
    }
  }
`;

export { CompleteButtonWrapper, SheetContainer };
