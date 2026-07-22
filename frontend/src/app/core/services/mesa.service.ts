import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MesaRequest, MesaResponse } from '../models/mesa.model';
import { EstadoMesa } from '../models/enums';

@Injectable({ providedIn: 'root' })
export class MesaService {
  private baseUrl = '/api/mesas';

  constructor(private http: HttpClient) {}

  listar(): Observable<MesaResponse[]> {
    return this.http.get<MesaResponse[]>(this.baseUrl);
  }

  obtenerPorId(id: number): Observable<MesaResponse> {
    return this.http.get<MesaResponse>(`${this.baseUrl}/${id}`);
  }

  crear(request: MesaRequest): Observable<MesaResponse> {
    return this.http.post<MesaResponse>(this.baseUrl, request);
  }

  actualizar(id: number, request: MesaRequest): Observable<MesaResponse> {
    return this.http.put<MesaResponse>(`${this.baseUrl}/${id}`, request);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  cambiarEstado(id: number, estado: EstadoMesa): Observable<MesaResponse> {
    return this.http.patch<MesaResponse>(`${this.baseUrl}/${id}/estado?estado=${estado}`, {});
  }

  obtenerPorEstado(estado: EstadoMesa): Observable<MesaResponse[]> {
    return this.http.get<MesaResponse[]>(`${this.baseUrl}/estado/${estado}`);
  }
}
