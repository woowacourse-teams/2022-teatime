const coaches = [
  {
    id: 0,
    name: '준',
    description: '밝은 에너지로 함께 성장하는 환경을 만드는 메이커준입니다. 😄',
    image:
      'https://user-images.githubusercontent.com/48676844/177775558-fe85d0f0-0cf5-4ef5-948f-80be9b5b055b.jpeg',
  },
  {
    id: 1,
    name: '포코',
    description:
      '인스턴트 같은 교육을 위한 강의보다는 진하게 오랜 시간 도움이 되는 강의를 만들고 싶습니다.',
    image: 'https://avatars.githubusercontent.com/u/23068523?v=4',
  },
  {
    id: 2,
    name: '공원',
    description:
      '평생을 학습하며 성장하기 위한 가장 중요한 것은 소프트웨어 장인이 되겠다는 마음가짐 이지 않을까? 🌳',
    image: 'https://avatars.githubusercontent.com/u/81607552?v=4',
  },
  {
    id: 3,
    name: '왼손',
    description: '처음 코딩을 배웠을 때의 어려움과 막막함을 잊지 않고 여러분과 함께 가겠습니다.',
    image:
      'https://yt3.ggpht.com/ytc/AKedOLTIscftm_6DT4YengiVrl1lxQKmKUCTbTChZmmR=s176-c-k-c0x00ffffff-no-rj',
  },
  {
    id: 4,
    name: '공원',
    description:
      '평생을 학습하며 성장하기 위한 가장 중요한 것은 소프트웨어 장인이 되겠다는 마음가짐 이지 않을까? 🌳',
    image: 'https://avatars.githubusercontent.com/u/81607552?v=4',
  },
  {
    id: 5,
    name: '포비',
    description: '동의되지 않는 권력에 굴복되지 마라! 반란군을 키우는 캡틴 포비입니다.',
    image:
      'https://user-images.githubusercontent.com/48676844/177775689-096b53fd-a9f2-44e6-9daf-73e4e0b9a603.png',
  },
  {
    id: 6,
    name: '준',
    description: '밝은 에너지로 함께 성장하는 환경을 만드는 메이커준입니다. 😄',
    image:
      'https://user-images.githubusercontent.com/48676844/177775558-fe85d0f0-0cf5-4ef5-948f-80be9b5b055b.jpeg',
  },
  {
    id: 7,
    name: '포코',
    description:
      '인스턴트 같은 교육을 위한 강의보다는 진하게 오랜 시간 도움이 되는 강의를 만들고 싶습니다.',
    image: 'https://avatars.githubusercontent.com/u/23068523?v=4',
  },
];

const schedules = (year: string, month: string) => {
  return [
    {
      day: 25,
      schedules: [{ id: 11111, dateTime: `${year}-${month}-25T10:00:00.000Z`, isPossible: true }],
    },
    {
      day: 26,
      schedules: [
        { id: 23323, dateTime: `${year}-${month}-26T10:00:00.000Z`, isPossible: false },
        { id: 33323, dateTime: `${year}-${month}-26T10:00:00.000Z`, isPossible: false },
      ],
    },
    {
      day: 28,
      schedules: [
        { id: 66666, dateTime: `${year}-${month}-28T10:30:00.000Z`, isPossible: false },
        { id: 141513, dateTime: `${year}-${month}-28T11:00:00.000Z`, isPossible: true },
        { id: 13524536, dateTime: `${year}-${month}-28T15:00:00.000Z`, isPossible: false },
        { id: 1516146, dateTime: `${year}-${month}-28T15:30:00.000Z`, isPossible: false },
        { id: 4132346325, dateTime: `${year}-${month}-28T17:00:00.000Z`, isPossible: true },
      ],
    },
    {
      day: 29,
      schedules: [
        { id: 77777, dateTime: `${year}-${month}-29T10:00:00.000Z`, isPossible: true },
        { id: 88888, dateTime: `${year}-${month}-29T11:30:00.000Z`, isPossible: true },
        { id: 127678, dateTime: `${year}-${month}-29T12:00:00.000Z`, isPossible: true },
        { id: 335372, dateTime: `${year}-${month}-29T12:30:00.000Z`, isPossible: true },
        { id: 283351, dateTime: `${year}-${month}-29T15:00:00.000Z`, isPossible: false },
        { id: 283352, dateTime: `${year}-${month}-29T15:30:00.000Z`, isPossible: true },
        { id: 283353, dateTime: `${year}-${month}-29T16:00:00.000Z`, isPossible: false },
        { id: 283357, dateTime: `${year}-${month}-29T16:30:00.000Z`, isPossible: true },
      ],
    },
  ];
};

const reservationInfo = [
  {
    dateTime: '2022-07-01T01:00:00.000Z',
    coachName: '준',
    coachImage: 'https://avatars.githubusercontent.com/u/50367798?v=4',
    sheets: [
      {
        questionNumber: 1,
        questionContent: '이번 면담을 통해 논의하고 싶은 내용',
        answerContent: '',
      },
      {
        questionNumber: 2,
        questionContent: '최근에 자신이 긍정적으로 보는 시도와 변화',
        answerContent: '',
      },
      {
        questionNumber: 3,
        questionContent: '이번 면담을 통해 생기기를 원하는 변화',
        answerContent: '',
      },
    ],
  },
  {
    dateTime: '2022-07-01T01:00:00.000Z',
    coachName: '포코',
    coachImage: 'https://avatars.githubusercontent.com/u/23068523?v=4',
    sheets: [
      {
        questionNumber: 1,
        questionContent: '이번 면담을 통해 논의하고 싶은 내용',
        answerContent: '',
      },
      {
        questionNumber: 2,
        questionContent: '최근에 자신이 긍정적으로 보는 시도와 변화',
        answerContent: '',
      },
      {
        questionNumber: 3,
        questionContent: '이번 면담을 통해 생기기를 원하는 변화',
        answerContent: '',
      },
    ],
  },
  {
    dateTime: '2022-07-01T01:00:00.000Z',
    coachName: '공원',
    coachImage: 'https://avatars.githubusercontent.com/u/81607552?v=4',
    sheets: [
      {
        questionNumber: 1,
        questionContent: '이번 면담을 통해 논의하고 싶은 내용',
        answerContent: '',
      },
      {
        questionNumber: 2,
        questionContent: '최근에 자신이 긍정적으로 보는 시도와 변화',
        answerContent: '',
      },
      {
        questionNumber: 3,
        questionContent: '이번 면담을 통해 생기기를 원하는 변화',
        answerContent: '',
      },
    ],
  },
];

const crewHistories = [
  {
    reservationId: 0,
    coachName: '준',
    coachImage: 'https://avatars.githubusercontent.com/u/50367798?v=4',
    dateTime: '2022-07-01T01:00:00.000Z',
    status: 'BEFORE_APPROVED',
  },
  {
    reservationId: 1,
    coachName: '포코',
    coachImage: 'https://avatars.githubusercontent.com/u/23068523?v=4',
    dateTime: '2022-07-01T01:00:00.000Z',
    status: 'BEFORE_APPROVED',
  },
  {
    reservationId: 2,
    coachName: '공원',
    coachImage: 'https://avatars.githubusercontent.com/u/81607552?v=4',
    dateTime: '2022-07-01T01:00:00.000Z',
    status: 'BEFORE_APPROVED',
  },
];

export { coaches, schedules, reservationInfo, crewHistories };
