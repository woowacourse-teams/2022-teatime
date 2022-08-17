import styled from 'styled-components';

const Login = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
`;

const SlackLoginButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 350px;
  height: 80px;
  border: none;
  border-radius: 12px;
  font-size: 35px;
  font-weight: bold;
  background-color: #611f69;
  color: white;
  cursor: pointer;

  img {
    width: 40px;
    height: 40px;
    margin-right: 15px;
  }
`;

export { Login, SlackLoginButton };
