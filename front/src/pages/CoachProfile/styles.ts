import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 80vh;

  img {
    width: 100px;
    height: 100px;
    margin-bottom: 10px;
    border-radius: 50%;
  }
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;

  button {
    padding: 10px 20px;
    border-radius: 16px;
    margin-right: 10px;
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

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 330px;
  margin: 30px 0;

  label {
    margin-bottom: 6px;
    color: ${({ theme }) => theme.colors.GRAY_600};
  }

  input {
    padding: 8px;
    height: 40px;
    border: 1px solid ${({ theme }) => theme.colors.GRAY_300};
    border-radius: 4px;
    font-size: 16px;
  }

  textarea {
    padding: 8px;
    border: 1px solid ${({ theme }) => theme.colors.GRAY_300};
    border-radius: 4px;
    font-size: 16px;
  }
`;

export { Container, ButtonWrapper, FirstButton, SecondButton, InputWrapper };
