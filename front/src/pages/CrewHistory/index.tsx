import * as S from './styles';
import ScheduleIcon from '@assets/schedule.svg';

const CrewHistory = () => {
  return (
    <S.Table>
      <caption>마이 페이지</caption>
      <S.Thead>
        <tr>
          <td>진행 상태</td>
          <td>코치</td>
          <td>날짜</td>
          <td>시간</td>
          <td />
        </tr>
      </S.Thead>
      <S.Tbody>
        <tr>
          <td>
            <span>승인전</span>
          </td>
          <td>
            <div>
              <img
                src="https://yt3.ggpht.com/ytc/AKedOLTIscftm_6DT4YengiVrl1lxQKmKUCTbTChZmmR=s176-c-k-c0x00ffffff-no-rj"
                alt="코치 프로필 이미지"
              />
              <span>왼손</span>
            </div>
          </td>
          <td>08월 02일</td>
          <td>11 : 00</td>
          <td>
            <img src={ScheduleIcon} alt="" />
            <img src={ScheduleIcon} alt="" />
          </td>
        </tr>
        <tr>
          <td>
            <span>승인완료</span>
          </td>
          <td>
            <div>
              <img
                src="https://yt3.ggpht.com/ytc/AKedOLTIscftm_6DT4YengiVrl1lxQKmKUCTbTChZmmR=s176-c-k-c0x00ffffff-no-rj"
                alt="코치 프로필 이미지"
              />
              <span>왼손</span>
            </div>
          </td>
          <td>08월 02일</td>
          <td>11 : 00</td>
          <td>
            <img src={ScheduleIcon} alt="" />
            <img src={ScheduleIcon} alt="" />
          </td>
        </tr>
      </S.Tbody>
    </S.Table>
  );
};

export default CrewHistory;
