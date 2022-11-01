const crews = {
  beforeApproved: [
    {
      crewId: 1,
      crewName: '아키',
      reservationId: 17,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-20T14:00:00.000Z',
    },
    {
      crewId: 2,
      crewName: '마루',
      reservationId: 18,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-21T15:00:00.000Z',
    },
    {
      crewId: 3,
      crewName: '호두',
      reservationId: 19,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-22T11:00:00.000Z',
    },
  ],
  approved: [
    {
      crewId: 4,
      crewName: '야호',
      reservationId: 20,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-19T20:00:00.000Z',
    },
    {
      crewId: 5,
      crewName: '호야',
      reservationId: 21,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-19T21:00:00.000Z',
      sheetStatus: 'WRITING',
    },
    {
      crewId: 6,
      crewName: '잉',
      reservationId: 22,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-19T22:00:00.000Z',
      sheetStatus: 'WRITING',
    },
  ],
  inProgress: [
    {
      crewId: 7,
      crewName: '안',
      reservationId: 123,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-17T20:00:00.000Z',
    },
    {
      crewId: 8,
      crewName: '코이',
      reservationId: 222,
      crewImage:
        'https://user-images.githubusercontent.com/48676844/181402601-b1b2c2ff-29a7-44fb-9613-16bd999abc1e.png',
      dateTime: '2022-08-17T21:00:00.000Z',
    },
  ],
};

const coachHistories = [
  {
    reservationId: 1,
    crewName: '아키',
    crewImage: 'https://avatars.githubusercontent.com/u/50367798?v=4',
    dateTime: '2022-07-01T01:00:00.000Z',
    status: 'CANCELED',
  },
  {
    reservationId: 2,
    crewName: '야호',
    crewImage: 'https://avatars.githubusercontent.com/u/23068523?v=4',
    dateTime: '2022-07-01T01:00:00.000Z',
    status: 'CANCELED',
  },
  {
    reservationId: 3,
    crewName: '마루',
    crewImage: 'https://avatars.githubusercontent.com/u/81607552?v=4',
    dateTime: '2022-07-01T01:00:00.000Z',
    status: 'DONE',
  },
];

export { crews, coachHistories };
