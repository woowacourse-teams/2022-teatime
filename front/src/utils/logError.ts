import * as Sentry from '@sentry/react';

const logError = (error: Error) => {
  if (process.env.NODE_ENV === 'development') {
    console.error(error);
    return;
  }

  Sentry.captureException(error);
};

export { logError };
