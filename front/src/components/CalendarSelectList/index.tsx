import * as S from './styles';

type Item = {
  id: string;
  text: string;
};

interface CalendarSelectListProps {
  lists: Item[];
  selectedItem: string;
  onSelect: (e: React.MouseEvent<HTMLElement>) => void;
}

const CalendarSelectList = ({ lists, selectedItem, onSelect }: CalendarSelectListProps) => {
  return (
    <S.Container onClick={onSelect}>
      {lists.map(({ id, text }) => (
        <S.ListItem key={id} id={id} isSelected={selectedItem === id}>
          {text}
        </S.ListItem>
      ))}
    </S.Container>
  );
};

export default CalendarSelectList;
