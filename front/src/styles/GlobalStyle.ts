import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
    * {  
      margin: 0;
      box-sizing: border-box;
      font-family: 'BMJUA';
    } 

    a {
      text-decoration: none;
    } 

    body {
      max-width: 100%;
      overflow-x: hidden;
    }
 
`;

export default GlobalStyle;
