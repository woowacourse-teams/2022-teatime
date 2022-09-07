const size = {
  mobileS: '320px',
  mobileM: '375px',
  mobileL: '425px',
  mobileXL: '480px',
  tablet: '768px',
  laptop: '1024px',
  laptopL: '1440px',
  desktop: '2560px',
};

const theme = {
  colors: {
    WHITE: '#ffffff',
    GRAY_100: '#f5f5f5',
    GRAY_150: '#ededed',
    GRAY_200: '#e2e1e1',
    GRAY_300: '#c4c4c4',
    GRAY_500: '#a0a0a0',
    GRAY_600: '#4b5563',
    GRAY_800: '#333333',
    RED_300: '#e6aca8',
    RED_400: '#e5837c',
    RED_600: '#eb0202',
    GREEN_100: '#eaf0da',
    GREEN_300: '#cce6ba',
    GREEN_400: '#b8e09b',
    GREEN_700: '#797d6b',
    GREEN_900: '#404338',
    BLUE_600: '#0085ff',
    BLUE_700: '#6D798E',
    BLUE_800: '#3d5a80',
    BLUE_900: '#1f2937',
    ORANGE_100: '#ffedd5',
    ORANGE_200: '#fae0af',
    ORANGE_600: '#ffa500',
    PURPLE_100: '#e8edff',
    PURPLE_300: '#a3ace6',
    PURPLE_700: '#653866',
    BLACK: '#000000',
  },
  devices: {
    mobileS: `max-width: ${size.mobileS}`,
    mobileM: `max-width: ${size.mobileM}`,
    mobileL: `max-width: ${size.mobileL}`,
    mobileXL: `max-width: ${size.mobileXL}`,
    tablet: `max-width: ${size.tablet}`,
    laptop: `max-width: ${size.laptop}`,
    laptopL: `max-width: ${size.laptopL}`,
    desktop: `max-width: ${size.desktop}`,
  },
};

export default theme;
