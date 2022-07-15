interface ConditionalProps {
  children: React.ReactNode;
  condition: boolean;
}

const Conditional = ({ children, condition }: ConditionalProps) => {
  return <>{condition && children}</>;
};

export default Conditional;
