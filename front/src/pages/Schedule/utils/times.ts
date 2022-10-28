import type { MultipleTime, TimeSchedule } from '@typings/domain';

const timeArray = [
  '10:00',
  '10:30',
  '11:00',
  '11:30',
  '12:00',
  '12:30',
  '13:00',
  '13:30',
  '14:00',
  '14:30',
  '15:00',
  '15:30',
  '16:00',
  '16:30',
  '17:00',
  '17:30',
  '18:00',
  '18:30',
  '19:00',
  '19:30',
  '20:00',
  '20:30',
  '21:00',
  '21:30',
];

const getAllTime = (date: string) => {
  return timeArray.map((time, index) => ({
    id: index,
    dateTime: `${date}T${time}:00.000Z`,
    isSelected: false,
  }));
};

const getSelectedTimes = <T extends TimeSchedule | MultipleTime>(array: T[]): string[] => {
  return array.reduce((newArray, { isSelected, dateTime }) => {
    if (isSelected) {
      newArray.push(dateTime);
    }
    return newArray;
  }, [] as string[]);
};

const changeSelectedTime = <T extends TimeSchedule | MultipleTime>(
  array: T[],
  dateTime: string
) => {
  const selectedIndex = array.findIndex((time) => time.dateTime === dateTime);
  const newArray = [...array];
  newArray[selectedIndex].isSelected = !newArray[selectedIndex].isSelected;

  return newArray;
};

export { timeArray, getAllTime, getSelectedTimes, changeSelectedTime };
