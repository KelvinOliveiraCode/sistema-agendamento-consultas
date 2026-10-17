import type { ConsultaRequest } from './types';

interface Props {
  form: ConsultaRequest;
  setForm: React.Dispatch<React.SetStateAction<ConsultaRequest>>;
  onSubmit: (e: React.FormEvent) => void;
  error: string | null;
}

export function ConsultaForm({ form, setForm, onSubmit, error }: Props) {
  return (
    <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
      <h2 className="text-xl font-bold mb-4">Agendar consulta</h2>
      {error && <p className="text-red-600 mb-4">{error}</p>}
      <form onSubmit={onSubmit} className="space-y-4 max-w-md">
        <div>
          <label className="block text-sm font-medium mb-1">Nome do paciente</label>
          <input
            className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
            value={form.pacienteNome}
            onChange={(e) => setForm({ ...form, pacienteNome: e.target.value })}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">Tipo de consulta</label>
          <input
            className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
            value={form.tipo}
            onChange={(e) => setForm({ ...form, tipo: e.target.value })}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">Data e hora</label>
          <input
            className="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
            type="datetime-local"
            value={form.dataHora}
            onChange={(e) => setForm({ ...form, dataHora: e.target.value })}
            required
          />
        </div>
        <button
          className="w-full bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
          type="submit"
          disabled={!form.pacienteNome || !form.tipo || !form.dataHora}
        >
          Agendar
        </button>
      </form>
    </div>
  );
}
