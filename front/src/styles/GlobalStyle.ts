import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
    * {  
      margin: 0;
      box-sizing: border-box;
    } 

    a {
      text-decoration: none;
    } 

    body{
      font-family: 'BMJUA';
      max-width: 100%;
      overflow-x: hidden;
    }
`;

export default GlobalStyle;
