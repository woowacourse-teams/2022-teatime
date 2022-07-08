import { useState } from 'react';
import axios from 'axios';
import dayjs from 'dayjs';
import InputModal from '@components/InputModal';
import ModalPotal from '@components/ModalPotal';
import { Schedule } from '@typings/domain';
import { Date, DateContainer, ScheduleBar } from './styles';

interface DateBoxProps {
  date?: number;
  schedules?: Schedule[];
}

const DateBox = ({ date, schedules = [] }: DateBoxProps) => {
  const [modalOpen, setModalOpen] = useState(false);
  const [selectScheduleId, setSelectScheduleId] = useState<number | null>(null);

  const handleToggleModal = () => {
    setModalOpen((prev) => !prev);
  };

  const handleClickScheduleBar = (scheduleId: number) => {
    setSelectScheduleId(scheduleId);
    setModalOpen(true);
  };

  const handleSubmitCrewName = async (
    e: React.FormEvent<HTMLFormElement>,
    scheduleId: number,
    crewName: string
  ) => {
    e.preventDefault();
    await axios.post(`/coaches/0/schedules/${scheduleId}`, { crewName });
    alert('면담신청이 예약되었습니다.😆');
    setModalOpen(false);
  };

  return (
    <DateContainer>
      <Date>{date}</Date>
      {schedules.map((schedule) => {
        return (
          <ScheduleBar key={schedule.id} onClick={() => handleClickScheduleBar(schedule.id)}>
            {dayjs(schedule.dateTime).format('H:mm')} - {dayjs(schedule.dateTime).format('H:mm')}
          </ScheduleBar>
        );
      })}
      <ModalPotal>
        {modalOpen && (
          <InputModal
            onClose={handleToggleModal}
            title={'면담 예약하기'}
            selectScheduleId={selectScheduleId}
            onSubmit={handleSubmitCrewName}
          />
        )}
      </ModalPotal>
    </DateContainer>
  );
};

export default DateBox;
