function App() {
<<<<<<< Updated upstream
  return <div className='App'>hello</div>;
=======
  return (
    <>
      <NavbarPublic />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/join' element={<Join />}></Route>
      </Routes>
    </>
  );
>>>>>>> Stashed changes
}

export default App;
