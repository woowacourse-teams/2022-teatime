import * as S from './styles';

interface Item {
  id: string;
  text: string;
}

interface SelectListProps {
  lists: Item[];
  show: boolean;
  selectedItem: string;
  onSelect: (e: React.MouseEvent<HTMLElement>) => void;
}

const SelectList = ({ lists, show, selectedItem, onSelect }: SelectListProps) => {
  return (
    <S.Container onClick={onSelect} show={show}>
      {lists.map(({ id, text }) => (
        <S.ListItem key={id} id={id} isSelected={selectedItem === id}>
          {text}
        </S.ListItem>
      ))}
    </S.Container>
  );
};

export default SelectList;
