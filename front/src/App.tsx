import { Routes, Route } from 'react-router-dom';

import Crew from '@pages/CrewMain';
import Reservation from '@pages/Reservation';
import Home from '@pages/Home';
import Certification from '@pages/Certification';
import Schedule from '@pages/Schedule';
import Coach from '@pages/CoachMain';
import NotFound from '@pages/NotFound';
import CrewSheet from '@pages/CrewSheet';
import CoachSheet from '@pages/CoachSheet';
import HistorySheet from '@pages/HistorySheet';
import CrewHistory from '@pages/CrewHistory';
import CoachHistory from '@pages/CoachHistory';
import Header from '@components/Header';
import SnackbarProvider from '@context/SnackbarProvider';
import UserProvider from '@context/UserProvider';
import AuthRoute from './AuthRoute';
import { ROUTES } from './constants';

const App = () => {
  return (
    <UserProvider>
      <SnackbarProvider>
        <Header />
        <Routes>
          <Route path={ROUTES.HOME} element={<Home />} />
          <Route path={ROUTES.CERTIFICATION} element={<Certification />} />
          <Route path="/*" element={<NotFound />} />
          <Route element={<AuthRoute role="CREW" />}>
            <Route path={ROUTES.CREW} element={<Crew />} />
            <Route path={ROUTES.CREW_HISTORY} element={<CrewHistory />} />
            <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
            <Route path={`${ROUTES.CREW_SHEET}/:id`} element={<CrewSheet />} />
          </Route>
          <Route element={<AuthRoute role="COACH" />}>
            <Route path={ROUTES.COACH} element={<Coach />} />
            <Route path={ROUTES.SCHEDULE} element={<Schedule />} />
            <Route path={ROUTES.COACH_HISTORY} element={<CoachHistory />} />
            <Route path={`${ROUTES.COACH_SHEET}/:id`} element={<CoachSheet />} />
            <Route path={`${ROUTES.HISTORY_SHEET}/:id`} element={<HistorySheet />} />
          </Route>
        </Routes>
      </SnackbarProvider>
    </UserProvider>
  );
};

export default App;
