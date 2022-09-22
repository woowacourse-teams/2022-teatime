import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 50px);
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-top: 2px solid ${({ theme }) => theme.colors.GRAY_200};

  h1 {
    font-size: 60px;
    color: ${({ theme }) => theme.colors.BLUE_700};
    text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    margin-bottom: 10px;
  }

  p {
    font-size: 20px;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.BLUE_700};
  }

  img {
    margin: 30px 0px;
    width: 200px;
    height: 200px;
  }
`;

const HomeLink = styled(Link)`
  color: ${({ theme }) => theme.colors.GREEN_700};
  border: 2px solid ${({ theme }) => theme.colors.GREEN_700};
  padding: 10px 30px;
  border-radius: 10px;
  font-weight: bold;

  &:hover {
    background-color: ${({ theme }) => theme.colors.GREEN_700};
    color: ${({ theme }) => theme.colors.WHITE};
    transition: 0.2s;
  }
`;

export { Layout, HomeLink };
