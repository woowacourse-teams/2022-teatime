import styled from 'styled-components';

const Background = styled.div`
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  left: 0;
  top: 0;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.5);
`;

const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 400px;
  border-radius: 12px;
  background-color: white;
  overflow: hidden;

  p {
    margin: 20px 0;
    font-size: 18px;
    font-weight: bold;
  }
`;

const TitleWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  img {
    margin: 0 0 3px 10px;
    width: 30px;
    height: 30px;
  }

  h1 {
    font-size: 26px;
    color: #3d5a80;
  }
`;

const InnerContainer = styled.div`
  padding: 20px;
`;

const CloseIconWrapper = styled.div`
  display: flex;
  justify-content: flex-end;

  img {
    cursor: pointer;
  }
`;

const ButtonWrapper = styled.div`
  button {
    border: none;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }
`;

const FirstButton = styled.button`
  width: 50%;
  height: 45px;
  background-color: #e9e9e9;
  &:hover {
    background-color: #d3d3d3;
  }
`;

const SecondButton = styled.button`
  width: 50%;
  height: 45px;
  background-color: #cce6ba;
  &:hover {
    background-color: #b8e09b;
  }
`;

export {
  Background,
  CloseIconWrapper,
  ModalContainer,
  FirstButton,
  SecondButton,
  ButtonWrapper,
  InnerContainer,
  TitleWrapper,
};
