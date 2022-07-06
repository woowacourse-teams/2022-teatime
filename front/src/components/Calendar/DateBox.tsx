import { DateContainer } from './styles';

interface DateBoxProps {
  date?: number;
}

const DateBox = ({ date }: DateBoxProps) => {
  return <DateContainer>{date}</DateContainer>;
};

export default DateBox;
