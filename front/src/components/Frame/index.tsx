import type { PropsWithRequiredChildren } from '@typings/utils';
import * as S from './styles';

const Frame = ({ children }: PropsWithRequiredChildren) => {
  return <S.Container>{children}</S.Container>;
};

export default Frame;
