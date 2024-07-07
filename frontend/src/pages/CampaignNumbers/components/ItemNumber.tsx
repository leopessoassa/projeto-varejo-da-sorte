import { Col, Row } from "react-bootstrap";
import { INumber } from "../../../interfaces/INumber";

interface IProps {
    item: INumber;
  }

export const ItemNumber = ({item}:IProps) => {
    return (
      <Row>
        <Col>{item.number}</Col>
        <Col>{item.created_at}</Col>
      </Row>
    );
  };
  