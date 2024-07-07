import ListGroup from 'react-bootstrap/ListGroup';
import { INumber } from "../../../interfaces/INumber";
import { ItemNumber } from "./ItemNumber";

interface IProps {
  itens: INumber[];
}

export const ListNumbers = ({ itens }: IProps) => {
  return (
    <ListGroup as="ul">
      {itens.map((item) => (
        <ListGroup.Item as="li">
          <ItemNumber item={item} />
        </ListGroup.Item>
      ))}
    </ListGroup>
  );
};
