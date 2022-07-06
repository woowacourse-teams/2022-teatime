import { DateContainer } from './styles';

interface DateBoxProps {
  date?: number;
}

const DateBox = ({ date }: DateBoxProps) => {
  return (
    <div>
      <DateContainer>{date}</DateContainer>
    </div>
  );
};

export default DateBox;
