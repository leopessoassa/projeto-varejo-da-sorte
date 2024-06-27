import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import { Route, Routes } from "react-router-dom";
import { NotFoundPage } from "./pages/Erros";
import HomePage from "./pages/Home";
import RulesPage from "./pages/Rules";
import GamePage from "./pages/Game";
import RegisterPage from "./pages/Register";
import WinnersPage from "./pages/Winners";
import CampaignNumbers from "./pages/CampaignNumbers";
import { Nav } from "react-bootstrap";

function App() {
  return (
    <>
      <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
          <Navbar.Brand href="/">Promoção</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Item>
                <Nav.Link className="nav-link" href="/">
                  Home
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="nav-link" href="/meus-numeros-da-sorte">
                  Números da Sorte
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="nav-link" href="/ganhadores">
                  Ganhadores
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="nav-link" href="/#como-participar">
                  Mecânica
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="nav-link" href="/#marcas-participantes">
                  Parceiros
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="nav-link" href="/#faq">
                  FAQ
                </Nav.Link>
              </Nav.Item>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/meus-numeros-da-sorte" element={<CampaignNumbers />} />
          <Route path="/ganhadores" element={<WinnersPage />} />
          <Route path="/regulamento" element={<RulesPage />} />
          <Route path="/jogo" element={<GamePage />} />
          <Route path="/cadastro" element={<RegisterPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </Container>
    </>
  );
}

export default App;
