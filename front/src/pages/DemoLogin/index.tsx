import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './styles';

const DemoLogin = () => {
  const navigate = useNavigate();
  const [nickname, setNickname] = useState('');

  const handleSubmitNickname = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    navigate('/demo-home', { state: nickname });
  };

  return (
    <S.Container>
      <form onSubmit={handleSubmitNickname}>
        <label htmlFor="nickname">닉네임 (1~10자)</label>
        <input
          id="nickname"
          type="text"
          maxLength={10}
          onChange={(e) => setNickname(e.target.value)}
          autoFocus
          required
        />
        <button>로그인</button>
      </form>
    </S.Container>
  );
};

export default DemoLogin;
