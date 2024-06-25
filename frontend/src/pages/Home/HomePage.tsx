import { useNavigate  } from "react-router-dom";
import { PreRegisterForm } from "./components/PreRegisterForm";

const HomePage = () => {
  const navigate = useNavigate();

  function onSubmitHandler(cpf: String) {
    console.log("[HomePage] onSubmitHandler", cpf);
    navigate('/sobre', {state: {client: 'será'}});
  }

  return (
    <>
      <h1>Campanha</h1>
      <PreRegisterForm onSubmitHandler={(cpf) => onSubmitHandler(cpf)} />
    </>
  );
};

export default HomePage;
