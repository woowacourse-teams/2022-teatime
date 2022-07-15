import { Routes, Route } from 'react-router-dom';
import Home from '@pages/Home';
import Reservation from '@pages/Reservation';
import SelectUser from '@pages/SelectUser';
import Header from '@components/Header';
import ScheduleProvider from '@context/ScheduleProvider';
import { ROUTES } from './constants';

const App = () => {
  return (
    <ScheduleProvider>
      <Header />
      <Routes>
        <Route path={ROUTES.SELECT_USER} element={<SelectUser />} />
        <Route path={ROUTES.HOME} element={<Home />} />
        <Route path={`${ROUTES.RESERVATION}/:id`} element={<Reservation />} />
      </Routes>
    </ScheduleProvider>
  );
};

export default App;
