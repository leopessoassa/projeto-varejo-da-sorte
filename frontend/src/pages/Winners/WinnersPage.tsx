import { Container } from "react-bootstrap";
import { ListWinners } from "./components/ListWinners";
import { useState } from "react";
import { IClient } from "../../interfaces/IClient";


const WinnersPage = () => {
  const [list, setList] = useState<IClient[]>([]);

  return (
    <section>
      <header>
        <h1>Ganhadores</h1>
      </header>
      <Container>
        <h4>Ganhadores do Sorteio de R$50Mil</h4>
        <ListWinners itens={list} />
      </Container>
    </section>
  );
}

export default WinnersPage;
