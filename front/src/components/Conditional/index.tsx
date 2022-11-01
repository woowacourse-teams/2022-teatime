import type { PropsWithRequiredChildren } from '@typings/utils';

interface ConditionalProps {
  condition: boolean;
}

const Conditional = ({ children, condition }: PropsWithRequiredChildren<ConditionalProps>) => {
  return <>{condition && children}</>;
};

export default Conditional;
