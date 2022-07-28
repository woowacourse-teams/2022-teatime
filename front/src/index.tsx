import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import worker from './mocks/browser';
import App from './App';
import GlobalStyle from '@styles/GlobalStyle';
import theme from '@styles/theme';

// const main = async () => {
//   if (process.env.NODE_ENV === 'development') {
//     if (window.location.pathname === '') {
//       window.location.pathname = '/';
//       return;
//     }

//     await worker.start({
//       serviceWorker: {
//         url: '/mockServiceWorker.js',
//       },
//     });
//   }
// };

const root = createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <GlobalStyle />
      <App />
    </BrowserRouter>
  </ThemeProvider>
);

// main();
