const getStorage = (key: string) => {
  const storedData = localStorage.getItem(key);

  return storedData ? JSON.parse(storedData) : null;
};

const setStorage = <T>(key: string, data: T): void => {
  localStorage.setItem(key, JSON.stringify(data));
};

const clearStorage = (key: string): void => {
  localStorage.removeItem(key);
};

export { getStorage, setStorage, clearStorage };
