import { useLocation } from "react-router-dom";


const AboutPage = () => {
  const { state } = useLocation();

  return (
    <>
      <h1>About me {state?.client ?? 'TESTE'}</h1>
      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
        veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
        commodo consequat.{" "}
      </p>
    </>
  );
}

export default AboutPage;
