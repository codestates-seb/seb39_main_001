import React from 'react';
import { CookiesProvider } from 'react-cookie';
import ReactDOM from 'react-dom/client';
import { QueryClientProvider, QueryClient } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import App from './App';
import GlobalStyles from './assets/styles/GlobalStyle';
import theme from './assets/styles/Theme';
import { worker } from './mocks/worker';

<<<<<<< HEAD
// if (process.env.NODE_ENV === 'development') {
//   worker.start();
// }
=======
if (process.env.NODE_ENV === 'development') {
  worker.start();
}
>>>>>>> origin/v1

const queryClient = new QueryClient();
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
<<<<<<< HEAD
    <CookiesProvider>
      <BrowserRouter>
        <QueryClientProvider client={queryClient}>
          <ThemeProvider theme={theme}>
            <GlobalStyles />
            <App />
          </ThemeProvider>
        </QueryClientProvider>
      </BrowserRouter>
    </CookiesProvider>
=======
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>
        <ThemeProvider theme={theme}>
          <GlobalStyles />
          <App />
        </ThemeProvider>
      </QueryClientProvider>
    </BrowserRouter>
>>>>>>> origin/v1
  </React.StrictMode>
);
