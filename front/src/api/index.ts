import axios from 'axios';

export const BASE_URL = process.env.BACK_URL;

const api = axios.create({
  baseURL: BASE_URL,
});

export default api;
