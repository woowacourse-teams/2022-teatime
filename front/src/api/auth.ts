import { api } from '@api/index';
import type { AccessToken } from '@typings/domain';

const login = (code: string) => api.post('/api/auth/login/v2', { code });

const getAccessToken = () => api.get<AccessToken>('/api/auth/refresh-token');

const logout = () => api.delete('/api/auth/logout');

export { login, getAccessToken, logout };
