import { Col, Container, Image, Row } from "react-bootstrap";

export const BrandsComponent = () => {
  return (
    <section id="marcas-participantes">
      <h1>Marcas Participantes</h1>
      <Container>
        <Row>
          <Col xs={6} md={4}>
            <Image src="https://www.adobe.com/br/express/create/media_1c18ec00595fbe0f8de2f57a81f496912de4b1719.png?width=180&format=png&optimize=medium" rounded />
          </Col>
          <Col xs={6} md={4}>
            <Image src="https://www.adobe.com/br/express/create/media_1c18ec00595fbe0f8de2f57a81f496912de4b1719.png?width=180&format=png&optimize=medium" roundedCircle />
          </Col>
          <Col xs={6} md={4}>
            <Image src="https://www.adobe.com/br/express/create/media_1c18ec00595fbe0f8de2f57a81f496912de4b1719.png?width=180&format=png&optimize=medium" thumbnail />
          </Col>
        </Row>
      </Container>
    </section>
  );
};
