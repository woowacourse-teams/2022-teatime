import { Link } from 'react-router-dom';
import * as S from './styles';

import { ROUTES } from '@constants/index';

const SelectUser = () => {
  return (
    <S.SelectUserContainer>
      <Link to={`${ROUTES.COACH}/41`}>
        <button>코치</button>
      </Link>
      <Link to={ROUTES.CREW}>
        <button>크루</button>
      </Link>
    </S.SelectUserContainer>
  );
};

export default SelectUser;
