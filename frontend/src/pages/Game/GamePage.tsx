import { useLocation } from "react-router-dom";


const GamePage = () => {
  const { state } = useLocation();

  return (
    <>
      <h1>GamePage</h1>
    </>
  );
}

export default GamePage;
