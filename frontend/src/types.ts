export type StatusConsulta = 'AGENDADA' | 'CANCELADA';

export interface Consulta {
  id: number;
  pacienteNome: string;
  tipo: string;
  dataHora: string;
  status: StatusConsulta;
  criadoEm: string;
}

export interface ConsultaRequest {
  pacienteNome: string;
  tipo: string;
  dataHora: string;
}
