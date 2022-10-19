import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
    * {  
      margin: 0;
      box-sizing: border-box;
      font-family: 'BMJUA';
    } 

    html, body {
      overflow-x: hidden;
      max-width: 100%;
      background-color: #f5f5f5;
    }

    a {
      text-decoration: none;
    } 

    button {
      background-color: inherit;
      border: inherit;
    }
`;

export default GlobalStyle;
