import axios from 'axios';

export const BASE_URL = 'http://43.200.41.255:8080';

const api = axios.create({
  baseURL: BASE_URL,
});

export default api;
