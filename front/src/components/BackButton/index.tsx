import { useNavigate } from 'react-router-dom';

import * as S from './styles';

import LeftArrowIcon from '@assets/left-arrow-disabled.svg';

const BackButton = () => {
  const navigate = useNavigate();

  return <S.ArrowIcon src={LeftArrowIcon} alt="화살표 아이콘" onClick={() => navigate(-1)} />;
};

export default BackButton;
