import styled from 'styled-components';

const InfoContainer = styled.div`
  width: 30%;
  margin-right: 50px;
  border-right: 1px solid ${({ theme }) => theme.colors.GRAY_200};
`;

const InterviewContainer = styled.div`
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

const SaveButton = styled.button`
  background-color: ${({ theme }) => theme.colors.WHITE};
  border: 1px solid ${({ theme }) => theme.colors.BLUE_600};
  color: ${({ theme }) => theme.colors.BLUE_600};
`;

const SubmitButton = styled.button`
  background-color: ${({ theme }) => theme.colors.BLUE_600};
  color: ${({ theme }) => theme.colors.WHITE};
  border: none;
`;

export { InfoContainer, InterviewContainer, ButtonContainer, SaveButton, SubmitButton };