import React from 'react';
import { CookiesProvider } from 'react-cookie';
import ReactDOM from 'react-dom/client';
import { QueryClientProvider, QueryClient } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
// import { ThemeProvider } from 'styled-components';
import App from './App';
import AutoScrollTop from './hooks/AutoScrollTop';
// import GlobalStyles from './assets/styles/GlobalStyle';
// import theme from './assets/styles/Theme';
// import { lightTheme, darkTheme } from './assets/styles/Theme';
// import { worker } from './mocks/worker';

// if (process.env.NODE_ENV === 'development') {
//   worker.start();
// }

const queryClient = new QueryClient();
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <CookiesProvider>
      <BrowserRouter>
        <AutoScrollTop />
        <QueryClientProvider client={queryClient}>
          {/* <ThemeProvider theme={theme}>
						<GlobalStyles />
						<ReactQueryDevtools initialIsOpen={false} /> */}
          <App />
          {/* </ThemeProvider> */}
        </QueryClientProvider>
      </BrowserRouter>
    </CookiesProvider>
  </React.StrictMode>
);
