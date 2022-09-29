import { getMonthDate, getHourMinutes } from '@utils/date';
import * as S from './styles';

import ScheduleIcon from '@assets/schedule.svg';
import CancelIcon from '@assets/cancel.svg';

interface TableRowProps {
  id: number;
  status: string;
  isHiddenSheet?: boolean;
  name: string;
  image: string;
  dateTime: string;
  statusName: string;
  color: string;
  bgColor: string;
  onClickSheet: () => void;
  onClickCancel?: (reservationId: number) => void;
}

const TableRow = ({
  id,
  status,
  isHiddenSheet,
  name,
  image,
  dateTime,
  statusName,
  color,
  bgColor,
  onClickSheet,
  onClickCancel,
}: TableRowProps) => {
  const date = getMonthDate(dateTime);
  const time = getHourMinutes(dateTime);
  const isEditStatus = status === 'BEFORE_APPROVED' || status === 'APPROVED';

  return (
    <S.TbodyRow>
      <td>
        <S.Span bgColor={bgColor} color={color}>
          {statusName}
        </S.Span>
      </td>
      <td>
        <S.Profile>
          <img src={image} alt={`${name} 프로필 이미지`} />
          <span>{name}</span>
        </S.Profile>
      </td>
      <td>{date}</td>
      <td>{time}</td>
      <td>
        {!isHiddenSheet && <S.Icon src={ScheduleIcon} alt="스케줄 아이콘" onClick={onClickSheet} />}
        {isEditStatus && (
          <S.Icon src={CancelIcon} alt="취소 아이콘" onClick={() => onClickCancel?.(id)} />
        )}
      </td>
    </S.TbodyRow>
  );
};

export default TableRow;
