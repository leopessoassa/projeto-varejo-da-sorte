export interface IClient {
    id: number;
    name: string;
    cpf: string;
    tel: string;
    email: string;
    email_confirm: string;
    birthday: Date;
    created_at: Date;
    updated_at: Date;
    winner_at: number;
}