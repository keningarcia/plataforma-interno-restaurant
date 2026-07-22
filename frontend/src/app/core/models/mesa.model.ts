import { EstadoMesa } from './enums';

export interface MesaRequest {
  numero: number;
  capacidad: number;
}

export interface MesaResponse {
  id: number;
  numero: number;
  capacidad: number;
  estado: EstadoMesa;
}
