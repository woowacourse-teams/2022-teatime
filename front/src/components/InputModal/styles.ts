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

const Container = styled.div`
  height: 200px;
  width: 500px;
  padding: 20px;
  margin-top: 70px;
  background: #fff;
  border-radius: 20px;

  h1 {
    font-size: 24px;
    color: #3d5a80;
    margin: 20px 0;
  }
`;

const FormContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  input {
    padding: 0 10px;
    border: none;
    height: 45px;
    background-color: #f2f2f2;
    border-radius: 8px;
  }
  button {
    margin-left: 10px;
    border: none;
    width: 50px;
    height: 45px;
    border-radius: 8px;
    background-color: #cce6ba;
    cursor: pointer;
  }
`;

const CloseIconWrapper = styled.div`
  display: flex;
  justify-content: flex-end;

  img {
    cursor: pointer;
  }
`;

export { Background, Container, CloseIconWrapper, FormContainer };
