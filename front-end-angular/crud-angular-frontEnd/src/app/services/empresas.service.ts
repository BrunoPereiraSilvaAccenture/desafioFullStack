import { Empresas } from './../_model/empresas';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, first, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmpresasService implements OnInit{

  private readonly API_POST = 'api/empresa/';
  private readonly API_GET = 'api/empresa/';
  private readonly API_PUT = 'api/empresa/';
  private readonly API_DELETE = 'api/empresa/';

  constructor(private httpclient: HttpClient, private _snackBar: MatSnackBar) {}

  ngOnInit(): void {}

  saveNew(record: {cnpj:string,nomefantasia:string,cep:string,telefone:string}) {
    let headers = new HttpHeaders()
      .append('Authorization', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ')

    return this.httpclient.post<Empresas>(`${this.API_POST}`, record,{ headers: headers }).pipe(first());
  }

  saveUpdate(record: {cnpj:string,nomefantasia:string,cep:string,telefone:string},id: string) {
    let headers = new HttpHeaders()
      .append('Authorization', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ')

    return this.httpclient.put<Empresas>(`${this.API_PUT}${id}`, record,{ headers: headers }).pipe(first());
  }

  delete(id: string) {
    let headers = new HttpHeaders()
      .append('Authorization', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ')

    return this.httpclient.delete(`${this.API_PUT}${id}`, { headers: headers }).pipe(first());
  }

  listEmpresas(){
    let headers = new HttpHeaders()
      .append('Authorization', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ')

    return this.httpclient.get<Empresas[]>(`${this.API_GET}`,{ headers: headers }).pipe(first());
  }
}
