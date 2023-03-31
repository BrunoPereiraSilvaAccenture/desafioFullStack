import { Empresas } from './empresas';
export class Fornecedores{
  public idFornecedor!: string;
  public tipodocumento!: string;
  public cnpjcpf!: string;
  public rg!: string;
  public datanascimento!: Date;
  public nome!: string;
  public email!: string;
  public telefone!: string;
  public cep!: string;
  public endereco!: string;
  public uf!: string;
  public empresaIdEmpresa!: Empresas;
  public cidade!: string;
  }
