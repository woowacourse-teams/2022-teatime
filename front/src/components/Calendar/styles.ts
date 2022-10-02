import styled from 'styled-components';

const CalendarContainer = styled.div`
  /* pointer-events: none; Todo: 다중선택을 완료하고 타임리스트가 뜰때 비활성화하기 */

  display: flex;
  flex-direction: column;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    align-items: center;
  }
`;

const DateGrid = styled.div`
  display: grid;
  gap: 10px;
  grid-template-columns: repeat(7, 50px);

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    grid-template-columns: repeat(7, 40px);
  }
`;

const DayOfWeekBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
  padding-top: 3px;
  font-weight: bolder;
  font-size: 20px;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 40px;
    height: 40px;
  }
`;

const YearMonthContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  padding: 0 10px;

  div {
    display: flex;
    justify-content: space-between;
    width: 70px;
  }

  span {
    font-size: 24px;
    font-weight: bolder;
  }

  img {
    width: 13px;
    height: 24px;
    cursor: pointer;
  }

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    width: 100%;
    justify-content: center;

    span {
      margin-right: 30px;
    }
  }
`;

export { CalendarContainer, DateGrid, DayOfWeekBox, YearMonthContainer };
