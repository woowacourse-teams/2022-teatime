import { Routes, Route } from 'react-router-dom';
import Home from '@pages/Home';
import Schedule from '@pages/Schedule';
import { ROUTES } from './constants';
import Header from '@components/Header';
import ScheduleProvider from '@context/ScheduleProvider';

const App = () => {
  return (
    <ScheduleProvider>
      <Header />
      <Routes>
        <Route path={ROUTES.HOME} element={<Home />} />
        <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
      </Routes>
    </ScheduleProvider>
  );
};

export default App;
