import { useNavigate  } from "react-router-dom";
import { PreRegisterForm } from "./components/PreRegisterForm";
import { AboutComponent } from "./components/AboutComponent";
import { BrandsComponent } from "./components/BrandsComponent";
import { FaqComponent } from "./components/FaqComponent";

const HomePage = () => {
  const navigate = useNavigate();

  const onSubmitHandler = (cpf: String) => {
    navigate('/cadastro', {state: {cpf}});
  };

  return (
    <>
      <h1>Principal</h1>
      <PreRegisterForm onSubmitHandler={(cpf) => onSubmitHandler(cpf)} />
      <AboutComponent />
      <BrandsComponent />
      <FaqComponent />
    </>
  );
};

export default HomePage;
