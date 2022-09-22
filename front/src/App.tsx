import { lazy, Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';

import Header from '@components/Header';
import Loading from '@components/Loading';
import SnackbarProvider from '@context/SnackbarProvider';
import UserProvider from '@context/UserProvider';
import AuthRoute from './AuthRoute';
import { ROUTES } from './constants';

const CrewMain = lazy(() => import('@pages/CrewMain'));
const Reservation = lazy(() => import('@pages/Reservation'));
const Home = lazy(() => import('@pages/Home'));
const Certification = lazy(() => import('@pages/Certification'));
const Schedule = lazy(() => import('@pages/Schedule'));
const CoachMain = lazy(() => import('@pages/CoachMain'));
const NotFound = lazy(() => import('@pages/NotFound'));
const CrewSheet = lazy(() => import('@pages/CrewSheet'));
const CoachSheet = lazy(() => import('@pages/CoachSheet'));
const HistorySheet = lazy(() => import('@pages/HistorySheet'));
const CrewHistory = lazy(() => import('@pages/CrewHistory'));
const CoachHistory = lazy(() => import('@pages/CoachHistory'));

const App = () => {
  return (
    <UserProvider>
      <SnackbarProvider>
        <Header />
        <Suspense fallback={<Loading showImage={false} text="Loading..." />}>
          <Routes>
            <Route path={ROUTES.HOME} element={<Home />} />
            <Route path={ROUTES.CERTIFICATION} element={<Certification />} />
            <Route path="/*" element={<NotFound />} />
            <Route element={<AuthRoute role="CREW" />}>
              <Route path={ROUTES.CREW} element={<CrewMain />} />
              <Route path={ROUTES.CREW_HISTORY} element={<CrewHistory />} />
              <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
              <Route path={`${ROUTES.CREW_SHEET}/:id`} element={<CrewSheet />} />
            </Route>
            <Route element={<AuthRoute role="COACH" />}>
              <Route path={ROUTES.COACH} element={<CoachMain />} />
              <Route path={ROUTES.SCHEDULE} element={<Schedule />} />
              <Route path={ROUTES.COACH_HISTORY} element={<CoachHistory />} />
              <Route path={`${ROUTES.COACH_SHEET}/:id`} element={<CoachSheet />} />
              <Route path={`${ROUTES.HISTORY_SHEET}/:id`} element={<HistorySheet />} />
            </Route>
          </Routes>
        </Suspense>
      </SnackbarProvider>
    </UserProvider>
  );
};

export default App;
