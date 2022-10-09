import * as S from './styles';
import RequestIcon from '@assets/request.svg';

interface CardProps {
  image: string;
  name: string;
  description: string;
  buttonName: string;
  isPossible: boolean;
  onClick: (e: React.MouseEvent) => void;
}

const Card = ({ name, image, description, buttonName, isPossible, onClick }: CardProps) => {
  return (
    <S.CardContainer onClick={onClick} isPossible={isPossible}>
      <S.RequestIcon
        src={RequestIcon}
        alt="티타임 요청 보내기"
        onClick={() => console.log('click')}
      />
      <S.CardWrapper>
        <S.ImageWrapper>
          <img src={image} alt={`${name} 카드 이미지`} />
        </S.ImageWrapper>
        <span>{name}</span>
        <p>{description}</p>
        <S.ButtonWrapper>
          <button>{buttonName}</button>
        </S.ButtonWrapper>
      </S.CardWrapper>
    </S.CardContainer>
  );
};

export default Card;
