import { useNavigate } from 'react-router-dom';
import { Layout, Menu, Title, Wrapper } from './styles';

const Coach = () => {
  const navigate = useNavigate();
  const handleClickCard = () => {
    navigate(`/schedule/1`);
  };

  return (
    <Layout>
      <Menu>
        <div>
          <span>확정 일정</span>
          <span>대기 일정</span>
        </div>
        <button onClick={handleClickCard}>일정 추가</button>
      </Menu>
      <div>
        <Title>7 / 25 월요일</Title>
        <Wrapper>
          <div>
            <span>🕒 11:00</span>
            <span>마루</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
          <div>
            <span>🕒 11:30</span>
            <span>아키</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
          <div>
            <span>🕒 13:00</span>
            <span>야호</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
        </Wrapper>
      </div>
      <div>
        <Title>7 / 26 화요일</Title>
        <Wrapper>
          <div>
            <span>🕒 10:00</span>
            <span>코이</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
          <div>
            <span>🕒 14:00</span>
            <span>안</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
        </Wrapper>
      </div>
      <div>
        <Title>7 / 27 수요일</Title>
        <Wrapper>
          <div>
            <span>🕒 15:00</span>
            <span>잉</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
          <div>
            <span>🕒 16:00</span>
            <span>쿄</span>
            <div>
              <button>내용 보기</button>
              <button>취소 하기</button>
            </div>
          </div>
        </Wrapper>
      </div>
    </Layout>
  );
};

export default Coach;
