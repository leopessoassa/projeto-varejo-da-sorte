import { useLocation } from "react-router-dom";
import { RegisterForm } from "./components/RegisterForm";
import { useEffect, useState } from "react";
import { IClient } from "../../interfaces/IClient";
import ClientService from "../../services/ClientService";

const RegisterPage = () => {
  const { state } = useLocation();
  const [client, setClient] = useState<IClient>();
  const { isLoading, data } = ClientService.useGetClients({
    enabled: true,
    refetchOnWindowFocus: true,
    retry: 1,
  });

  useEffect(() => {
    console.log("isLoading:", isLoading);
    console.log("data:", data);
  }, [data, isLoading])

  function onSubmitHandler(cpf: String) {
    console.log("[RegisterPage] onSubmitHandler", cpf);
  }

  return (
    <>
      <h1>Campanha</h1>
      <RegisterForm onSubmitHandler={(cpf) => onSubmitHandler(cpf)} />
    </>
  );
};

export default RegisterPage;
