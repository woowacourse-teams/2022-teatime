import { Routes, Route } from 'react-router-dom';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';

import Crew from '@pages/Crew';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/SelectUser';
import Schedule from '@pages/Schedule';
import Coach from '@pages/Coach';
import AddSheet from '@pages/AddSheet/index';
import CrewHistory from '@pages/CrewHistory/index';
import Header from '@components/Header';
import ScheduleProvider from '@context/ScheduleProvider';
import { ROUTES } from '@constants/index';

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
        <Route path={ROUTES.COACH} element={<Coach />} />
        <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
        <Route path={`${ROUTES.SCHEDULE}/:id`} element={<Schedule />} />
        <Route path={`${ROUTES.FORM}/:id`} element={<AddSheet />} />
        <Route path={`${ROUTES.CREW_HISTORY}/:id`} element={<CrewHistory />} />
      </Routes>
    </ScheduleProvider>
  );
};

export default App;
