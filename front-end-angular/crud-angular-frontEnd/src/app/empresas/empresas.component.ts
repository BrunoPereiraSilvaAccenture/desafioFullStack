import { EmpresasService } from './../services/empresas.service';
import { Empresas } from './../_model/empresas';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmpresasEditComponent } from '../empresas-edit/empresas-edit.component';
import { DialogComponent } from '../dialog/dialog.component';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-empresas',
  templateUrl: './empresas.component.html',
  styleUrls: ['./empresas.component.css']
})
export class EmpresasComponent implements OnInit{

  public displayedColumns: string[] = ['idEmpresa', 'cnpj','nomefantasia','endereco','cidade','uf','cep','telefone', 'actions'];
  //public dataSource!: Observable<Empresas[]>;

  dataSource = new MatTableDataSource<Empresas>();

  constructor(private dialog: MatDialog,private empresasService:EmpresasService,
    private _snackBar: MatSnackBar){};

  ngOnInit(): void {

    this.empresasService.listEmpresas().subscribe(things => {
      this.dataSource.data = things;
  });

 
    //this.dataSource = this.empresasService.listEmpresas();
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
        if(resp) {
          console.log(`Empresa ${inputEmpresa.nomefantasia.toUpperCase()} excluida com sucesso!`);
          this.empresasService.delete(inputEmpresa.idEmpresa).subscribe(
              (resp)=> {
                this._snackBar.open(`Empresa ${inputEmpresa.nomefantasia} Deletada com Sucesso!`,'',{duration: 10000});
                window.location.reload();
              },
              (error:HttpErrorResponse)=>
              {
                this._snackBar.open(`Status: ${error.status}-${error.statusText}; Message: ${error.error}`,'',{duration: 10000});
                return null;
              });
        }
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

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
