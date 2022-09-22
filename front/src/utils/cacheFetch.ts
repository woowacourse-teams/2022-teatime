const cacheStorage: Record<string, unknown> = {};

export const cacheFetch = async <T>(
  key: string,
  apiRequestCallback: () => Promise<T>,
  cacheTime: number
): Promise<T> => {
  if (cacheStorage[key]) {
    return cacheStorage[key] as T;
  }

  const response = await apiRequestCallback();
  cacheStorage[key] = response;

  setTimeout(() => {
    delete cacheStorage[key];
  }, cacheTime);

  return response;
};
