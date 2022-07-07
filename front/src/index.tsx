import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import worker from './mocks/browser';
import App from './App';
import GlobalStyle from '@styles/GlobalStyle';

const main = async () => {
  if (process.env.NODE_ENV === 'development') {
    if (window.location.pathname === '') {
      window.location.pathname = '/';
      return;
    }

    await worker.start({
      serviceWorker: {
        url: '/mockServiceWorker.js',
      },
    });
  }
};

const root = createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <BrowserRouter>
    <GlobalStyle />
    <App />
  </BrowserRouter>
);

main();
