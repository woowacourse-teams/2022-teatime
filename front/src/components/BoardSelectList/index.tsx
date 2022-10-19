import { useContext } from 'react';

import { BoardChangeContext } from '@context/BoardModeProvider';
import * as S from './styles';

type Item = {
  id: string;
  text: string;
};

interface BoardSelectListProps {
  lists: Item[];
  hidden?: boolean;
  selectedItem: string;
}

const BoardSelectList = ({ lists, hidden, selectedItem }: BoardSelectListProps) => {
  const changeBoard = useContext(BoardChangeContext);

  return (
    <S.Container onClick={changeBoard} hidden={hidden}>
      {lists.map(({ id, text }) => (
        <S.ListItem key={id} id={id} isSelected={selectedItem === id}>
          {text}
        </S.ListItem>
      ))}
    </S.Container>
  );
};

export default BoardSelectList;
