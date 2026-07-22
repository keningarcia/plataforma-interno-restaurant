import { EstadoDetalle, EstadoPedido } from './enums';

export interface DetalleRequest {
  platoId: number;
  cantidad: number;
}

export interface AgregarPlatoRequest {
  platoId: number;
  cantidad: number;
}

export interface PedidoRequest {
  mesaId: number;
  meseroId: number;
  detalles: DetalleRequest[];
}

export interface DetalleResponse {
  id: number;
  platoNombre: string;
  cantidad: number;
  precio: number;
  subtotal: number;
  estado: EstadoDetalle;
}

export interface PedidoResponse {
  id: number;
  codigo: string;
  fecha: string;
  hora: string;
  estado: EstadoPedido;
  mesaNumero: string;
  meseroNombre: string;
  detalles: DetalleResponse[];
  total: number;
}
