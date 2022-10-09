import styled, { css } from 'styled-components';

const profileInput = css`
  width: 100%;
  padding: 8px;
  border: 2px solid ${({ theme }) => theme.colors.GRAY_250};
  border-radius: 8px;
  font-size: 16px;
`;

const Container = styled.div`
  display: flex;
  justify-content: center;
  margin: 50px 20px;
`;

const Grid = styled.div`
  display: grid;
  grid-template-columns: 2fr 1fr;
  width: 900px;
  gap: 40px;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    grid-template-columns: 1fr;
  }
`;

const BorderBox = styled.div`
  width: 100%;
  padding: 30px 20px;
  border: 1.5px solid ${({ theme }) => theme.colors.GRAY_200};
`;

const BorderBoxName = styled.p`
  color: ${({ theme }) => theme.colors.GREEN_900};
  margin-bottom: 10px;
  font-size: 18px;
`;

const PreviewBorderBox = styled(BorderBox)`
  @media screen and (${({ theme }) => theme.devices.tablet}) {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    display: none;
  }
`;

const ProfileForm = styled.form`
  input {
    ${profileInput}
    margin-bottom: 30px;
  }

  textarea {
    ${profileInput}
  }
`;

const LabelContainer = styled.div`
  display: flex;
  justify-content: space-between;

  label {
    font-size: 18px;
    color: ${({ theme }) => theme.colors.GREEN_900};
  }
`;

const QuestionContainer = styled.div`
  display: flex;
`;

const QuestionInner = styled.div`
  display: flex;
`;

const QuestionBorderBox = styled(BorderBox)``;

const QuestionNameWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 10px;
  font-size: 18px;
  color: ${({ theme }) => theme.colors.GREEN_900};
`;

const QuestionInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;

  input {
    ${profileInput}
    margin-bottom: 15px;
    height: 40px;
  }
`;

const QuestionCheckBoxContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100px;

  input {
    margin-bottom: 15px;
    width: 40px;
    height: 40px;
    cursor: pointer;
  }
`;

const CardWrapper = styled.div`
  width: 250px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin: 20px 0;

  button {
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    width: 50%;
    height: 42px;
    border: none;
    border-radius: 10px;
    font-size: 18px;
    cursor: pointer;

    :hover {
      opacity: 0.7;
    }
  }
`;

export {
  Container,
  ProfileForm,
  Grid,
  BorderBox,
  PreviewBorderBox,
  QuestionContainer,
  QuestionInner,
  QuestionBorderBox,
  QuestionNameWrapper,
  QuestionInputContainer,
  QuestionCheckBoxContainer,
  BorderBoxName,
  LabelContainer,
  CardWrapper,
  ButtonWrapper,
};
