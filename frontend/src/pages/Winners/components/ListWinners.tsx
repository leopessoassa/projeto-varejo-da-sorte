import ListGroup from 'react-bootstrap/ListGroup';
import { IClient } from "../../../interfaces/IClient";
import { ItemWinner } from './ItemWinner';

interface IProps {
  itens: IClient[];
}

export const ListWinners = ({ itens }: IProps) => {
  return (
    <ListGroup as="ul">
      {itens.map((item) => (
        <ListGroup.Item as="li">
          <ItemWinner item={item} />
        </ListGroup.Item>
      ))}
    </ListGroup>
  );
};
