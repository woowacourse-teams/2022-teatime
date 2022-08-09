import dayjs from 'dayjs';

import { History } from '@typings/domain';
import theme from '@styles/theme';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';

interface TableRowProps {
  history: History;
}

interface HistoryStatus {
  [key: string]: Status;
}

type Status = { statusName: string; color: string; backgroundColor: string };

const historyStatus: HistoryStatus = {
  BEFORE_APPROVED: {
    statusName: '승인전',
    color: theme.colors.ORANGE_600,
    backgroundColor: theme.colors.ORANGE_100,
  },
  APPROVED: {
    statusName: '승인완료',
    color: theme.colors.PURPLE_300,
    backgroundColor: theme.colors.PURPLE_100,
  },
  IN_PROGRESS: {
    statusName: '면담완료',
    color: theme.colors.GREEN_700,
    backgroundColor: theme.colors.GREEN_100,
  },
};

const TableRow = ({ history }: TableRowProps) => {
  const { status, coachName, coachImage, dateTime } = history;
  const { statusName, color, backgroundColor } = historyStatus[status];

  const date = dayjs.tz(dateTime).format('MM월 DD일');
  const time = dayjs.tz(dateTime).format('HH:mm');

  return (
    <tr>
      <td>
        <S.Span bgColor={backgroundColor} color={color}>
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
