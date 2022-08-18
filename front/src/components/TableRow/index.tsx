import { getMonthDate, getHourMinutes } from '@utils/date';
import { History } from '@typings/domain';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import TrashIcon from '@assets/trash.svg';

interface TableRowProps {
  history: History;
  statusName: string;
  color: string;
  bgColor: string;
  onClickSheet: (reservationId: number) => void;
  onClickDelete: (reservationId: number) => void;
}

const TableRow = ({
  history,
  statusName,
  color,
  bgColor,
  onClickSheet,
  onClickDelete,
}: TableRowProps) => {
  const { reservationId, status, coachName, coachImage, dateTime } = history;
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
        <S.Icon
          src={ScheduleIcon}
          alt="스캐줄 아이콘"
          onClick={() => onClickSheet(reservationId)}
        />
        {status !== 'IN_PROGRESS' && (
          <S.Icon
            src={TrashIcon}
            alt="휴지통 아이콘"
            onClick={() => onClickDelete(reservationId)}
          />
        )}
      </td>
    </tr>
  );
};

export default TableRow;
