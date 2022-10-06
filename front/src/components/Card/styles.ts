import styled, { css } from 'styled-components';

const CardContainer = styled.div<{ isPossible: boolean; isPreview: boolean }>`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 350px;
  padding: 35px 16px;
  border-radius: 20px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  border: 2px solid ${({ theme }) => theme.colors.GRAY_250};
  overflow: hidden;
  cursor: ${({ isPreview }) => !isPreview && 'pointer'};

  div {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 100%;
  }

  img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }

  span {
    font-size: 20px;
    font-weight: 600;
  }

  p {
    text-align: center;
    font-size: 15px;
    margin: 10px 0;
  }

  button {
    width: 67%;
    height: 35px;
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    border: none;
    border-radius: 20px;
    font-size: 18px;
    font-weight: bolder;
    cursor: ${({ isPreview }) => !isPreview && 'pointer'};
  }

  &:hover {
    transform: ${({ isPreview }) => !isPreview && 'scale(1.03)'};
  }

  ${(props) =>
    props.isPossible &&
    css`
      ::before {
        content: '';
        position: absolute;
        top: 15px;
        right: 15px;
        width: 14px;
        height: 14px;
        border-radius: 50%;
        background-color: ${({ theme }) => theme.colors.GREEN_400};
      }
    `}

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    height: 250px;
    padding: 20px 10px;

    span {
      font-size: 16px;
    }

    img {
      width: 70px;
      height: 70px;
    }

    p {
      font-size: 12px;
    }

    button {
      font-size: 14px;
    }
  }

  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    height: 200px;
    padding: 20px 10px;

    img {
      width: 50px;
      height: 50px;
    }

    p {
      display: none;
    }
  }
`;

const ImageWrapper = styled.div`
  margin-bottom: 10px;
`;

const ButtonWrapper = styled.div`
  justify-content: flex-end;
`;

export { CardContainer, ImageWrapper, ButtonWrapper };
