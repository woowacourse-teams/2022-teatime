import axios from 'axios';

const api = axios.create({
  baseURL: 'http://3.38.213.179:8080',
});

export default api;
