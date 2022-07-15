import { Link } from 'react-router-dom';
import styled from 'styled-components';

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 50px;
  padding: 0 10%;
  box-shadow: 0px 3px 4px #ececec;

  h1 {
    font-size: 18px;
  }

  img {
    width: 30px;
    margin-right: 8px;
  }
`;

const LogoLink = styled(Link)`
  display: flex;
  color: black;
  align-items: center;
  flex-direction: row;
  text-decoration: none;
  cursor: pointer;
`;

export { HeaderContainer, LogoLink };
