import { Col, Container, Row } from "react-bootstrap";
import { SelectClientForm } from "./components/SelectClientForm";
import { useState } from "react";
import { INumber } from "../../interfaces/INumber";
import { ListNumbers } from "./components/ListNumbers";

const CampaignNumbersPage = () => {
  const [list, setlist] = useState<INumber[]>([]);

  function onSubmitHandler(cpf: String) {
    console.log("[HomePage] onSubmitHandler", cpf);
  }

  return (
    <section>
      <header>
        <h1>Meus números</h1>
      </header>
      <Container>
        <Row>
          <Col>
            <SelectClientForm onSubmitHandler={(cpf) => onSubmitHandler(cpf)} />
          </Col>
        </Row>
        <Row>
          <Col>
            <ListNumbers itens={list} />
          </Col>
        </Row>
      </Container>
    </section>
  );
}

export default CampaignNumbersPage;
