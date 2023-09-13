export interface Commento {
  id?: number;
  data_commento?: Date;
  utente_id?: number;
  video_id?: number;
  testo?: string;
  commento_padre_id?: number;
}
