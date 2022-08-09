import { History } from '@typings/domain';
import { getMonthDate, getHourMinutes } from '@utils/index';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';

interface TableRowProps {
  history: History;
  statusName: string;
  color: string;
  bgColor: string;
}

const TableRow = ({ history, statusName, color, bgColor }: TableRowProps) => {
  const { coachName, coachImage, dateTime } = history;
  const date = getMonthDate(dateTime);
  const time = getHourMinutes(dateTime);

  return (
    <tr>
      <td>
        <S.Span bgColor={bgColor} color={color}>
          {statusName}
        </S.Span>
      </td>
      <td>
        <S.Profile>
          <img src={coachImage} alt="코치 프로필 이미지" />
          <span>{coachName}</span>
        </S.Profile>
      </td>
      <td>{date}</td>
      <td>{time}</td>
      <td>
        <S.Icon src={ScheduleIcon} alt="면담 시트 아이콘" />
        <S.Icon src={ScheduleIcon} alt="휴지통 아이콘" />
      </td>
    </tr>
  );
};

export default TableRow;
