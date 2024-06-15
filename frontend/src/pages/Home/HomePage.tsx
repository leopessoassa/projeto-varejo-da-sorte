import { PreRegisterForm } from "./components/PreRegisterForm";

const HomePage = () => {
  function onSubmitHandler(cpf: String) {
    console.log("[HomePage] onSubmitHandler", cpf);
  }

  return (
    <>
      <h1>Campanha</h1>
      <PreRegisterForm onSubmitHandler={(cpf) => onSubmitHandler(cpf)} />
    </>
  );
};

export default HomePage;
