import * as S from './styles';

interface FrameProps {
  children: React.ReactNode;
}

const Frame = ({ children }: FrameProps) => {
  return <S.Container>{children}</S.Container>;
};

export default Frame;
