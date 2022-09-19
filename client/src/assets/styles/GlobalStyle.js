import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset'; // style-reset 패키지

const GlobalStyles = createGlobalStyle`
  ${reset}
  a{
      text-decoration: none;
      color: inherit;
  }
  *{
      box-sizing: border-box;
  }
  body {
      font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
      font-size: 16px;
      color: ${({ theme }) => theme.colors.black1};
  }
`;

export default GlobalStyles;
