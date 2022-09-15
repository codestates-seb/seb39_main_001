import styled from 'styled-components';

function App() {
  return (
    <div className="App">
      <Styled>JUSE</Styled>
    </div>
  );
}

const Styled = styled.p`
  color: #be99ff;
  font-size: 100px;
  border: 1px solid ${({ theme }) => theme.colors.purple1};
  margin: 50px;
  font-weight: 900;
  :hover {
    color: ${({ theme }) => theme.colors.purple2};
  }
`;

export default App;
