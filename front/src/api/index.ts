import axios from 'axios';

import { getStorage } from '@utils/localStorage';
import { LOCAL_DB } from '@constants/index';

const BASE_URL = process.env.BACK_URL;

const api = axios.create({
  baseURL: BASE_URL,
});

axios.defaults.withCredentials = true;

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

export { api, BASE_URL };
