import { Routes, Route } from 'react-router-dom';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';

import Crew from '@pages/Crew';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/SelectUser';
import Schedule from '@pages/Schedule';
import Coach from '@pages/Coach';
import NotFound from '@pages/NotFound';
import AddSheet from '@pages/AddSheet';
import ViewSheet from '@pages/ViewSheet';
import CrewHistory from '@pages/CrewHistory/index';
import Header from '@components/Header';
import ScheduleProvider from '@context/ScheduleProvider';
import { ROUTES } from './constants';

dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.tz.setDefault('Asia/Seoul');

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
        <Route path={`${ROUTES.ADD_SHEET}/:id`} element={<AddSheet />} />
        <Route path={`${ROUTES.VIEW_SHEET}/:id`} element={<ViewSheet />} />
        <Route path={`${ROUTES.HISTORY}/:id`} element={<CrewHistory />} />
        <Route path="/*" element={<NotFound />} />
      </Routes>
    </ScheduleProvider>
  );
};

export default App;
