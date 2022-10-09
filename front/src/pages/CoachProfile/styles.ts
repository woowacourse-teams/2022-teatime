import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;

  form {
    margin-right: 60px;
  }
`;

const ProfileTitle = styled.h1`
  font-size: 22px;
`;

const CardWrapper = styled.div`
  width: 250px;
`;

const PreviewTitle = styled.p`
  margin-bottom: 4px;
  padding-left: 10px;
  color: ${({ theme }) => theme.colors.GRAY_600};
`;

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 420px;
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

const EditButton = styled.button`
  width: 100%;
  height: 42px;
  border-radius: 18px;
  background-color: ${({ theme }) => theme.colors.GREEN_700};
  color: ${({ theme }) => theme.colors.WHITE};
  border: none;
  font-size: 18px;
  cursor: pointer;

  :hover {
    opacity: 0.7;
  }
`;

export { Container, ProfileTitle, CardWrapper, PreviewTitle, EditButton, InputWrapper };
