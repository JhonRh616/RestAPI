export interface ApiGeneralResponse<T> {
  success: boolean;
  message: string;
  body: T;
}