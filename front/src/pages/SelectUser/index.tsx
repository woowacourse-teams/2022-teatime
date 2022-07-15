import { Link } from 'react-router-dom';
import { ROUTES } from '@constants/index';
import { SelectUserContainer } from './styles';

const SelectUser = () => {
  return (
    <SelectUserContainer>
      <Link to={ROUTES.SCHEDULE}>
        <button>코치</button>
      </Link>
      <Link to={ROUTES.HOME}>
        <button>크루</button>
      </Link>
    </SelectUserContainer>
  );
};

export default SelectUser;
