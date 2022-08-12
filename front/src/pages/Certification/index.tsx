import { useSearchParams } from 'react-router-dom';

const Certification = () => {
  const [searchParams] = useSearchParams();

  const code = searchParams.get('code') ?? '';
  console.log('code', code);

  return <div></div>;
};

export default Certification;
