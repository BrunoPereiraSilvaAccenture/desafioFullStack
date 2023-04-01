import { Fornecedores } from './../_model/fornecedores';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FornecedoresService implements OnInit{
  private readonly API_POST = 'api/empresa/{id}/fornecedor/';
  private readonly API_GET = 'api/fornecedor/';
  private readonly API_PUT = 'api/fornecedor/';
  private readonly API_DELETE = 'api/fornecedor/';
  private readonly API_POST_TOKEN = 'api/login';

  constructor(private httpclient: HttpClient, private _snackBar: MatSnackBar) {
    
  }

  ngOnInit(): void {}

  saveNewFornecedor(record: {
    tipodocumento:string;
    cnpjcpf: string;
    nome: string;
    cep: string;
    telefone: string;
    email:string;
    rg:string;
    datanascimento:string;
  }, id: string) {
    let headers = new HttpHeaders().append(
      'Authorization',
      'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ'
    );

    return this.httpclient
    .post<Fornecedores>(`${this.API_POST.replaceAll('{id}',id)}`, record, { headers: headers })
    .pipe(first());
  }

  upadateFornecedor(record: {
    tipodocumento:string;
    cnpjcpf: string;
    nome: string;
    cep: string;
    telefone: string;
    email:string;
    rg:string;
    datanascimento:string;
  }, id: string) {
    let headers = new HttpHeaders().append(
      'Authorization',
      'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ'
    );
    return this.httpclient
    .put<Fornecedores>(`${this.API_PUT}${id}`, record, { headers: headers })
    .pipe(first());
  }

  delete(id: string) {
    let headers = new HttpHeaders().append(
      'Authorization',
      'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ'
    );

    return this.httpclient
      .delete(`${this.API_DELETE}${id}`, { headers: headers })
      .pipe(first());
  }

  listFornecedores() {
    
    let headers = new HttpHeaders().append('Authorization', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2NlbnR1cmVCUiIsImV4cCI6MTY4MTA5MDAxNX0.AUfJAdv54qxyRB09A1XNUJN4ClMFs52rp0B78PtPKEgpI2ob0RtrdVdgh3976CVEK0xTtun2-CcQ2YaClF4EUQ');

    return this.httpclient
      .get<Fornecedores[]>(`${this.API_GET}`, { headers: headers })
      .pipe(first());
  }

}
