import axios from 'axios';

import { getStorage, setStorage } from '@utils/localStorage';
import { LOCAL_DB } from '@constants/index';
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
      if (error.response.data.message === '유효하지 않은 토큰입니다.') {
        const { config } = error;
        console.log('config', config);

        const originalRequest = config;
        console.log('originalRequest', originalRequest);

        const { data } = await getAccessToken();
        console.log('data', data);

        axios.defaults.headers.common['Authorization'] = `Bearer ${data.accessToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${data.accessToken}`;

        const user = getStorage(LOCAL_DB.USER);
        const newUser = { ...user, token: data.accessToken };
        setStorage(LOCAL_DB.USER, newUser);

        return api(originalRequest);
      }
    }

    return Promise.reject(error);
  }
);

export { api, BASE_URL };
