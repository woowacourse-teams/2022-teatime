import styled, { css } from 'styled-components';

const CardContainer = styled.div<{ isPossible: boolean; isPreview: boolean }>`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 320px;
  padding: 35px 16px;
  border-radius: 20px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  border: 2px solid ${({ theme }) => theme.colors.GRAY_250};
  overflow: hidden;
  cursor: ${({ isPreview }) => !isPreview && 'pointer'};

  &:hover {
    transform: ${({ isPreview }) => !isPreview && 'scale(1.02)'};
    transition: ease-in-out 0.2s;
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
  }

  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    height: 200px;
    padding: 20px 10px;
  }
`;

const RequestIcon = styled.img`
  position: absolute;
  top: 16px;
  left: 16px;

  :hover {
    transform: scale(1.2);
    opacity: 0.7;
    transition: ease-in-out 0.2s;
  }
`;

const CardWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;

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
    word-break: break-all;
  }

  button {
    width: 90%;
    height: 32px;
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
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
    img {
      width: 50px;
      height: 50px;
    }

    p {
      display: none;
    }

    button {
      height: 25px;
    }
  }
`;

const ImageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  margin-bottom: 10px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
`;

export { CardContainer, RequestIcon, CardWrapper, ImageWrapper, ButtonWrapper };
