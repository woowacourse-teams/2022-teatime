import { api } from '@api/index';
import { Question, Questions } from '@typings/domain';

const getQuestions = () => api.get<Questions>(`/api/v2/questions`);

const editQuestions = (data: Question[]) => api.put(`/api/v2/questions`, data);

export { getQuestions, editQuestions };
