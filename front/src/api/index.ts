import axios from 'axios';

export const BASE_URL = 'https://api.teatime.pe.kr';

const api = axios.create({
  baseURL: BASE_URL,
});

export default api;
