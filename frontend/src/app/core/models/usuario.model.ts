export interface UsuarioRequest {
  nombres: string;
  apellidos: string;
  correo: string;
  password: string;
  rolId: number;
  activo: boolean;
}

export interface UsuarioResponse {
  id: number;
  nombres: string;
  apellidos: string;
  correo: string;
  rol: string;
  activo: boolean;
}
