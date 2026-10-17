import { useState, useEffect } from 'react';
import type { Consulta, ConsultaRequest } from './types';
import {
  agendarConsulta,
  cancelarConsulta,
  listarConsultas,
} from './api';
import { ConsultaList } from './ConsultaList';
import { ConsultaForm } from './ConsultaForm';

type View = 'list' | 'form';

export default function App() {
  const [view, setView] = useState<View>('list');
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [form, setForm] = useState<ConsultaRequest>({
    pacienteNome: '',
    tipo: '',
    dataHora: '',
  });
  const [formError, setFormError] = useState<string | null>(null);

  useEffect(() => {
    carregar();
  }, []);

  async function carregar() {
    setLoading(true);
    setError(null);
    try {
      const data = await listarConsultas();
      setConsultas(data);
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Erro inesperado ao listar');
    } finally {
      setLoading(false);
    }
  }

  async function handleAgendar(e: React.FormEvent) {
    e.preventDefault();
    setFormError(null);
    const partes = form.dataHora.split(':');
    const dataHora = partes.length === 2 ? form.dataHora + ':00' : form.dataHora;
    try {
      const nova = await agendarConsulta({ ...form, dataHora });
      setConsultas((prev) => [nova, ...prev]);
      setView('list');
      setForm({ pacienteNome: '', tipo: '', dataHora: '' });
    } catch (e) {
      setFormError(e instanceof Error ? e.message : 'Erro ao agendar consulta');
    }
  }

  async function handleCancelar(id: number) {
    if (!window.confirm('Deseja realmente cancelar esta consulta?')) {
      return;
    }
    try {
      await cancelarConsulta(id);
      setConsultas((prev) =>
        prev.map((c) =>
          c.id === id ? { ...c, status: 'CANCELADA' } : c,
        ),
      );
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Erro ao cancelar consulta');
    }
  }

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-6">Agendamento de Consultas</h1>
      {error && <p className="text-red-600 mb-4">{error}</p>}
      {view === 'list' ? (
        <>
          <button
            onClick={() => setView('form')}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 mb-4"
          >
            Nova consulta
          </button>
          <ConsultaList
            consultas={consultas}
            loading={loading}
            onCancelar={handleCancelar}
          />
        </>
      ) : (
        <>
          <button
            onClick={() => setView('list')}
            className="text-gray-600 text-sm mb-4 hover:underline"
          >
            ← Voltar à lista
          </button>
          <ConsultaForm
            form={form}
            setForm={setForm}
            onSubmit={handleAgendar}
            error={formError}
          />
        </>
      )}
    </div>
  );
}
