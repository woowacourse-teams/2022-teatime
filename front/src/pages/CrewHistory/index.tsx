import styled from 'styled-components';

import ScheduleIcon from '@assets/schedule.svg';

const CrewHistory = () => {
  return (
    <Table>
      <Thead>
        <tr>
          <td colSpan={2}>이름</td>
          <td>날짜</td>
          <td>시간</td>
          <td />
        </tr>
      </Thead>
      <Tbody>
        <tr>
          <td>
            <img
              src="https://yt3.ggpht.com/ytc/AKedOLTIscftm_6DT4YengiVrl1lxQKmKUCTbTChZmmR=s176-c-k-c0x00ffffff-no-rj"
              alt="코치 프로필 이미지"
            />
          </td>
          <td>왼손</td>
          <td>08월 02일</td>
          <td>11 : 00</td>
          <td>
            <img src={ScheduleIcon} alt="" />
          </td>
        </tr>
        <tr>
          <td>
            <img
              src="https://yt3.ggpht.com/ytc/AKedOLTIscftm_6DT4YengiVrl1lxQKmKUCTbTChZmmR=s176-c-k-c0x00ffffff-no-rj"
              alt="코치 프로필 이미지"
            />
          </td>
          <td>왼손</td>
          <td>08월 02일</td>
          <td>11 : 00</td>
          <td>
            <img src={ScheduleIcon} alt="" />
          </td>
        </tr>
      </Tbody>
    </Table>
  );
};

const Table = styled.table`
  width: 70%;
  border-collapse: collapse;
`;

const Thead = styled.thead`
  text-align: center;
  background-color: ${({ theme }) => theme.colors.GRAY_200};

  td {
    padding: 14px 0;
    font-size: 18px;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.GRAY_600};
  }
`;

const Tbody = styled.tbody`
  tr {
    border-bottom: 1px solid ${({ theme }) => theme.colors.GRAY_300};
  }

  td {
    padding: 20px;
    text-align: center;
    color: ${({ theme }) => theme.colors.BLUE_700};
    font-size: 18px;
    font-weight: bold;
  }

  tr :nth-child(1) {
    img {
      width: 80px;
      height: 80px;
      border-radius: 12px;
    }
  }

  tr :nth-child(2) {
    color: ${({ theme }) => theme.colors.GRAY_600};
  }

  tr :nth-child(5) {
    img {
      width: 22px;
      height: 22px;
      cursor: pointer;
      :hover {
        transform: scale(1.1);
      }
    }
  }
`;

export default CrewHistory;
