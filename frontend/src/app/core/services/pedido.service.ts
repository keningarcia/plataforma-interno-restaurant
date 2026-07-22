import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PedidoRequest, PedidoResponse, AgregarPlatoRequest } from '../models/pedido.model';

@Injectable({ providedIn: 'root' })
export class PedidoService {
  private baseUrl = '/api/pedidos';

  constructor(private http: HttpClient) {}

  listar(): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(this.baseUrl);
  }

  obtenerPorId(id: number): Observable<PedidoResponse> {
    return this.http.get<PedidoResponse>(`${this.baseUrl}/${id}`);
  }

  crear(request: PedidoRequest): Observable<PedidoResponse> {
    return this.http.post<PedidoResponse>(this.baseUrl, request);
  }

  actualizar(id: number, request: PedidoRequest): Observable<PedidoResponse> {
    return this.http.put<PedidoResponse>(`${this.baseUrl}/${id}`, request);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  agregarPlato(pedidoId: number, request: AgregarPlatoRequest): Observable<PedidoResponse> {
    return this.http.post<PedidoResponse>(`${this.baseUrl}/${pedidoId}/platos`, request);
  }

  actualizarCantidad(detalleId: number, cantidad: number): Observable<PedidoResponse> {
    return this.http.patch<PedidoResponse>(`${this.baseUrl}/platos/${detalleId}/cantidad?cantidad=${cantidad}`, {});
  }

  eliminarPlato(detalleId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/platos/${detalleId}`);
  }

  enviarACocina(pedidoId: number): Observable<PedidoResponse> {
    return this.http.patch<PedidoResponse>(`${this.baseUrl}/${pedidoId}/enviar-cocina`, {});
  }

  marcarEnPreparacion(pedidoId: number): Observable<PedidoResponse> {
    return this.http.patch<PedidoResponse>(`${this.baseUrl}/${pedidoId}/preparacion`, {});
  }

  marcarListo(pedidoId: number): Observable<PedidoResponse> {
    return this.http.patch<PedidoResponse>(`${this.baseUrl}/${pedidoId}/listo`, {});
  }

  entregarPedido(pedidoId: number): Observable<PedidoResponse> {
    return this.http.patch<PedidoResponse>(`${this.baseUrl}/${pedidoId}/entregar`, {});
  }

  cancelarPedido(pedidoId: number): Observable<PedidoResponse> {
    return this.http.patch<PedidoResponse>(`${this.baseUrl}/${pedidoId}/cancelar`, {});
  }

  obtenerPedidosPendientes(): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.baseUrl}/pendientes`);
  }

  obtenerPedidosPorMesa(mesaId: number): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.baseUrl}/mesa/${mesaId}`);
  }

  obtenerPedidosPorMesero(meseroId: number): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.baseUrl}/mesero/${meseroId}`);
  }
}
