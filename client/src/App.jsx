import { Route, Routes } from 'react-router-dom';
import Join from './pages/Join';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/join" element={<Join />}></Route>
      </Routes>
    </div>
  );
}

export default App;
