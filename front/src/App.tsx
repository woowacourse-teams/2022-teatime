import { Routes, Route } from 'react-router-dom';

import Crew from '@pages/Crew';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/Home';
import Certification from '@pages/Certification';
import Schedule from '@pages/Schedule';
import Coach from '@pages/Coach';
import NotFound from '@pages/NotFound';
import CrewSheet from '@pages/CrewSheet';
import CoachSheet from '@pages/CoachSheet';
import HistorySheet from '@pages/HistorySheet';
import CrewHistory from '@pages/CrewHistory';
import Header from '@components/Header';
import ScheduleProvider from '@context/ScheduleProvider';
import SnackbarProvider from '@context/SnackbarProvider';
import { ROUTES } from './constants';

const App = () => {
  return (
    <SnackbarProvider>
      <ScheduleProvider>
        <Header />
        <Routes>
          <Route path={ROUTES.HOME} element={<SelectUser />} />
          <Route path={ROUTES.CERTIFICATION} element={<Certification />} />
          <Route path={ROUTES.CREW} element={<Crew />} />
          <Route path={ROUTES.COACH} element={<Coach />} />
          <Route path={ROUTES.SCHEDULE} element={<Schedule />} />
          <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
          <Route path={`${ROUTES.CREW_SHEET}/:id`} element={<CrewSheet />} />
          <Route path={`${ROUTES.COACH_SHEET}/:id`} element={<CoachSheet />} />
          <Route path={`${ROUTES.HISTORY_SHEET}/:id`} element={<HistorySheet />} />
          <Route path={`${ROUTES.CREW_HISTORY}/:id`} element={<CrewHistory />} />
          <Route path="/*" element={<NotFound />} />
        </Routes>
      </ScheduleProvider>
    </SnackbarProvider>
  );
};

export default App;
