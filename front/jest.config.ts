import { defaults } from 'jest-config';

export default {
  verbose: true,
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
  },
  moduleNameMapper: {
    '^@constants/(.*)$': '<rootDir>/src/constants/$1',
    '^@typings/(.*)$': '<rootDir>/src/typings/$1',
  },
  testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
  moduleFileExtensions: [...defaults.moduleFileExtensions, 'ts', 'tsx'],
  preset: 'ts-jest',
  testEnvironment: 'node',
  transformIgnorePatterns: ['<rootDir>/node_modules/'],
};
