import { Routes, Route } from 'react-router-dom';
import Home from '@pages/Home';
import Schedule from '@pages/Schedule';
import { ROUTES } from './constants';

const App = () => {
  return (
    <Routes>
      <Route path={ROUTES.HOME} element={<Home />} />
      <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
    </Routes>
  );
};

export default App;
