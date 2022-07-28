import { Link } from 'react-router-dom';
import { ROUTES } from '@constants/index';
import * as S from './styles';

const SelectUser = () => {
  return (
    <S.SelectUserContainer>
      <Link to={ROUTES.COACH}>
        <button>코치</button>
      </Link>
      <Link to={ROUTES.CREW}>
        <button>크루</button>
      </Link>
    </S.SelectUserContainer>
  );
};

export default SelectUser;
