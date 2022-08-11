import { Routes, Route } from 'react-router-dom';

import Crew from '@pages/Crew';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/SelectUser';
import Schedule from '@pages/Schedule';
import Coach from '@pages/Coach';
import NotFound from '@pages/NotFound';
import CrewSheet from '@pages/CrewSheet';
import CoachSheet from '@pages/CoachSheet';
import CrewHistory from '@pages/CrewHistory';
import Header from '@components/Header';
import ScheduleProvider from '@context/ScheduleProvider';
import { ROUTES } from './constants';

const App = () => {
  return (
    <ScheduleProvider>
      <Header />
      <Routes>
        <Route path={ROUTES.HOME} element={<SelectUser />} />
        <Route path={ROUTES.CREW} element={<Crew />} />
        <Route path={`${ROUTES.COACH}/:id`} element={<Coach />} />
        <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
        <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
        <Route path={`${ROUTES.CREW_SHEET}/:id`} element={<CrewSheet />} />
        <Route path={`${ROUTES.COACH_SHEET}/:id`} element={<CoachSheet />} />
        <Route path={`${ROUTES.HISTORY}/:id`} element={<CrewHistory />} />
        <Route path="/*" element={<NotFound />} />
      </Routes>
    </ScheduleProvider>
  );
};

export default App;
