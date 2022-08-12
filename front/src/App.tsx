import { Routes, Route } from 'react-router-dom';

import Crew from '@pages/Crew';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/Home';
import Certification from '@pages/Certification';
import Schedule from '@pages/Schedule';
import Coach from '@pages/Coach';
import NotFound from '@pages/NotFound';
import Sheet from '@pages/Sheet';
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
          <Route path={`${ROUTES.COACH}/:id`} element={<Coach />} />
          <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
          <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
          <Route path={`${ROUTES.SHEET}/:id`} element={<Sheet />} />
          <Route path={`${ROUTES.HISTORY}/:id`} element={<CrewHistory />} />
          <Route path="/*" element={<NotFound />} />
        </Routes>
      </ScheduleProvider>
    </SnackbarProvider>
  );
};

export default App;
