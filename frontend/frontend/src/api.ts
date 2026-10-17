import type { Consulta, ConsultaRequest } from './types';

const API_BASE = '/consultas';

async function json<T>(res: Response): Promise<T> {
  if (!res.ok) {
    const erro = (await res.json().catch(() => ({}))) as { erro?: string };
    throw new Error(erro.erro || `Erro na requisição (${res.status})`);
  }
  return (await res.json()) as T;
}

export async function listarConsultas(): Promise<Consulta[]> {
  return json<Consulta[]>(await fetch(API_BASE));
}

export async function agendarConsulta(data: ConsultaRequest): Promise<Consulta> {
  return json<Consulta>(
    await fetch(API_BASE, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    }),
  );
}

export async function cancelarConsulta(id: number): Promise<void> {
  const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
  if (!res.ok) {
    throw new Error('Erro ao cancelar consulta');
  }
}
