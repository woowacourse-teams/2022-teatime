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

const EditButton = styled.button`
  width: 100%;
  height: 40px;
  border-radius: 18px;
  margin-right: 10px;
  background-color: ${({ theme }) => theme.colors.GREEN_700};
  color: ${({ theme }) => theme.colors.WHITE};
  border: none;
  font-size: 16px;
  cursor: pointer;

  :hover {
    opacity: 0.7;
  }
`;

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 330px;
  margin: 30px 0;

  div {
    display: flex;
    justify-content: space-between;
    margin-bottom: 8px;
    padding: 0 2px;
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

export { Container, EditButton, InputWrapper };
