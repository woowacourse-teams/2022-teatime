import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import * as Sentry from '@sentry/react';
import { BrowserTracing } from '@sentry/tracing';

import App from './App';
import GlobalStyle from '@styles/GlobalStyle';
import GlobalFonts from '@styles/GlobalFonts';
import { theme } from '@styles/theme';
import worker from './mocks/browser';

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

Sentry.init({
  dsn: process.env.SENTRY_DSN,
  integrations: [new BrowserTracing()],
  tracesSampleRate: 1.0,
});

const root = createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <GlobalStyle />
      <GlobalFonts />
      <App />
    </BrowserRouter>
  </ThemeProvider>
);

// main();
