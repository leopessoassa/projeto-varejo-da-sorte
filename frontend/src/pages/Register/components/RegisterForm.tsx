import { FieldValues, useForm } from "react-hook-form";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import InputMask from "react-input-mask";
import { Col, Row } from "react-bootstrap";
import { ErrorMessage } from "@hookform/error-message";
import { cpf as cpfValidate } from "cpf-cnpj-validator";

interface IRegister {
  onSubmitHandler: (cpf: string) => void;
}

export const RegisterForm = ({ onSubmitHandler }: IRegister) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: {
      cpf: "",
    },
    shouldUseNativeValidation: true,
    shouldFocusError: true,
  });

  const onSubmit = async (data: FieldValues) => {
    onSubmitHandler(data.cpf);
  };

  return (
    <>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Row>
          <Form.Group as={Row} className="mb-3" controlId="formCPF">
            <Form.Label>Seu CPF</Form.Label>
            <Col sm="10">
              <Form.Control
                size="lg"
                type="text"
                as={InputMask}
                mask="999.999.999-99"
                placeholder="Digite seu CPF para iniciar"
                {...register("cpf", {
                  validate: (data) => {
                    //Campo obrigatório
                    if (!data) return "CPF é um campo obrigatório";

                    //Número mínimo de caractere
                    const numeros = data.replace(/[^\d]+/g, "") || "";
                    if (numeros?.length < 11)
                      return "CPF precisa ser 11 números";

                    //Verifca se CPF é válido
                    return !cpfValidate.isValid(numeros)
                      ? "CPF inválido"
                      : true;
                  },
                })}
              />
              <ErrorMessage
                errors={errors}
                name="cpf"
                render={({ message }) => <p>{message}</p>}
              />
            </Col>
            <Col>
              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Col>
          </Form.Group>
        </Row>
      </Form>
    </>
  );
};
