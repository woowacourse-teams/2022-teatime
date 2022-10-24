const logError = (error: Error) => {
  if (process.env.NODE_ENV === 'development') {
    console.error(error);
  }
};

export { logError };
