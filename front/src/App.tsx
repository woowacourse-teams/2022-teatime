import { Routes, Route } from 'react-router-dom';
import Home from '@pages/Home';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/SelectUser';
import Schedule from '@pages/Schedule';
import Coach from '@pages/Coach';
import Header from '@components/Header';
import { ROUTES } from './constants';
import ScheduleProvider from '@context/ScheduleProvider';
import CoachScheduleProvider from '@context/CoachScheduleProvider';

const App = () => {
  return (
    <ScheduleProvider>
      <CoachScheduleProvider>
        <Header />
        <Routes>
          <Route path={ROUTES.HOME} element={<Home />} />
          <Route path={ROUTES.SELECT_USER} element={<SelectUser />} />
          <Route path={ROUTES.COACH} element={<Coach />} />
          <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
          <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
        </Routes>
      </CoachScheduleProvider>
    </ScheduleProvider>
  );
};

export default App;
