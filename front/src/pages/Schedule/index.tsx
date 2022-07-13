import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';

const Schedule = () => {
  return (
    <Frame>
      <Calendar />
      <TimeList />
    </Frame>
  );
};

export default Schedule;
