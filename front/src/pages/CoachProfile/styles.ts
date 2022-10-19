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
    margin-bottom: 20px;
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

const CardWrapper = styled.div`
  width: 250px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-top: 40px;

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

const ToggleWrapper = styled.div<{ isPokable: boolean }>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  span {
    font-size: 18px;
    color: ${({ theme }) => theme.colors.GREEN_900};
  }

  button {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50px;
    height: 25px;
    border-radius: 20px;
    border: none;
    background-color: ${({ isPokable, theme }) =>
      isPokable ? theme.colors.BLUE_600 : theme.colors.GRAY_500};
    transition: all 0.3s ease-out;
    cursor: pointer;
  }

  div {
    position: absolute;
    left: 4px;
    width: 18px;
    height: 18px;
    border-radius: 50px;
    background-color: white;
    transition: all 0.3s ease-out;
    ${({ isPokable }) =>
      isPokable &&
      css`
        transform: translate(25px, 0);
      `}
  }
`;

export {
  Container,
  ProfileForm,
  Grid,
  BorderBox,
  PreviewBorderBox,
  BorderBoxName,
  LabelContainer,
  CardWrapper,
  ButtonWrapper,
  ToggleWrapper,
};
