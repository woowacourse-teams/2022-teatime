import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import api from '@api/index';
import theme from '@styles/theme';
import type { CrewListMap } from '@typings/domain';

import PlusIcon from '@assets/plus.svg';
import * as S from './styles';
import dayjs from 'dayjs';

interface BoardItemValue {
  title: string;
  buttonName: string;
  color: string;
  handleClickMenuButton: (index: number, id: number) => void;
  handleClickCancelButton: (status: string, index: number, id: number) => void;
}

interface BoardItem {
  [key: string]: BoardItemValue;
}

const Coach = () => {
  const { id: coachId } = useParams();
  const navigate = useNavigate();
  const [crews, setCrews] = useState<CrewListMap>({
    beforeApproved: [],
    approved: [],
    inProgress: [],
  });

  const handleApprove = async (index: number, reservationId: number) => {
    try {
      await api.post(`/api/reservations/${reservationId}`, {
        coachId,
        isApproved: true,
      });

      setCrews((allBoards) => {
        const copyBeforeApprovedBoard = [...allBoards.beforeApproved];
        const currentItem = copyBeforeApprovedBoard[index];
        const copyApprovedBoard = [...allBoards.approved];
        copyBeforeApprovedBoard.splice(index, 1);
        copyApprovedBoard.push(currentItem);
        copyApprovedBoard.sort(
          (a, b) => Number(dayjs.tz(a.dateTime)) - Number(dayjs.tz(b.dateTime))
        );

        return {
          ...allBoards,
          beforeApproved: copyBeforeApprovedBoard,
          approved: copyApprovedBoard,
        };
      });
    } catch (error) {
      alert('승인 에러');
      console.log(error);
    }
  };

  const handleReject = async (status: string, index: number, reservationId: number) => {
    if (!confirm('예약을 거절하시겠습니까?')) return;

    try {
      await api.post(`/api/reservations/${reservationId}`, {
        coachId,
        isApproved: false,
      });

      setCrews((allBoards) => {
        const copyBeforeStatusBoard = [...allBoards[status]];
        copyBeforeStatusBoard.splice(index, 1);

        return {
          ...allBoards,
          [status]: copyBeforeStatusBoard,
        };
      });
    } catch (error) {
      alert('거절 기능 에러');
      console.log(error);
    }
  };

  const handleCancel = async (status: string, index: number, reservationId: number) => {
    if (!confirm('예약을 취소하시겠습니까?')) return;

    try {
      await api.delete(`/api/reservations/${reservationId}`, {
        headers: {
          applicantId: Number(coachId),
          role: 'COACH',
        },
      });

      setCrews((allBoards) => {
        const copyBeforeStatusBoard = [...allBoards[status]];
        copyBeforeStatusBoard.splice(index, 1);

        return {
          ...allBoards,
          [status]: copyBeforeStatusBoard,
        };
      });
    } catch (error) {
      alert('취소 에러');
      console.log(error);
    }
  };

  const boardItem: BoardItem = {
    beforeApproved: {
      title: '대기중인 일정',
      buttonName: '승인하기',
      color: theme.colors.ORANGE_600,
      handleClickMenuButton: handleApprove,
      handleClickCancelButton: handleReject,
    },
    approved: {
      title: '확정된 일정',
      buttonName: '내용보기',
      color: theme.colors.PURPLE_300,
      handleClickMenuButton: () => console.log('내용보기'),
      handleClickCancelButton: handleCancel,
    },
    inProgress: {
      title: '진행중인 일정',
      buttonName: '이력작성',
      color: theme.colors.GREEN_700,
      handleClickMenuButton: () => console.log('이력작성'),
      handleClickCancelButton: handleCancel,
    },
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: crewListMap } = await api.get('/api/coaches/me/reservations', {
          headers: { coachId: Number(coachId) },
        });

        setCrews(crewListMap);
      } catch (error) {
        alert('크루 목록 get 에러');
        console.log(error);
      }
    })();
  }, []);

  return (
    <S.Layout>
      <S.BoardListHeader>
        <S.AddScheduleButton onClick={() => navigate(`/schedule/41`)}>
          <img src={PlusIcon} alt="추가 아이콘" />
          <span>일정 추가</span>
        </S.AddScheduleButton>
      </S.BoardListHeader>
      <S.BoardListContainer>
        {Object.keys(crews).map((status) => {
          const { title, buttonName, color, handleClickMenuButton, handleClickCancelButton } =
            boardItem[status];

          return (
            <Board key={status} title={title} color={color} length={crews[status].length}>
              {crews[status].map((crew, index) => (
                <BoardItem
                  key={crew.reservationId}
                  dateTime={crew.dateTime}
                  image={crew.crewImage}
                  personName={crew.crewName}
                  buttonName={buttonName}
                  color={color}
                  onClickMenu={() => handleClickMenuButton(index, crew.reservationId)}
                  onClickCancel={() => handleClickCancelButton(status, index, crew.reservationId)}
                />
              ))}
            </Board>
          );
        })}
      </S.BoardListContainer>
    </S.Layout>
  );
};

export default Coach;
