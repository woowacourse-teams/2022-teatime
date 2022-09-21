import * as S from './styles';

interface Item {
  id: string;
  text: string;
}

interface SelectListProps {
  lists: Item[];
  hidden?: boolean;
  selectedItem: string;
  onSelect: (e: React.MouseEvent<HTMLElement>) => void;
}

const SelectList = ({ lists, hidden, selectedItem, onSelect }: SelectListProps) => {
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

export default SelectList;
