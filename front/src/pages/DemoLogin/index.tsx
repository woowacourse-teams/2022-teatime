import { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';

import { UserDispatchContext } from '@context/UserProvider';
import * as S from './styles';

const DemoLogin = () => {
  const navigate = useNavigate();
  const dispatch = useContext(UserDispatchContext);
  const [nickname, setNickname] = useState('');

  const handleSubmitNickname = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (nickname.trim().length === 0) return alert('공백은 입력할 수 없습니다.');
    navigate('/demo-home', { state: nickname });
  };

  useEffect(() => {
    dispatch({ type: 'DELETE_USER' });
  }, []);

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
