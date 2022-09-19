import { createGlobalStyle } from 'styled-components';

const GlobalFonts = createGlobalStyle`
    @font-face {
    font-family: 'BMJUA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
    font-display: swap;
    }
`;

export default GlobalFonts;
