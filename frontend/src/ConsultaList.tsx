import type { Consulta } from './types';

interface Props {
  consultas: Consulta[];
  loading: boolean;
  onCancelar: (id: number) => void;
}

export function ConsultaList({ consultas, loading, onCancelar }: Props) {
  if (loading) {
    return <p className="text-gray-500">Carregando consultas...</p>;
  }
  if (consultas.length === 0) {
    return <p className="text-gray-500">Nenhuma consulta agendada.</p>;
  }

  return (
    <div className="border border-gray-200 rounded-lg shadow-sm overflow-hidden">
      <table className="w-full text-left border-collapse">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-3 border-b border-gray-200 font-semibold">Paciente</th>
            <th className="p-3 border-b border-gray-200 font-semibold">Tipo</th>
            <th className="p-3 border-b border-gray-200 font-semibold">Data / Hora</th>
            <th className="p-3 border-b border-gray-200 font-semibold">Status</th>
            <th className="p-3 border-b border-gray-200 font-semibold">Ação</th>
          </tr>
        </thead>
        <tbody>
          {consultas.map((c) => (
            <tr key={c.id} className="border-b border-gray-200">
              <td className="p-3">{c.pacienteNome}</td>
              <td className="p-3">{c.tipo}</td>
              <td className="p-3">{c.dataHora}</td>
              <td className="p-3">
                <span
                  className={`px-2 py-1 rounded-full text-xs font-medium ${
                    c.status === 'AGENDADA'
                      ? 'bg-green-100 text-green-700'
                      : 'bg-red-100 text-red-700'
                  }`}
                >
                  {c.status === 'AGENDADA' ? 'Agendada' : 'Cancelada'}
                </span>
              </td>
              <td className="p-3">
                {c.status === 'AGENDADA' ? (
                  <button
                    onClick={() => onCancelar(c.id)}
                    className="text-red-600 text-sm hover:underline"
                  >
                    Cancelar
                  </button>
                ) : (
                  <span className="text-gray-400 text-sm">—</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
