import { Routes, Route } from 'react-router-dom';
import Home from '@pages/Home';
import Schedule from '@pages/Schedule';
import { ROUTES } from './constants';
import Layout from '@pages/Layout';

const App = () => {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path={ROUTES.HOME} element={<Home />} />
        <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
      </Route>
    </Routes>
  );
};

export default App;
