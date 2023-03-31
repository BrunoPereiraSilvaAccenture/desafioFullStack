import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Endereco } from '../_model/endereco';
import { Observable, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EnderecoService implements OnInit {
  private readonly APICEP = 'http://cep.la/';

  constructor(private httpclient: HttpClient) {}

  ngOnInit(): void {}

  listEnderdeco(cep:string) {
    let headers = new HttpHeaders()
      //.append('Authorization', accessToken)
      .append('Accept','application/json');

    return this.httpclient.get<Endereco>(`${this.APICEP}${cep}`, { headers: headers });
  }


}
