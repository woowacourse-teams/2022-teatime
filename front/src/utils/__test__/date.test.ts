import {
  getDateTime,
  getFormatDate,
  getHourMinutes,
  getMonthDate,
  getMonthYearDetails,
} from '../date';

describe('입력한 날짜 정보를 올바른 포맷으로 변환하는지 확인한다.', () => {
  test("년도, 월, 일이 입력되면 'YYYY-MM-DD' 형식으로 변환한다.", () => {
    const year = '2022';
    const month = '08';
    const day = 3;

    expect(getFormatDate(year, month, day)).toBe('2022-08-03');
  });
});

describe('dateTime 형식을 올바른 포맷으로 변환하는지 확인한다.', () => {
  test("dateTime이 입력되면 'HH:MM' 형식으로 변환한다.", () => {
    const dateString = '2022-07-01T01:00:00.000Z';

    expect(getHourMinutes(dateString)).toBe('01:00');
  });

  test("dateTime이 입력되면 'MM월 DD일' 형식으로 변환한다.", () => {
    const dateString = '2022-07-01T01:00:00.000Z';

    expect(getMonthDate(dateString)).toBe('07월 01일');
  });
});

describe('Date 객체 형식을 올바르게 변환하는지 확인한다.', () => {
  test('현재 날짜를 새로운 날짜 정보 객체 형식으로 변환한다. (month: MM, year: YYYY, firstDOW: 0~6, lastDate: 28~31)', () => {
    const currentDate = new Date();
    const monthYearDetails = getMonthYearDetails(currentDate);

    expect(monthYearDetails.month.length).toBe(2);
    expect(monthYearDetails.year.length).toBe(4);
    expect(monthYearDetails.firstDOW).toBeLessThanOrEqual(6);
    expect(monthYearDetails.lastDate).toBeGreaterThanOrEqual(28);
  });
});

describe('날짜/시간을 올바르게 비교하는지 확인한다.', () => {
  test('입력된 날짜의 시간이 현재보다 과거인지 확인한다.', () => {
    const dateString = '2022-09-17T14:30:00.000Z';
    const isPastTime = new Date() > getDateTime(dateString);

    expect(isPastTime).toBe(true);
  });
});
