import { setupWorker } from 'msw';
import handlers from './handlers/handlers';

const worker = setupWorker(...handlers);

export default worker;
