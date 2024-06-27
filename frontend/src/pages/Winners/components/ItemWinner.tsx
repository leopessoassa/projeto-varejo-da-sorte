import { Col, Row } from "react-bootstrap";
import { IClient } from "../../../interfaces/IClient";

interface IProps {
    item: IClient;
  }

export const ItemWinner = ({item}:IProps) => {
    return (
      <Row>
        <Col>{item.name}</Col>
        <Col>{item.winner_at}</Col>
      </Row>
    );
  };
  