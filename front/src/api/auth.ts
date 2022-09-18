import { api } from '@api/index';

const login = (code: string) => api.post('/api/auth/login', { code });

export { login };
