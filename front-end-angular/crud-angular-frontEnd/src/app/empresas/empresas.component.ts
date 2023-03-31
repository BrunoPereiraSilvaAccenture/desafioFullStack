import { Empresas } from './../_model/empresas';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmpresasEditComponent } from '../empresas-edit/empresas-edit.component';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-empresas',
  templateUrl: './empresas.component.html',
  styleUrls: ['./empresas.component.css']
})
export class EmpresasComponent implements OnInit{

  public displayedColumns: string[] = ['idEmpresa', 'cnpj','nomefantasia','endereco','cidade','uf','cep','telefone', 'actions'];
  public dataSource: Empresas[] = [];

  constructor(private dialog: MatDialog){};

  ngOnInit(): void {

  }

  public editEmpresa(inputEmpresa:Empresas){
    this.dialog.open(EmpresasEditComponent,{disableClose: true,
      data: {editableEmpresa: inputEmpresa}}).
        afterClosed().subscribe(
      resp => {
        console.log(`Editar empresa ${inputEmpresa.nomefantasia.toUpperCase()}!`);
    });
  }

  public deleteEmpresa(inputEmpresa:Empresas){
    this.dialog.open(DialogComponent,{disableClose: true,
      data: {
        dialogMsg:`Tem certeza que deseja excluir a empresa ${inputEmpresa.nomefantasia.toUpperCase()}?`,
        leftButtonLabel:'Cancelar',
        rightButtonLabel:'Sim'}}).
        afterClosed().subscribe(
      resp => {
        if(resp) {console.log(`Empresa ${inputEmpresa.nomefantasia.toUpperCase()} excluida com sucesso!`);}
        else {console.log(`Empresa ${inputEmpresa.nomefantasia.toUpperCase()} não excluida!`);}
    });
  }

  public createNewEmpresa(){
    this.dialog.open(EmpresasEditComponent,{disableClose: true,
      data: {actionName: 'Criar'}}).
        afterClosed().subscribe(
      resp => {
        console.log('Criar empresa!');
    });
  }

}
