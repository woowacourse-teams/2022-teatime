import styled from 'styled-components';

const Item = styled.div<{ isSelected: boolean }>`
  margin: 0 16px 10px 0;
  padding: 8px 12px;
  background-color: ${({ theme, isSelected }) => isSelected && theme.colors.GRAY_200};
  border-radius: 8px;
  border: 2px solid ${({ theme }) => theme.colors.GRAY_300};
  cursor: pointer;

  :hover {
    border: 2px solid ${({ theme }) => theme.colors.GRAY_500};
  }
`;

const Date = styled.div`
  margin-bottom: 4px;
  img {
    width: 18px;
    height: 18px;
    margin-right: 4px;
  }

  span {
    vertical-align: top;
    color: ${({ theme }) => theme.colors.BLUE_700};
  }
`;

const Profile = styled.div`
  display: flex;
  align-items: center;

  img {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    margin-right: 8px;
  }

  span {
    color: ${({ theme }) => theme.colors.GRAY_600};
  }
`;

export { Item, Date, Profile };
