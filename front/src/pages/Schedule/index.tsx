import ReservationInfo from '@components/ReservationInfo';
import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';

const Schedule = () => {
  return (
    <Frame>
      <ReservationInfo />
      <Calendar />
      <TimeList />
    </Frame>
  );
};

export default Schedule;
