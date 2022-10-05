import * as S from './styles';

type Item = {
  id: string;
  text: string;
};

interface BoardSelectListProps {
  lists: Item[];
  hidden?: boolean;
  selectedItem: string;
  onSelect: (e: React.MouseEvent<HTMLElement>) => void;
}

const BoardSelectList = ({ lists, hidden, selectedItem, onSelect }: BoardSelectListProps) => {
  return (
    <S.Container onClick={onSelect} hidden={hidden}>
      {lists.map(({ id, text }) => (
        <S.ListItem key={id} id={id} isSelected={selectedItem === id}>
          {text}
        </S.ListItem>
      ))}
    </S.Container>
  );
};

export default BoardSelectList;
