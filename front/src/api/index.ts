import axios from 'axios';

import { clearStorage, getStorage, setStorage } from '@utils/localStorage';
import { ERROR_MESSAGE, LOCAL_DB } from '@constants/index';
import { getAccessToken } from './auth';

const BASE_URL = process.env.BACK_URL;

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    const token = getStorage(LOCAL_DB.USER)?.token;

    if (typeof token !== 'string' || !token) {
      return config;
    }

    config.headers = {
      Authorization: `Bearer ${token}`,
    };
    return config;
  },

  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response.status === 401) {
      if (error.response.data.message === ERROR_MESSAGE.INVALID_TOKEN) {
        const { config } = error;
        const originalRequest = { ...config };

        const {
          data: { accessToken },
        } = await getAccessToken();

        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;

        const user = getStorage(LOCAL_DB.USER);
        const newUser = { ...user, token: accessToken };
        setStorage(LOCAL_DB.USER, newUser);

        return api(originalRequest);
      }
    }

    if (error.response.status === 400) {
      if (
        error.response.data.message === ERROR_MESSAGE.WRONG_TOKEN ||
        error.response.data.message === ERROR_MESSAGE.NOT_EXIST_TOKEN
      ) {
        clearStorage(LOCAL_DB.USER);
        location.href = `${process.env.REDIRECT_URL}`;
        return;
      }
    }

    return Promise.reject(error);
  }
);

export { api, BASE_URL };
