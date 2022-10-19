import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 70vh;
  margin: auto;

  label {
    text-align: left;
    font-size: 16px;
    color: ${({ theme }) => theme.colors.GREEN_900};
  }

  input {
    width: 100%;
    height: 40px;
    margin: 6px 0 20px 0;
    padding: 8px;
    border: 2px solid ${({ theme }) => theme.colors.GRAY_250};
    border-radius: 8px;
    font-size: 16px;
  }

  button {
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    width: 100%;
    height: 40px;
    border: none;
    border-radius: 10px;
    font-size: 18px;
    cursor: pointer;

    :hover {
      opacity: 0.7;
    }
  }
`;

export { Container };
