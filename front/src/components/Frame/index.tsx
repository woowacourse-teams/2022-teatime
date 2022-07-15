import { Container } from './styles';

interface FrameProps {
  children: React.ReactNode;
}

const Frame = ({ children }: FrameProps) => {
  return <Container>{children}</Container>;
};

export default Frame;
