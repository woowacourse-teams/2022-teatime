import * as S from './styles';
import RequestIcon from '@assets/request.svg';
import SubscribeIcon from '@assets/subscribe.svg';

interface CardProps {
  image: string;
  name: string;
  description: string;
  buttonName: string;
  isPossible?: boolean;
  onClick?: (e: React.MouseEvent) => void;
  isPreview?: boolean;
}

const Card = ({
  name,
  image,
  description,
  buttonName,
  isPossible,
  onClick,
  isPreview,
}: CardProps) => {
  return (
    <S.CardContainer onClick={onClick} isPossible={!!isPossible} isPreview={!!isPreview}>
      <S.IconWrapper>
        <img id="request" src={RequestIcon} alt="티타임 요청 보내기" />
        <img id="subscribe" src={SubscribeIcon} alt="구독 하기" />
      </S.IconWrapper>
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
